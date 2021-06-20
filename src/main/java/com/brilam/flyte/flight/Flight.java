package com.brilam.flyte.flight;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity(name = "flights")
public class Flight {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private int id;
  
  @Column(name = "arrivalDate", columnDefinition="DATETIME")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date arrivalDate;
  
  @Column(name = "departureDate", columnDefinition="DATETIME")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date departureDate;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="airline", referencedColumnName="id")
  private Airline airline;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "origin", referencedColumnName = "id")
  private Location origin;
  
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "destination", referencedColumnName = "id")
  private Location destination;
  
  @Column(name = "travelTime")
  private long totalTimeInSeconds;
  
  private BigDecimal cost;
  private int numSeats;
  
  public int getFlightNumber() {
    return id;
  }
  
  public void setFlightNumber(int id) {
    this.id = id;
  }
  
  public Date getDepartureDate() {
    return departureDate;
  }
  
  public void setDepartureDate(Date departureDate) {
    this.departureDate = departureDate;
  }
  
  public Date getArrivalDate() {
    return arrivalDate;
  }
  
  public void setArrivalDate(Date arrivalDate) {
    this.arrivalDate = arrivalDate;
  }
  
  public Location getOrigin() {
    return origin;
  }
  
  public void setOrigin(Location origin) {
    this.origin = origin;
  }
  
  public Location getDestination() {
    return destination;
  }
  
  public void setDestination(Location destination) {
    this.destination = destination;
  }
  
  public long getTotalTimeInSeconds() {
    return totalTimeInSeconds;
  }
  
  public void setTotalTimeInSeconds(long totalTimeInSeconds) {
    this.totalTimeInSeconds = totalTimeInSeconds;
  }
  
  public BigDecimal getCost() {
    return cost;
  }
  
  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }
  
  public int getNumSeats() {
    return numSeats;
  }
  
  public void setNumSeats(int numSeats) {
    this.numSeats = numSeats;
  }
}
