package com.brilam.flyte.flight;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService {
  private final LocationRepository locationRepository;
  
  @Autowired
  public LocationService(LocationRepository locationRepository) {
    this.locationRepository = locationRepository;
  }
  
  public List<Location> findLocationById(int id) {
    return locationRepository.findById(id);
  }
  
  public List<Location> findLocationByName(String name) {
    return locationRepository.findByName(name);
  }
  
  public List<Location> getAllLocations() {
    return locationRepository.findAll();
  }
}
