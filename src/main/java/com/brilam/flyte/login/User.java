package com.brilam.flyte.login;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "users")
public class User {
  
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private long id;
  private String firstName;
  private String lastName;
  private String username;
  private String password;
  private Role role;
  private String email;
  
  public User(String firstName, String lastName, String username, String password, String email) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.username = username;
    this.password = password;
    this.role = Role.CLIENT;
    this.email = email;
  }
  
  public long getId() {
    return id;
  }
  
  public void setId(long id)  {
    this.id = id;
  }
  
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  
  public String getFirstName() {
    return firstName;
  }
  
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getLastName() {
    return lastName;
  }
  
  public void setUsername(String username) {
    this.username = username;
  }
  
  public String getUsername() {
    return username;
  }
  
  public void setPassword(String password) {
    this.password = password;
  }
  
  public String getPassword() {
    return password;
  }
  
  public void setRole(Role role) {
    this.role = role;
  }
  
  public Role getRole() {
    return role;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getEmail() {
    return email;
  }
  
  @Override
  public String toString() {
    return String.format("%s %s", firstName, lastName);
  }
}


