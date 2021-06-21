package com.brilam.flyte.flight;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.brilam.flyte.FlyteApplication;

public class FlightGraph {
  private List<Flight> flights;
  private Map<Integer, Set<Integer>> flightGraph;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(FlightGraph.class);

  
  public FlightGraph(List<Flight> flights) {
    this.flights = flights;
    flightGraph = new HashMap<>();
    createEmptyConnections();
    populateConnections();
    
  }
  
  /**
   * Creates the graph with empty connections.
   * It populates the graph with the flights.
   */
  private void createEmptyConnections() {
    for (Flight flight: flights) {
      flightGraph.put(flight.getFlightNumber(), new HashSet<>());
    }
  }
  
  /**
   * Populates all the connections for each flight.
   */
  private void populateConnections() {
    for (Flight flight: flights) {
      populateConnections(flight);
    }
  }
  
  /**
   * Populates the connections for a flight.
   * @param flight the flight to populate connections for
   */
  private void populateConnections(Flight flight) {
    int originId = flight.getOrigin().getId();
    int destinationId = flight.getDestination().getId();
    long departureTime = flight.getDepartureDate().getTime();
    long arrivalTime = flight.getArrivalDate().getTime();
    
    for (Flight currFlight : flights) {
      LOGGER.info("Current flight origin ID: " + currFlight.getOrigin().getId());
      LOGGER.info("Destination ID: " + destinationId);
      LOGGER.info("Current flight destination ID: " + currFlight.getDestination().getId());

      // Checks if flight connect to current flight
      if (!(currFlight.equals(flight)) && currFlight.getOrigin().getId() == destinationId) {
        long currDeparture = currFlight.getDepartureDate().getTime();
        long duration = currDeparture - arrivalTime;
        
        if ((duration >= FlyteApplication.MINIMUM_LAYOVER_TIME_MILLIS) && (duration <= FlyteApplication.MAXIMUM_LAYOVER_TIME_MILLIS)) {
          flightGraph.get(flight.getFlightNumber()).add(currFlight.getFlightNumber());
        }
      } else if (!(currFlight.equals(flight)) && currFlight.getDestination().getId() == originId) {
        // Checks if current flight connects to flight
        long currArrival = currFlight.getArrivalDate().getTime();
        long duration = departureTime - currArrival;
        
        if ((duration >= FlyteApplication.MINIMUM_LAYOVER_TIME_MILLIS) && (duration <= FlyteApplication.MAXIMUM_LAYOVER_TIME_MILLIS)) {
          flightGraph.get(currFlight.getFlightNumber()).add(flight.getFlightNumber());
        }
      }
    }
  }
  
  /**
   * Returns the flight given a flight number.
   * @param flightNumber the flight number of the flight
   * @return the flight with the given flight number
   */
  public Flight getFlight(int flightNumber) {
    for (Flight flight: flights) {
      if (flight.getFlightNumber() == flightNumber) {
        return flight;
      }
    }
    return null;
  }
  
  /**
   * Returns the connecting flights for a flight.
   * @param flight a flight to find connections to
   * @return the connecting flights for a flight
   */
  public Set<Flight> getConnectingFlights(Flight flight) {
    int id = flight.getFlightNumber();

    Set<Integer> connectingFlightIds = flightGraph.get(id);
    HashSet<Flight> paths = new HashSet<Flight>();
    for (int flightId : connectingFlightIds) {
      paths.add(getFlight(flightId));
    }
    return paths;
  }
  
  @Override
  public String toString() {
    return flightGraph.toString();
  }
}
