package com.merkmal.apigate;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

// src: https://projectlombok.org/features/Data
@Data
public class Singlenote {

	private long id;

        // vvv doesn't work, format literally prints out this string.
        // private final String dateFormat = "yyyy.MM.dd.HH.mm.ss";
        private String created;
        private String lastModified;

	private String title;
	private String body;
	private String[] tags;

	public long getId() {
		return id;
	}

        public void setCreateDate(){
          // attempt1 - class variable
          // String dateFormat = this.dateFormat.toString();
          // this.created = new SimpleDateFormat(this.dateFormat).format(new Date());

          // copy-pasta to setUpdateDate, doesn't work as class variable for some reason; see above comments
          String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.S";
          this.created = new SimpleDateFormat(dateFormat).format(new Date());
        }
        public void setUpdateDate(){
          String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.S";
          this.lastModified = new SimpleDateFormat(dateFormat).format(new Date());
        }
        // HACK - cannot figure out how to override Lambok "@Data"-generated constructor
        // privately allow overriding this.created
        private void setUpdateDate(String dateOverride){
          this.lastModified = dateOverride;
        }

        // call this on creation; update created date and lastmodified at same time
        // reason: must ensure that this.lastModified is set from this.created or else timestamps won't match ( calling this.setUpdateDate() as this would be a duplicate call to the date function, i.e. time will have passed)
        private void setCreateDateAndUpdateDate(){
          this.setCreateDate();
          this.setUpdateDate(this.getCreated());
        }

        public Singlenote(){
          this.setCreateDateAndUpdateDate();
        }

        public void update(Singlenote updateNote){
          this.setBody( updateNote.getBody() );
          this.setTags( updateNote.getTags() );
          this.setUpdateDate( updateNote.getLastModified());
        }

}
