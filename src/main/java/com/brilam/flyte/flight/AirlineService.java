package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {
  private final AirlineRepository airlineRepository;
  
  @Autowired
  public AirlineService(AirlineRepository airlineRepository) {
    this.airlineRepository = airlineRepository;
  }
  
  public List<Airline> findAirlineById(int id) {
    return airlineRepository.findById(id);
  }
  
  public List<Airline> findAirlineByName(String name) {
    return airlineRepository.findByName(name);
  }
  
  public List<Airline> getAllAirlines() {
    return airlineRepository.findAll();
  }
}
