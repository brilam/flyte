package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface AirlineRepository extends CrudRepository<Airline, Integer> {
  List<Airline> findById(int id);
  
  List<Airline> findByName(String name);
  
  List<Airline> findAll();
}
