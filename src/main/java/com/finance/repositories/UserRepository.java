package com.finance.repositories;

import java.util.Optional;

import com.finance.entities.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {

  @Query("SELECT u from UserEntity u where u.userName= :userName")
  Optional<UserEntity> getUser(String userName);
}
