package com.merkmal.apigate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Demo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
//	private @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
	private String title;
	private String body;
	private String[] tags;

	public long getId() {
		return id;
	}
        Demo(){}
        
        Demo(String name){
          this.body = body;
        }
}
