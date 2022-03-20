package com.investree.demo.repository;

import com.investree.demo.model.Users;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends PagingAndSortingRepository<Users, Long> {

  @Query("SELECT u FROM users u WHERE u.username = :username")
  Optional<Users> findByUsername(
      @Param("username") String username
  );

}
