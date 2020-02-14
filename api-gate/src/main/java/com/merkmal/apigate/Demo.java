package com.merkmal.apigate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@Entity
public class Demo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
        private String createDate;
        private String updateDate;

	private String title;
	private String body;
	private String[] tags;

	public long getId() {
		return id;
	}
        /*
        Demo(){}
        */
        
        /*
         * NOT SURE how lombok constructors work; disabling for now since this was so very misleading
        Demo(String name, String body, String[] tags){
          this.body = body;
          //this.createDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
          this.createDate ="hello";

        }
        */

        public void setCreateDate(){
          this.createDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        }
        public void setUpdateDate(){
          this.updateDate = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        }
}
