package com.weareadaptive.auction.controller;

import com.weareadaptive.auction.dto.request.CreateUserRequest;
import com.weareadaptive.auction.dto.request.UpdateUserRequest;
import com.weareadaptive.auction.dto.response.UserResponse;
import com.weareadaptive.auction.model.User;
import com.weareadaptive.auction.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Stream;

import static com.weareadaptive.auction.controller.Mapper.map;

@RestController
@RequestMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserResponse create(@RequestBody @Valid CreateUserRequest user) {
    User newUser = userService.create(user.username(), user.password(),
      user.firstName(), user.lastName(), user.organisation());
    return Mapper.map(newUser);
  }

  @GetMapping("{id}")
  public UserResponse getUser(@PathVariable @Valid int id) {
    return Mapper.map(userService.getUser(id));
  }

  @PutMapping("{id}")
  public UserResponse edit(@PathVariable @Valid int id, @RequestBody @Valid UpdateUserRequest updateUserRequest) {
    return Mapper.map(
      userService.editUser(
        id,
        updateUserRequest.firstName(),
        updateUserRequest.lastName(),
        updateUserRequest.organisation()));
  }

  @GetMapping
  public Stream<UserResponse> getUsers() {
    return userService.getAll()
      .map(Mapper::map);
  }

  @PutMapping("{id}/block")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void block(@PathVariable("id") @Valid int id) {
    userService.block(id);
  }

  @PutMapping("{id}/unblock")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void unblock(@PathVariable("id") @Valid int id) {
    userService.unblock(id);
  }

}
