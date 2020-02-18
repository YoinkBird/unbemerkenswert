package com.example.notiz;

public class Notebook {

  public String id, author, title;

  public String toString(){
    return String.format("{ \"id\":\"%s\", \"title\":\"%s\", \"author\":\"%s\" }", this.id, this.title, this.author);
  }

  public Notebook(String author, String title) {
    this.author = author;
    this.title = title;
  }

  public String getId(){
    return this.id;
  }
  public String setId(String id){
    this.id = id;
    return this.id;
  }
  public String setId(int id){
    return this.setId(String.valueOf(id));
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
