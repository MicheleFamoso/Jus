package com.Michele.Jus.Repository;

import com.Michele.Jus.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    public Optional<User> findByUsername(String username);
}
