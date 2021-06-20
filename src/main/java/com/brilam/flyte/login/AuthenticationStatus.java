package com.brilam.flyte.login;

/**
 * This class is used to present the authentication
 * status for login.
 * @author Brian Lam
 */
public class AuthenticationStatus {
  private boolean isSuccess;
  
  public AuthenticationStatus(boolean isSuccess) {
    this.isSuccess = isSuccess;
  }
  
  public boolean isSuccess() {
    return isSuccess;
  }
}
