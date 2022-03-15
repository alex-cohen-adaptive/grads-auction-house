package com.weareadaptive.auction.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "auctionuser")
public class AuctionUser {

  private String username;

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private int id;

  private String firstName;

  private String lastName;

  private String password;

  private String organization;

  private boolean isBlocked;

  private boolean isAdmin;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getOrganization() {
    return organization;
  }


  public boolean isBlocked() {
    return isBlocked;
  }

  public void block() {
    isBlocked = true;
  }

  public void unblock() {
    isBlocked = true;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean admin) {
    isAdmin = admin;
  }

  @Override
  public String toString() {
    return "User{"
        + "username='" + username + '\''
        + ", userid=" + id
        + ", firstName='" + firstName + '\''
        + ", lastName='" + lastName + '\''
        + ", password='" + password + '\''
        + ", organization='" + organization + '\''
        + ", isBlocked=" + isBlocked
        + ", isAdmin=" + isAdmin
        + '}';
  }


}