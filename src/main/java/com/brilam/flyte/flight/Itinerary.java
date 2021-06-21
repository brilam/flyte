package com.brilam.flyte.flight;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Itinerary {
  // Itinerary IDs are String of the form FLIGHT#1-FLIGHT#2, etc...
  private String itineraryId;
  private Map<Integer, Flight> flights;  
  private BigDecimal totalCost;
  private int originId;
  private int destinationId;
  // Total time in seconds
  private long totalTime;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(Itinerary.class);

  public Itinerary(Map<Integer, Flight> flights) {
    this.flights = flights;
    totalCost = new BigDecimal(0);
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
    if (flights.size() == 1) {
      itineraryId = String.format("%d", flights.keySet().toArray()[0]);
    } else {
      List<String> flightString = flights.keySet().stream()
          .map(flightNum -> String.valueOf(flightNum))
          .collect(Collectors.toList());
      itineraryId = String.join("-", flightString);
    }
  }
  
  /**
   * Adds each flight and updates the total cost, and set the origin ID
   * and destination ID.
   */
  private void addFlights() {
    List<Flight> flightsList = new ArrayList<>();
    
    for (Integer flightNum: flights.keySet()) {
      LOGGER.info(String.format("Looking for flight: %d", flightNum.intValue()));
      Flight flight = flights.get(flightNum);
      LOGGER.info(String.format("Found flights %s", flight));

      updateTotalCost(flight.getCost());
      updateTotalTime(flight.getTotalTimeInSeconds());
      flightsList.add(flight);
    }
    setOriginId(flightsList.get(0).getOrigin().getId());
    setDestinationId(flightsList.get(flightsList.size() - 1).getDestination().getId());
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
