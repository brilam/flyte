package com.brilam.flyte.flight;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LocationController {
  private final LocationService locationService;
  private static final Logger LOGGER = LoggerFactory.getLogger(LocationController.class);
  
  @Autowired
  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }
  
  @GetMapping("/api/locations")
  public List<Location> getLocation(@RequestParam(required=false) Integer id) {
    
    LOGGER.info("ID: " + id);
    if (id == null) {
      return locationService.getAllLocations();
    } else {
      return locationService.findLocationById(id);
    }
  }
}
