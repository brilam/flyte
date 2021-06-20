package com.brilam.flyte.login;

public enum Role {
  CLIENT(0), 
  AIRLINE(1), 
  FLYTE_EMPLOYEE(1);

  private int role = -1;

  private Role(int role) {
    this.role = role;
  }

  public int getRole() {
    return role;
  }
}
