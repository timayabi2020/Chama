package com.chama;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Members {
	@Id
    @GeneratedValue
	private long id;
	private String name;
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
	    
}
