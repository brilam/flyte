package com.brilam.flyte.flight;

import java.util.Date;

public class SearchInfo {
  private Date date;
  private int originId;
  private int destinationId;
  
  public Date getDate() {
    return date;
  }
  
  public void setDate(Date date) {
    this.date = date;
  }
  
  public int getOriginId() {
    return originId;
  }
  
  public void setOriginId(int originId) {
    this.originId = originId;
  }
  
  public int getDestinationId() {
    return destinationId;
  }
  
  public void setDestinationId(int destinationId) {
    this.destinationId = destinationId;
  }
}
