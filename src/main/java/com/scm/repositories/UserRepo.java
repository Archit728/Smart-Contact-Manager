package com.scm.repositories;

import com.scm.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo
  extends JpaRepository<User, String> { //working with what entity and id of that entity
  //extra db related methods
  //custom query/finder methods can be written too
  /* Spring Data JPA automatically provides the implementation for you at runtime based on the method name. 
    This is a powerful feature called query method derivation, where Spring Data JPA parses the method name and generates the appropriate query behind the scenes. 
    Query Derivation:
    The method name findByEmail follows a specific naming convention that Spring Data JPA understands.
    The prefix findBy indicates that you want to retrieve data (in this case, an Optional<User>) based on a condition.
    The suffix Email corresponds to the field in your User entity (i.e., the email field).*/
  Optional<User> findByEmail(String email);
}
