package com.weareadaptive.auction.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.Set;

import static org.apache.logging.log4j.util.Strings.isBlank;

@javax.persistence.Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "user",
        uniqueConstraints =
        @UniqueConstraint(
                name = "username_unique",
                columnNames = "username"
        )
)
public class User {

/*
  public User(
      int id,
      String username,
      String password,
      String firstName,
      String lastName,
      String organisation) {
    this(id, username, password, firstName, lastName, organisation, false);
  }

  public User(
      int id,
      String username,
      String password,
      String firstName,
      String lastName,
      String organisation,
      boolean isAdmin) {
    if (isBlank(username)) {
      throw new BusinessException("username cannot be null or empty");
    }
    if (isBlank(password)) {
      throw new BusinessException("password cannot be null or empty");
    }
    if (isBlank(firstName)) {
      throw new BusinessException("firstName cannot be null or empty");
    }
    if (isBlank(lastName)) {
      throw new BusinessException("lastName cannot be null or empty");
    }
    if (isBlank(organisation)) {
      throw new BusinessException("organisation cannot be null or empty");
    }
    //TODO: Add regex for email
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.organisation = organisation;
    this.isAdmin = isAdmin;
  }*/

    @Column(
            name = "username",
            nullable = false
    )
    private String username;

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.IDENTITY,
            generator = "organization_sequence"
    )
    private Long userid;

    @Column(
            nullable = false,
            name="first_name"
    )
    private String firstName;

    @Column(
            nullable = false,
            name="last_name"
    )
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String organization;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
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

/*
    @OneToMany(mappedBy = "user")
    private Set<Bid> bids;*/
}
