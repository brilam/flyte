package com.brilam.flyte.flight;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
  Set<Flight> findById(int id);
  
  Set<Flight> findByOriginAndDestination(String origin, String destination);
  
  Set<Flight> findAll();
}
