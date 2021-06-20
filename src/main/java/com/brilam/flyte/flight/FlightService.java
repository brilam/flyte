package com.brilam.flyte.flight;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
  private FlightRepository flightRepository;
  
  @Autowired
  public FlightService(FlightRepository flightRepository) {
    this.flightRepository = flightRepository;
  }
  
  public Set<Flight> findFlightById(int id) {
    return flightRepository.findById(id);
  }
  
  public Set<Flight> getAllFlights() {
    return flightRepository.findAll();
  }
}
