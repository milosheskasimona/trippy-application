package com.simonamilosheska.models;

import java.time.LocalDate;

abstract class BaseTable {

  protected int id;
  protected LocalDate createdOn;
  protected LocalDate deletedOn;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public LocalDate getCreatedOn() {
    return createdOn;
  }

  public void setCreatedOn(LocalDate createdOn) {
    this.createdOn = createdOn;
  }

  public LocalDate getDeletedOn() {
    return deletedOn;
  }

  public void setDeletedOn(LocalDate deletedOn) {
    this.deletedOn = deletedOn;
  }
}
