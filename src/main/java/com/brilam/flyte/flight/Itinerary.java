package com.brilam.flyte.flight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class Itinerary {
  // Itinerary IDs are String of the form FLIGHT#1-FLIGHT#2, etc...
  private String itineraryId;
  // List of flight IDs
  private List<Integer> flightNums;
  private BigDecimal totalCost;
  private int originId;
  private int destinationId;
  // Total time in seconds
  private long totalTime;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Itinerary.class);

  
  // TODO: Probably remove this, since it is null (not being injected)
  @Autowired
  private FlightService flightService;
  
  public Itinerary(List<Integer> flightNums) {
    this.flightNums = flightNums;
    makeItineraryId();
    addFlights();
  }
  
  /**
   * Creates the itinerary ID. If the interary only contains
   * one flight, the flight number is the itinerary ID.
   * If there are multiple flights, concatenate all the
   * flights with a hyphen separating each flight.
   * Example: FLIGHT#1-FLIGHT#2
   */
  private void makeItineraryId() {
    if (flightNums.size() == 1) {
      itineraryId = String.format("%d", flightNums.get(0));
    } else {
      List<String> flightString = flightNums.stream()
          .map(flight -> String.valueOf(flight))
          .collect(Collectors.toList());
      itineraryId = String.join("-", flightString);
    }
  }
  
  /**
   * Adds each flight and updates the total cost, and set the origin ID
   * and destination ID.
   */
  private void addFlights() {
    List<Flight> flights = new ArrayList<>();
    
    for (Integer flightNum: flightNums) {
      LOGGER.info(String.format("Looking for flight: %d", flightNum.intValue()));
      LOGGER.info("Flight service null"   + (flightService == null));
      List<Flight> foundFlights = flightService.findFlightById(flightNum.intValue());
      LOGGER.info(String.format("Found flights %s", foundFlights));

      Flight flight = flightService.findFlightById(flightNum.intValue()).get(0);
      updateTotalCost(flight.getCost());
      updateTotalTime(flight.getTotalTimeInSeconds());
      flights.add(flight);
    }
    setOriginId(flights.get(0).getOrigin().getId());
    setDestinationId(flights.get(flights.size() - 1).getDestination().getId());
  }
  
  /**
   * Updates the total cost of the itinerary.
   * @param cost the cost from a flight
   */
  private void updateTotalCost(BigDecimal cost) {
    totalCost = totalCost.add(cost);
  }
  
  private void updateTotalTime(long timeInSeconds) {
    totalTime += timeInSeconds;
  }
  
  public void setOriginId(int originId) {
    this.originId = originId;
  }
  
  public void setDestinationId(int destinationId) {
    this.destinationId = destinationId;
  }
  
  public int getOriginId() {
    return originId;
  }
  
  public int getDestinationId() {
    return destinationId;
  }
  
  public BigDecimal getTotalCost() {
    return totalCost;
  }
  
  public String getItineraryId() {
    return itineraryId;
  }
  
  /**
   * Returns total time in seconds.
   * @return total time in seconds
   */
  public long getTotalTime() {
    return totalTime;
  }
}
