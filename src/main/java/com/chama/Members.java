package com.chama;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MEMBERS")
public class Members {
	@Id
    @GeneratedValue
	private long id;
	private String name;
	private String email;
	@Column(columnDefinition = "double default 0.0")
	private double amount_lent_out;
	@Column(columnDefinition = "double default 0.0")
	private double amount_borrowed;
    public Members(){

    }
	public Members(long id, String name) {

        this.id = id;
        this.name = name;
       
    }
	 public Members(String name) {
	        this.name = name;
	        
	    }
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the amount_lent_out
	 */
	public double getAmount_lent_out() {
		return amount_lent_out;
	}
	/**
	 * @param amount_lent_out the amount_lent_out to set
	 */
	public void setAmount_lent_out(double amount_lent_out) {
		this.amount_lent_out = amount_lent_out;
	}
	/**
	 * @return the amount_borrowed
	 */
	public double getAmount_borrowed() {
		return amount_borrowed;
	}
	/**
	 * @param amount_borrowed the amount_borrowed to set
	 */
	public void setAmount_borrowed(double amount_borrowed) {
		this.amount_borrowed = amount_borrowed;
	}
	
	    
}
