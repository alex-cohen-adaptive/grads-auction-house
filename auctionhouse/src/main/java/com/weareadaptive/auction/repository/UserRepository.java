package com.weareadaptive.auction.repository;

import com.weareadaptive.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //applied to the entities in the object and not the one in the DB
    //
    @Query(
            "select u from user where u.username=?1 and u.password=?2"
    )
    public Optional<User> validate( String username,  String password);
}
