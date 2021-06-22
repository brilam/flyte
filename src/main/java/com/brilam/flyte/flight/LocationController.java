package com.brilam.flyte.flight;

import java.util.ArrayList;
import java.util.Arrays;
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
  public List<Location> getLocation(@RequestParam(required=false) Integer id, @RequestParam(required=false) String names) {
    
    LOGGER.info("ID: " + id);
    LOGGER.info("Name: " + names);

    if (id != null) {
      return locationService.findLocationById(id);
    }
    
    if (names != null) {
      String[] locationNames = names.split(",");
      LOGGER.info("Array:" + Arrays.toString(locationNames));
      List<Location> locations = new ArrayList<>();
      for (String locationName: locationNames) {
        LOGGER.info("Adding:"  + locationService.findLocationByName(locationName).toString());
        locations.addAll(locationService.findLocationByName(locationName));
      }
      LOGGER.info(locations.toString());
      return locations;
    }
   
    // Fallback for nothing provided or both optional parameters provided
    return locationService.getAllLocations();
  }
}
