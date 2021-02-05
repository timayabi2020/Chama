package com.chama;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChamaController {

	@Autowired
	private MemberService memberService;
	private static Logger logger = LoggerFactory.getLogger(ChamaController.class);

	@GetMapping("/users")
	ResponseEntity<Map<String, List<String>>> getAllUsers() {
		logger.info("Invoking get users method ");
		Map<String, List<String>> map = new HashMap<>();
		try {
		List<String> list = new ArrayList<>();
		for (Members member : memberService.findAll()) {
			System.out.println("Getting " + member.getEmail());
			list.add(member.getName());

		}
		map.put("Users", list);
		}catch(Exception ex) {
			logger.error("Get users method failed "+ex.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PostMapping("/add")
	ResponseEntity<Members> addUser(@RequestBody UserRequestBody payload) {
		logger.info("Invoking add users method ");
		Members member = new Members();
		try {
		if (payload == null || payload.getEmail() == null || payload.getEmail().isEmpty() || payload.getUser() == null
				|| payload.getEmail().isEmpty()) {
			return new ResponseEntity<>(member, HttpStatus.FORBIDDEN);
		}

		if (memberService.exists(payload.getEmail())) {
			return new ResponseEntity<>(memberService.memberDetails(payload.getEmail()).get(), HttpStatus.FORBIDDEN);
		}
		member.setName(payload.getUser());
		member.setEmail(payload.getEmail());
		member.setAmount_borrowed(0.0);
		member.setAmount_lent_out(0.0);
		memberService.save(member);
		}catch(Exception ex) {
			logger.error("Add users method failed "+ex.getMessage());
			return new ResponseEntity<>(member, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(member, HttpStatus.OK);
	}
	
	@PostMapping("/delete")
	ResponseEntity<Map<String,String>> deleteUser(@RequestBody UserRequestBody payload) {
		Map<String,String>map = new HashMap<>();
		try {
		if (payload == null || payload.getEmail() == null || payload.getEmail().isEmpty()) {
			map.put("error", "Email is missing");
			map.put("errorCode", "500");
			return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
		}

		if (!memberService.exists(payload.getEmail())) {
			map.put("error", "Email not found");
			map.put("errorCode", "404");
			return new ResponseEntity<>(map, HttpStatus.FORBIDDEN);
		}
		//Get all data in ledger associated with the user
		
		Members details = memberService.memberDetails(payload.getEmail()).get();
		List<Iou> borrowList = memberService.borrowingDetails(details.getId());
		List<Iou> LenderList = memberService.LendingDetails(details.getId());
		for (Iou iou_borrow : LenderList) {
			//reduce the borrowed amount for whoever owed me
			Members debt = memberService.memberDetailsById(iou_borrow.getBorrower()).get();
			debt.setAmount_borrowed(debt.getAmount_borrowed()-iou_borrow.getAmount());
			memberService.save(debt);
			memberService.deleteLedger(iou_borrow);

		}
		for (Iou iou_lend : borrowList) {
			memberService.deleteLedger(iou_lend);
		
		}
        
		//Finally delete the user
		memberService.deleteMembers(details);
		map.put("successMessage", "Successfully deleted user");
		map.put("successCode", "200");
		}catch(Exception ex) {
			map.put("error", "Error when deleting users");
			map.put("errorCode", "500");
			logger.error("Delete users method failed "+ex.getMessage());
			return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(map, HttpStatus.OK);
	}


	@PostMapping("/borrow")
	ResponseEntity<String> borrow(@RequestBody UserRequestBody payload) {
		logger.info("Invoking borrowing method");
         try {
		if (payload == null || payload.getAmount() == null || payload.getAmount().isEmpty()
				|| payload.getBorrower() == null || payload.getBorrower().isEmpty() || payload.getLender() == null
				|| payload.getLender().isEmpty()) {
			return new ResponseEntity<>("Borrower email or lender email or request amount missing",
					HttpStatus.FORBIDDEN);
		}

		if (!memberService.exists(payload.getBorrower())) {
			return new ResponseEntity<>("Borrower does not belong to mahustler sacco", HttpStatus.FORBIDDEN);
		}
		if (!memberService.exists(payload.getLender())) {
			return new ResponseEntity<>("Lender email does not belong to mahustler sacco", HttpStatus.FORBIDDEN);
		}

		if (payload.getLender().equals(payload.getBorrower())) {
			return new ResponseEntity<>("You cannot borrow from your own account", HttpStatus.FORBIDDEN);
		}

		Members borrower_details = memberService.memberDetails(payload.getBorrower()).get();
		Members lender_details = memberService.memberDetails(payload.getLender()).get();
		Optional<Iou> borrow_history = memberService.borrower_lender_history(borrower_details.getId(),
				lender_details.getId());
		Optional<Iou> lend_history = memberService.borrower_lender_history(lender_details.getId(),
				borrower_details.getId());
		if (borrow_history.isPresent()) {
			// Update the balances for the lender
			borrow_history.get().setAmount(borrow_history.get().getAmount() + Double.parseDouble(payload.getAmount()));
			memberService.saveIou(borrow_history.get());
		} else if (lend_history.isPresent()) {
			lend_history.get().setAmount(lend_history.get().getAmount() + Double.parseDouble(payload.getAmount()));
			memberService.saveIou(lend_history.get());
		} else {
			Iou iou = new Iou();

			iou.setBorrower(borrower_details.getId());
			iou.setLender(lender_details.getId());
			iou.setBorrow_date(new Date());
			iou.setAmount(Double.parseDouble(payload.getAmount()));
			iou.setBorrower_name(borrower_details.getName());
			iou.setLender_name(lender_details.getName());

			memberService.saveIou(iou);
		}

		// Update balances for both borrower and lender
		borrower_details
				.setAmount_borrowed(borrower_details.getAmount_borrowed() + Double.parseDouble(payload.getAmount()));
		memberService.save(borrower_details);

		lender_details
				.setAmount_borrowed(lender_details.getAmount_lent_out() + Double.parseDouble(payload.getAmount()));
		memberService.save(lender_details);
         }catch(Exception ex) {
        	 logger.error("Borrowing and lending method failed");
        	 return new ResponseEntity<>("Server error", HttpStatus.INTERNAL_SERVER_ERROR);
         }
		return new ResponseEntity<>("Successfully borrowed", HttpStatus.OK);
	}

	@PostMapping("/user_ledger_details")
	ResponseEntity<?> ledgger(@RequestBody UserRequestBody payload) {
		logger.info("Invoking ledger method");
		JSONObject parentObj = new JSONObject();
		try {
		if (payload == null || payload.getEmail() == null || payload.getEmail().isEmpty()) {
			parentObj.put("Error", "User email address is missing");
			//return parentObj.toString();
			return new ResponseEntity<>(parentObj.toString(), HttpStatus.FORBIDDEN);
		}
		if (!memberService.exists(payload.getEmail())) {
			parentObj.put("Error", "User email provided does not belong to hustler nation");
			//return parentObj.toString();
			return new ResponseEntity<>(parentObj.toString(), HttpStatus.FORBIDDEN);
		}

		Members details = memberService.memberDetails(payload.getEmail()).get();
		parentObj.put("name", details.getName());

		JSONObject borrowMap = new JSONObject();
		JSONObject lenderMap = new JSONObject();

		List<Iou> borrowList = memberService.borrowingDetails(details.getId());
		List<Iou> LenderList = memberService.LendingDetails(details.getId());
		for (Iou iou_borrow : LenderList) {
			lenderMap.put(iou_borrow.getBorrower_name(), String.valueOf(iou_borrow.getAmount()));
		}
		for (Iou iou_lend : borrowList) {
			borrowMap.put(iou_lend.getLender_name(), String.valueOf(iou_lend.getAmount()));
		}
		parentObj.put("owes", borrowMap);
		parentObj.put("owed_by", lenderMap);
		parentObj.put("balance", String.valueOf(details.getAmount_lent_out() - details.getAmount_borrowed()));
		}catch(Exception ex) {
			parentObj.put("error", "Error occured when getting ledger information");
			parentObj.put("errorCode", "500");
			logger.error("Error occured when getting ledger information");
			return new ResponseEntity<>(parentObj, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(parentObj.toString(), HttpStatus.OK);
	}
	
}
