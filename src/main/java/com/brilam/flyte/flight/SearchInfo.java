package com.brilam.flyte.flight;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

public class SearchInfo {
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
