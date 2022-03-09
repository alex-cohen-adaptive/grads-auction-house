package com.weareadaptive.auction.service;

import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userState = userRepository;
  }

  public User create(String username, String password, String firstName, String lastName,
                     String organisation) {
    throw new UnsupportedOperationException();
  }

  p
}
