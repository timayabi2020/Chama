package com.chama;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChamaController {
	 @Autowired
	 private MemberService memberService;
	 
	 @GetMapping("/users")
	    ResponseEntity<Map<String, List<String>>> getAllToDos() {
		  Map<String, List<String>>map = new HashMap<>();
		  List<String> list = new ArrayList<>();
		  for(Members member: memberService.findAll()){
			  list.add(member.getName());
		  }
		     map.put("Users",list);
	        return new ResponseEntity<>(map, HttpStatus.OK);
	    }
}
