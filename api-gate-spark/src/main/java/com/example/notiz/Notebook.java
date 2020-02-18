package com.example.notiz;

import lombok.Data;

@Data
public class Notebook {

  public String id, author, title;

  public String toString(){
    return String.format("{ \"id\":\"%s\", \"title\":\"%s\", \"author\":\"%s\" }", this.id, this.title, this.author);
  }

  public Notebook(String author, String title) {
    this.author = author;
    this.title = title;
  }

  public String setId(String id){
    this.id = id;
    return this.id;
  }
  public String setId(int id){
    return this.setId(String.valueOf(id));
  }

}
