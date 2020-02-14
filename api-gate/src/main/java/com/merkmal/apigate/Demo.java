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

        // vvv doesn't work, format literally prints out this string.
        // private final String dateFormat = "yyyy.MM.dd.HH.mm.ss";
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
          // attempt1 - class variable
          // String dateFormat = this.dateFormat.toString();
          // this.createDate = new SimpleDateFormat(this.dateFormat).format(new Date());

          // copy-pasta to setUpdateDate, doesn't work as class variable for some reason; see above comments
          String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
          this.createDate = new SimpleDateFormat(dateFormat).format(new Date());
        }
        public void setUpdateDate(){
          String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";
          this.updateDate = new SimpleDateFormat(dateFormat).format(new Date());
        }
}
