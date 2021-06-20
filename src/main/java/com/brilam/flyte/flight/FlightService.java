package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
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
}
