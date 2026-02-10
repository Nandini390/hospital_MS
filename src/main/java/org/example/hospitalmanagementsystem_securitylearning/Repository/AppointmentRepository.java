package org.example.hospitalmanagementsystem_securitylearning.Repository;

import lombok.RequiredArgsConstructor;
import org.example.hospitalmanagementsystem_securitylearning.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
}
