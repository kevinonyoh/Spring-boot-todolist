
package com.todolist.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    UserModel save(UserModel user);

    UserModel findByEmail(String email);

    boolean existsByEmail(String email);

}