package com.brilam.flyte.login;

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
   * Returns an authentication status after authenticating the user with 
   * the given login information.
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
  
  /**
   * Returns a registration status after registering the user with the
   * given registeration information.
   * @param registerInfo register information (email, user, personal details)
   * @return if registration was successful
   */
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
