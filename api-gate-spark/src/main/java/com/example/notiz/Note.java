package com.example.notiz;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

// src: https://projectlombok.org/features/Data
@Data
public class Note {

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

        public String toString(){
          String tagsJson = new String();
          for ( int i=0; i<this.tags.length; i++){
            tagsJson += String.format("\"%s\"", this.tags[i]);
            if ( i != this.tags.length - 1 ){
              tagsJson += ", ";
            }
          }
          System.out.println(tagsJson);
          return String.format("{ \"id\":\"%s\", \"title\":\"%s\", \"body\":\"%s\", \"tags\": [ %s ] }", this.id, this.title, this.body, tagsJson );
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

        public Note(String title, String body, String[] tags){
          this.title = title;
          this.body = body;
          this.tags = tags;
          this.setCreateDateAndUpdateDate();
        }

        public void update(Note updateNote){
          this.setBody( updateNote.getBody() );
          this.setTags( updateNote.getTags() );
          this.setUpdateDate( updateNote.getLastModified());
        }

        /*
        public void addNote(Singlenote newNote){
            final Random random = new Random();
            int id = random.nextInt(Integer.MAX_VALUE);
            this.notes.put(String.valueOf(id), newNote);

        }
        */
}
