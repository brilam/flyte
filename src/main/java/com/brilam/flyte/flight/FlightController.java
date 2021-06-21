package com.brilam.flyte.flight;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
  private static final Logger LOGGER = LoggerFactory.getLogger(FlightController.class);


  private final FlightService flightService;
  
  @Autowired
  public FlightController(FlightService flightService) {
    this.flightService = flightService;
  }
  
  @GetMapping("/api/flights")
  public List<Flight> getFlight(@RequestParam(required=false) Integer id) {
    if (id != null) {
      return flightService.findFlightById(id);
    }
    
    return flightService.getAllFlights();
  }
  
  @PostMapping("/api/search")
  public List<Itinerary> searchFlights(@RequestBody SearchInfo searchInfo) {
    return flightService.searchFlights(searchInfo.getDate(), searchInfo.getOriginId(), searchInfo.getDestinationId());
  }
}
