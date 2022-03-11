package com.weareadaptive.auction.service;

import com.weareadaptive.auction.dto.request.CreateUserRequest;
import com.weareadaptive.auction.dto.request.UpdateUserRequest;
import com.weareadaptive.auction.exception.BadRequestException;
import com.weareadaptive.auction.exception.NotAllowedException;
import com.weareadaptive.auction.exception.NotFoundException;
import com.weareadaptive.auction.model.user.AuctionUser;
import com.weareadaptive.auction.repository.UserRepository;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//todo combine get user in a separte class
//todo testing get from repository for checks if data correctly added
// todo refactor repository methods for specific queries only
//todo overall refactoring
//todo refactor test data class
//todo refactor instant errors for my tables dbms

@Service
public class UserService {
  @Autowired
  private UserRepository userRepository;


  public AuctionUser create(CreateUserRequest userRequest) {
    if (userRepository.existsByUsername(userRequest.username())) {
      throw new BadRequestException("User already exists!");
    }
    AuctionUser auctionUser = AuctionUser.builder()
        .username(userRequest.username())
        .firstName(userRequest.firstName())
        .lastName(userRequest.lastName())
        .organization(userRequest.organisation())
        .password(userRequest.password())
        .build();
    userRepository.save(auctionUser);
    return auctionUser;
  }

  public AuctionUser validate(String username, String password) {
    var validated = userRepository.validate(username, password);
    if (validated.isEmpty()) {
      throw new NotAllowedException("Invalid Username or Password!");
    }
    return validated.get();
  }

  public Stream<AuctionUser> getAll() {
    return userRepository.findAll().stream();
  }

  public AuctionUser getUser(int id) {
    var user = userRepository.findById(id);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found!");
    }
    return user.get();
  }

  public AuctionUser getUser(String username) {
    var user = userRepository.getByUsername(username);
    if (user.isEmpty()) {
      throw new NotFoundException("User not found!");
    }
    return user.get();
  }

  public void editUser(int id, UpdateUserRequest updateUserRequest) {
    getUser(id);
    userRepository.updateUserFirstNameLastNameOrganizationByUserId(
        id,
        updateUserRequest.firstName(),
        updateUserRequest.lastName(),
        updateUserRequest.organisation());
  }


  private void isAdmin(AuctionUser auctionUser) {
    if (auctionUser.isAdmin()) {
      throw new NotFoundException("Cannot block/unblock admin!");
    }
  }

  public void block(int id) {
    var user = getUser(id);
    isAdmin(user);
    if (user.isBlocked()) {
      throw new BadRequestException("User already blocked!");
    }
    userRepository.blockUser(id);
  }

  public void unblock(int id) {
    var user = getUser(id);
    isAdmin(user);
    if (!user.isBlocked()) {
      throw new BadRequestException("User already unblocked!");
    }
    userRepository.unblockUser(id);
  }
}
