package com.brilam.flyte.flight;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

public interface AirlineRepository extends CrudRepository<Airline, Integer> {
  Set<Airline> findById(int id);
  
  Set<Airline> findByName(String name);
  
  Set<Airline> findAll();
}
