package org.example.hospitalmanagementsystem_securitylearning.Repository;

import org.example.hospitalmanagementsystem_securitylearning.Entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {
}
