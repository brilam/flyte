package com.brilam.flyte.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController { 
  private final LoginService loginService;
 
  @Autowired
  public LoginController(LoginService loginService) {
    this.loginService = loginService;
  }
  
  
  @PostMapping("/api/login")
  @ResponseBody
  public AuthenticationStatus login(@RequestBody LoginInformation loginInfo) {
    return loginService.login(loginInfo);
  }
  
  @PostMapping("/api/register")
  @ResponseBody
  public RegistrationStatus register(@RequestBody RegisterInformation registerInfo) {
   return loginService.register(registerInfo);
  }
}
