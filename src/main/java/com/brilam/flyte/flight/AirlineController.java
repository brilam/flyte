package com.brilam.flyte.flight;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AirlineController {
  private final AirlineService airlineService;
  
  @Autowired
  public AirlineController(AirlineService airlineService) {
    this.airlineService = airlineService;
  }
  
  @GetMapping("/api/airlines")
  public Set<Airline> getAirline(@RequestParam(required=false) Integer id, @RequestParam(required=false) String name) {
    if (id != null) {
      return airlineService.findAirlineById(id);
    }
    
    if (name != null) {
      return airlineService.findAirlineByName(name);
    }
    
    // Fallback for nothing provided or both optional parameters provided
    return airlineService.getAllAirlines();
  }
}
