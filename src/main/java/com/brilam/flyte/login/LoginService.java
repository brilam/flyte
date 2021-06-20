package com.brilam.flyte.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import at.favre.lib.crypto.bcrypt.BCrypt;

@Service
public class LoginService {
  private final UserRepository userRepository;

  
  @Autowired
  public LoginService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  
  /**
   * Authenticates the user with the given login information.
   * @param loginInfo login information (contains username and password)
   * @return if login was successful
   */
  public AuthenticationStatus login(LoginInformation loginInfo) {
    String hashedPassword = userRepository.getHashedPasswordByUsername(loginInfo.getUsername());
    
    boolean isMatchingPassword = false;
    if (hashedPassword == null) {
      return new AuthenticationStatus(false);
    } else {
      isMatchingPassword =
          BCrypt.verifyer().verify(loginInfo.getPassword().toCharArray(), hashedPassword).verified;
    }

    return new AuthenticationStatus(isMatchingPassword);
  }
  
  public RegistrationStatus register(RegisterInformation registerInfo) {
    boolean isUserExists = userRepository.isUserExists(registerInfo.getUsername(), registerInfo.getEmail()) > 0;
    if (isUserExists) {
      return new RegistrationStatus(false);
    } else {
      String hashedPassword = BCrypt.withDefaults().hashToString(12, registerInfo.getPassword().toCharArray());
      User user = new User(registerInfo.getFirstName(), registerInfo.getLastName(), registerInfo.getUsername(), hashedPassword, registerInfo.getEmail());
      userRepository.save(user);
      return new RegistrationStatus(true);
    }
  }
}
