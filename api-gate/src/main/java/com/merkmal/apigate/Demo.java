package com.merkmal.apigate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Demo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String content;

	public long getId() {
		return id;
	}
        
        public void setContent(String content){
          this.content = content;
        }
        public String getContent(){
          return content;
        }
}
