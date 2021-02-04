package com.chama;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	private IouRepository iouRepository;

	public MemberService(MemberRepository memberRepository, IouRepository iouRepository) {
		this.memberRepository = memberRepository;
		this.iouRepository = iouRepository;
	}

	public List<Members> findAll() {
		return memberRepository.findAll(Sort.by(Direction.ASC, "name"));
	}

	public Members save(Members members) {
		return memberRepository.save(members);
	}

	public boolean exists(String email) {
		return memberRepository.findByEmail(email).isPresent();
	}

	public Optional<Members> memberDetails(String email) {
		return memberRepository.findByEmail(email);
	}
	
	public Optional<Members> memberDetailsById(Long id) {
		return memberRepository.findById(id);
	}

	public void deleteMembers(Members members) {
		memberRepository.delete(members);
	}

	/** IOU operations **/
	public List<Iou> findAllIou() {
		return iouRepository.findAll();
	}

	public Iou saveIou(Iou iou) {
		return iouRepository.save(iou);
	}

	public List<Iou> borrowingDetails(Long user_id) {
		return iouRepository.findByBorrowerId(user_id);
	}

	public List<Iou> LendingDetails(Long user_id) {
		return iouRepository.findByLenderId(user_id);
	}

	public Optional<Iou> borrower_lender_history(Long borrower, Long lender) {
		return iouRepository.findByHistory(borrower, lender);
	}

	public void deleteLedger(Iou iou) {
		iouRepository.delete(iou);
	}
}
