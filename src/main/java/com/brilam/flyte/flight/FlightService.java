package com.brilam.flyte.flight;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlightService.class);

  private FlightRepository flightRepository;
  
  @Autowired
  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }
  
  public List<Flight> findFlightById(int id) {
    return flightRepository.findById(id);
  }
  
  public List<Flight> getAllFlights() {
    return flightRepository.findAll();
  }
  
  public List<Itinerary> searchFlights(Date date, int originId, int destinationId) {
    List<Flight> allFlights = getAllFlights();    
    FlightGraph flightGraph = new FlightGraph(allFlights);
    LOGGER.info(flightGraph.toString());
    List<Itinerary> possibleItineraries = new ArrayList<>();
    
    for (Flight flight: allFlights) {
      if (isValidFlight(flight, date, originId)) {
        possibleItineraries.addAll(getMatchingItineraries(flightGraph, flight, originId, destinationId));
      }
    }
    
    return possibleItineraries;
  }
  
  private List<Itinerary> getMatchingItineraries(FlightGraph flightGraph, Flight flight, int originId, int destinationId) {
    Set<Flight> connectingFlights = flightGraph.getConnectingFlights(flight);
    ArrayList<Itinerary> result = new ArrayList<>();

    LOGGER.info(String.format("Flight ID: %d Origin ID: %d Destination ID: %d", flight.getFlightNumber(), originId, destinationId));
    if ((flight.getDestination().getId() == destinationId) && (!flight.isFullyBooked())) {
      Map<Integer, Flight> flights = new HashMap<>();
      flights.put(flight.getFlightNumber(), findFlightById(flight.getFlightNumber()).get(0));
      result.add(new Itinerary(flights));
    } else {
     // Go through every connecting flight
      for (Flight nextFlight : connectingFlights) {
        Map<Integer, Flight> alreadyVisitedFlights = new HashMap<>();
        ArrayList<Integer> alreadyVisitedOrigin = new ArrayList<>();
        if (!(nextFlight.isFullyBooked())) {
          LOGGER.info("Doing search...");
          alreadyVisitedFlights.put(flight.getFlightNumber(), findFlightById(flight.getFlightNumber()).get(0));
          alreadyVisitedFlights.put(nextFlight.getFlightNumber(), findFlightById(nextFlight.getFlightNumber()).get(0));
          alreadyVisitedOrigin.add(originId);
          alreadyVisitedOrigin.add(nextFlight.getOrigin().getId());

          // Add all possible itineraries from
          // connecting flight to destination
          addValidItineraries(flightGraph, destinationId, result, alreadyVisitedFlights, nextFlight,
              alreadyVisitedOrigin);
        }
      }
    }
    return result;
  }
  
  private void addValidItineraries(FlightGraph flightGraph, int destinationId, ArrayList<Itinerary> result,
      Map<Integer, Flight> alreadyVisitedFlights, Flight flight, ArrayList<Integer> alreadyVisitedOrigin) {

    // Base case where flightPath is already valid
    if ((flight.getDestination().getId() == destinationId) && (!(flight.isFullyBooked()))) {      
      result.add(new Itinerary(alreadyVisitedFlights));
    } else {

      // Go through every valid connection and recursively
      // add and check routes
      Set<Flight> connectingFlights = flightGraph.getConnectingFlights(flight);
      if (!connectingFlights.isEmpty()) {
        for (Flight nextFlight : connectingFlights) {
          if (!(alreadyVisitedOrigin.contains(nextFlight.getOrigin().getId()))
              && (!(nextFlight.isFullyBooked()))) {

            // Add onto data for every valid neighboring flight
            Map<Integer, Flight> updatedFlightPath = new HashMap<>();
            updatedFlightPath.putAll(alreadyVisitedFlights);
            
            ArrayList<Integer> alreadyVisitedPath = new ArrayList<>(alreadyVisitedOrigin);
            updatedFlightPath.put(nextFlight.getFlightNumber(), findFlightById(nextFlight.getFlightNumber()).get(0));
            alreadyVisitedPath.add(nextFlight.getOrigin().getId());

            addValidItineraries(flightGraph, destinationId, result, updatedFlightPath,
                nextFlight, alreadyVisitedPath);
          }
        }
      }
    }
  }
  
  /**
   * Returns if a flight is valid. A flight is considered valid
   * if the tickets are not all sold out, the origin id matches,
   * and the departure date matches.
   * @param flight the flight to validate
   * @param date the departure date
   * @param originId the location ID of the origin
   * @return if a flight is valid
   */
  private static boolean isValidFlight(Flight flight, Date date, int originId) {
    return flight.getOrigin().getId() == originId && flight.getNumSeats() > 0 && flight.getDepartureDate().compareTo(date) >= 0;
  }
}
