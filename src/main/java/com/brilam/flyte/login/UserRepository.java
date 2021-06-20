package com.brilam.flyte.login;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
  // No need to be implemented as Spring will auto implement it
  
  @Query("SELECT u.password FROM users u where u.username = ?1")
  String getHashedPasswordByUsername(String username);
  
  @Query("SELECT COUNT(u) FROM users u where u.username = ?1 OR u.email = ?2")
  int isUserExists(String username, String email);
}
