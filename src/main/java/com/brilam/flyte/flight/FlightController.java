package com.brilam.flyte.flight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);

  private final FlightService flightService;
  private final LocationService locationService;
  
  @Autowired
  public FlightController(FlightService flightService, LocationService locationService) {
    this.flightService = flightService;
    this.locationService = locationService;
  }
  
  @GetMapping("/api/flights")
  public List<Flight> getFlight(@RequestParam(required=false) Integer id) {
    // Search flight by ID
    if (id != null) {
      return flightService.findFlightById(id);
    }
    
    // Return empty list if invalid result
    return new ArrayList<Flight>();
  }
  
  @GetMapping("/api/itinerary")
  public List<Itinerary> searchFlights(@RequestParam String departureDate,
      @RequestParam String originLocation,
      @RequestParam String destinationLocation) {

    List<Location> origin = locationService.findLocationByName(originLocation);
    List<Location> destination = locationService.findLocationByName(destinationLocation);

    // If either the origin or destination have invalid results, return nothing
    if (origin.size() == 0 || destination.size() == 0) {
      return new ArrayList<Itinerary>();
    }

    int originId = origin.get(0).getId();
    int destinationId = destination.get(0).getId();
    Date date = null;
    try {
      date = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss").parse(departureDate);
    } catch (ParseException e) {
      // Invalid date
      return new ArrayList<Itinerary>();
    }

    return flightService.searchFlights(date, originId, destinationId); 
  }
}
