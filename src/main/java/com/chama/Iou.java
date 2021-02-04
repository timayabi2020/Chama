package com.chama;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="IOU")
public class Iou {
	@Id
    @GeneratedValue
	private long id;
	private double amount;
	private long borrower;
	private long lender;
	private Date borrow_date;
	private Date lend_date;
	private String borrower_name;
	private String lender_name;
}
