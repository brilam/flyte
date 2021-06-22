package com.brilam.flyte.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.brilam.flyte.flight.FlightService;
import com.brilam.flyte.flight.LocationService;

@Controller
public class IndexController {
  private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);
  private final LocationService locationService;
  private final FlightService flightService;
  
  @Autowired
  public IndexController(LocationService locationService, FlightService flightService) {
    this.locationService = locationService;
    this.flightService = flightService;
  }

  @GetMapping("/locations")
  public String showLocations(Model model) {
    model.addAttribute("locations", locationService.getAllLocations());
    return "locations";
  }
  
  @PostMapping("/search")
  public String sendsearch(Model model) {
    LOGGER.info("is flight null" + (flightService == null));
    return "index";
  }
}
