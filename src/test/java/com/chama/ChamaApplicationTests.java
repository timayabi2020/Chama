package com.chama;


import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class ChamaApplicationTests {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	private MemberService memberService;

	@Test
	void getMemberTest() throws Exception {

		List<Members> memberList = new ArrayList<Members>();
		when(memberService.findAll()).thenReturn(memberList);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/users").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		int httpStatus = result.getResponse().getStatus();
		System.out.println("Get members service http status " + httpStatus);
		if (httpStatus == 200 || httpStatus == 403) {
			// Pass
		} else {
			fail("Error invoking Post users API ");
		}

	}

	@Test
	void addMembers() throws Exception {

		MvcResult result = mockMvc.perform(
				MockMvcRequestBuilders.post("/api/add").content("{\"email\":\"chuck@gmail.com\",\"user\":\"Chuck\"}")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int httpStatus = result.getResponse().getStatus();
		System.out.println("Post members service http status " + httpStatus);
		if (httpStatus == 200 || httpStatus == 403) {
			// Pass
		} else {
			fail("Error invoking Post users API ");
		}
	}

	@Test
	void borrow() throws Exception {

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/borrow")
				.content("{\"lender\":\"chuck@gmail.com\",\"borrower\":\"timwamalwa@gmail.com\",\"amount\":\"10.0\"}")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andReturn();
		int httpStatus = result.getResponse().getStatus();
		System.out.println("IOU service http status " + httpStatus);
		if (httpStatus == 200 || httpStatus == 403) {
			// Pass
		} else {
			fail("Error invoking Post users API ");
		}
	}

	@Test
	void getLedger() throws Exception {

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/user_ledger_details").content("{\"email\":\"chuck@gmail.com\"}")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int httpStatus = result.getResponse().getStatus();
		System.out.println("Ledger service http status " + httpStatus);
		if (httpStatus == 200 || httpStatus == 403) {
			// Pass
		} else {
			fail("Error invoking Post users API ");
		}
	}

	@Test
	void deleteUser() throws Exception {

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.post("/api/delete").content("{\"email\":\"chuck@gmail.com\"}")
						.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int httpStatus = result.getResponse().getStatus();
		System.out.println("Delete users service http status " + httpStatus);
		if (httpStatus == 200 || httpStatus == 403) {
			// Pass
		} else {
			fail("Error invoking Post users API ");
		}
	}

}