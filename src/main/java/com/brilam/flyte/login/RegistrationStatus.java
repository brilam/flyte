package com.brilam.flyte.login;

public class RegistrationStatus {
  private boolean isSuccess;
  
  public RegistrationStatus(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
  
  public boolean isSuccess() {
    return isSuccess;
  }
}
