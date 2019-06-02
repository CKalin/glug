package de.thkoeln.glug.communication.response;

public class Answer {
  private String text;
  private int id;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}
