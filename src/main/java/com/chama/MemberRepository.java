package com.chama;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Members, Long> {
	@Query(value = "select * from members where email =:email", nativeQuery = true)
	Optional<Members> findByEmail(@Param("email")String email);
}
