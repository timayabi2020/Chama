package com.chama;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class MemberOperations {
	@Autowired
    private MemberRepository memberRepository;
	
	 @Test
	    void getLastMember(){
	        Members memberSample = new Members("Adam");
	        memberRepository.save(memberSample);
	        MemberService memberService = new MemberService(memberRepository);

	        List<Members> toDoList = memberService.findAll();
	        Members lastToDo = toDoList.get(toDoList.size()-1);

	        assertEquals(memberSample.getName(), lastToDo.getName());
	        assertEquals(memberSample.getId(), lastToDo.getId());
	    }
	 @Test
	    void saveMember() {
	        MemberService memberService = new MemberService(memberRepository);
	        Members memberSample = new Members("Bob");

	        memberService.save(memberSample);

	        assertEquals(1.0, memberRepository.count());
	    }
	 
	 

}
