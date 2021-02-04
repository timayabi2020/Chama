package com.chama;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IouRepository extends JpaRepository<Iou, Long>{
	@Query(value = "select * from iou where borrower =:borrower", nativeQuery = true)
	List<Iou> findByBorrowerId(@Param("borrower")Long borrower);
	
	@Query(value = "select * from iou where lender =:lender", nativeQuery = true)
	List<Iou> findByLenderId(@Param("lender")Long lender);
    
	@Query(value = "select * from iou where borrower =:borrower and lender=:lender", nativeQuery = true)
	Optional<Iou> findByHistory(@Param("borrower")Long borrower, @Param("lender")Long lender);

}
