package com.chama;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class MemberService {
	 private MemberRepository memberRepository;

	    public MemberService(MemberRepository memberRepository) {
	        this.memberRepository = memberRepository;
	    }
	public List<Members> findAll() {
	    return memberRepository.findAll();
	}
	
	 public Members save(Members members) {
	        return memberRepository.save(members);
	    }
}
