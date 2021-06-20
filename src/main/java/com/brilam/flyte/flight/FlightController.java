package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FlightController {
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
}
