package com.weareadaptive.auction.service;

import com.weareadaptive.auction.dto.request.CreateUserRequest;
import com.weareadaptive.auction.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.weareadaptive.auction.model.user.User;

import java.util.Objects;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User create(CreateUserRequest userRequest) {

        var user = User.builder()
                .firstName(userRequest.firstName())
                .lastName(userRequest.lastName())
                .organization(userRequest.organisation())
                .password(userRequest.password())
                .build();
        userRepository.save(user);
        return user;
    }

    }
/*
  public Stream<User> getAll() {
    return userState.stream();
  }

  public User getUser(int id) {
    User user = userState.get(id);
    return Objects.requireNonNull(user);
  }

  public User editUser(int id, String firstname, String lastname, String organization) {
    User user = userState.get(id);
    user.setFirstName(firstname);
    user.setLastName(lastname);
    user.setOrganisation(organization);
    return Objects
      .requireNonNull(user);
  }

  public void block(int id) {
    userState.get(id).block();
  }

  public void unblock(int id) {
    userState.get(id).unblock();
  }*/

}
