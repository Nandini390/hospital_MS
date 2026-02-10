package org.example.hospitalmanagementsystem_securitylearning.Repository;

import org.example.hospitalmanagementsystem_securitylearning.Entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
