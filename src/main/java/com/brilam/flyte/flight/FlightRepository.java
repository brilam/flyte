package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Integer> {
  List<Flight> findById(int id);
  
  List<Flight> findByOriginAndDestination(String origin, String destination);
  
  List<Flight> findAll();
}
