package com.simonamilosheska.exceptions;

public class EmptyListException extends  RuntimeException{

  public EmptyListException() {
    super("List is Empty");
  }
}
