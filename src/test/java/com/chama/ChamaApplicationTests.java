package com.chama;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@ExtendWith(SpringExtension.class)
@WebMvcTest
class ChamaApplicationTests {
	@Autowired
    MockMvc mockMvc;

    @MockBean
    private MemberService memberService;
    @Test
    void addAllMembers() throws Exception {
    	
    	 List<Members> memberList = new ArrayList<Members>();
    	 memberList.add(new Members(1L,"Adam"));
    	 memberList.add(new Members(2L,"Bob"));
         when(memberService.findAll()).thenReturn(memberList);
         
         mockMvc.perform(MockMvcRequestBuilders.get("/members").contentType(MediaType.APPLICATION_JSON)
        		 );
    	 
    }

}