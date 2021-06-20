package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LocationRepository extends CrudRepository<Location, Integer> {
  List<Location> findById(int id);
  
  List<Location> findByName(String name);
  
  List<Location> findAll();
}
