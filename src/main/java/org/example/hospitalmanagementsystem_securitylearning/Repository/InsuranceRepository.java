package org.example.hospitalmanagementsystem_securitylearning.Repository;

import org.example.hospitalmanagementsystem_securitylearning.Entity.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance,Long> {
}
