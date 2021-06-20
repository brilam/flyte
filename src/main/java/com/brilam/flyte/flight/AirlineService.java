package com.brilam.flyte.flight;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {
  private final AirlineRepository airlineRepository;
  
  @Autowired
  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }
  
  public Set<Airline> findAirlineById(int id) {
    return airlineRepository.findById(id);
  }
  
  public Set<Airline> findAirlineByName(String name) {
    return airlineRepository.findByName(name);
  }
  
  public Set<Airline> getAllAirlines() {
    return airlineRepository.findAll();
  }
}
