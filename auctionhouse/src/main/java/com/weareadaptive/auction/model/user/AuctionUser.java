package com.weareadaptive.auction.model.user;

import com.weareadaptive.auction.model.auction.Auction;
import java.util.Set;
import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "auctionuser")
public class AuctionUser {

  @Column(name = "username")
  private String username;

  @Id
  @GeneratedValue(
      strategy = GenerationType.IDENTITY
  )
  private int id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "password")
  private String password;

  @Column(name = "organization")
  private String organization;

  @Column(name = "is_blocked")
  private boolean isBlocked;

  @Column(name = "is_admin")
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

  public void setOrganization(String organization) {
    this.organization = organization;
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