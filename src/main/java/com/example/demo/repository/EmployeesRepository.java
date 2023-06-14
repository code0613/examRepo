package com.example.demo.repository;

import com.example.demo.Entity.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EmployeesRepository extends JpaRepository<Employees, Long> {

    List<Employees> findAllByJobId(String jobId);
}
