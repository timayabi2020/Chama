package com.chama;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChamaController {
	 @Autowired
	 private MemberService memberService;
	 @GetMapping("/members")
	    ResponseEntity<List<Members>> getAllToDos() {
	        return new ResponseEntity<>(memberService.findAll(), HttpStatus.OK);
	    }
}
