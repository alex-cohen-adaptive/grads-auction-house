package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.user.AuctionUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<AuctionUser, Integer> {
  //applied to the entities in the object and not the one in the DB
  //
  @Query(
      value = "SELECT u FROM auctionuser u WHERE u.username = :username AND u.password = :password"
  )
  Optional<AuctionUser> validate(@Param("username") String username, @Param("password") String password);

  boolean existsByUsername(String username);

  boolean existsById(Integer integer);

  Optional<AuctionUser> getByUsername(String username);

  @Modifying
  @Transactional
  @Query(
      value = "UPDATE auctionuser  u "
          + "SET  u.firstName = :firstName, "
          + "u.lastName = :lastName, "
          + "u.organization = :organization "
          + "WHERE u.id = :id")
  int updateUserFirstNameLastNameOrganizationByUserId(
      @Param("id") int id,
      @Param("firstName") String firstName,
      @Param("lastName") String lastName,
      @Param("organization") String organization);

  @Modifying(flushAutomatically = true)
  @Transactional
  @Query(
      value = "UPDATE auctionuser  u "
          + "SET  u.isBlocked = true "
          + "WHERE u.id = :id")
  int blockUser(
      @Param("id") int id);


  @Modifying(flushAutomatically = true)
  @Transactional
  @Query(
      value = "UPDATE auctionuser  u "
          + "SET  u.isBlocked = false "
          + "WHERE u.id = :id")
  int unblockUser(
      @Param("id") int id);


}
