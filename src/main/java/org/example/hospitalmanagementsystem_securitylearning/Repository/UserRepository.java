package org.example.hospitalmanagementsystem_securitylearning.Repository;

import org.example.hospitalmanagementsystem_securitylearning.Entity.Type.AuthProviderType;
import org.example.hospitalmanagementsystem_securitylearning.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByProviderIdAndProviderType(String providerId, AuthProviderType providerType);
}