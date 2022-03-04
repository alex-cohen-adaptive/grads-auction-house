package com.weareadaptive.auction.service;

import com.weareadaptive.auction.model.state.UserState;
import com.weareadaptive.auction.model.user.User;
import java.util.Objects;
import java.util.stream.Stream;
import org.springframework.stereotype.Service;


@Service
public class UserService {
  private final UserState userState;

  public UserService(UserState userState) {
    this.userState = userState;
  }


  public User create(String username, String password, String firstName, String lastName,
                     String organisation) {

    User user = new User(userState.nextId(), username, password, firstName, lastName, organisation);
    userState.add(user);
    return user;
  }

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
  }
}
