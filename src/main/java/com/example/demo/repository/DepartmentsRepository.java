package com.example.demo.repository;

import com.example.demo.Entity.Departments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentsRepository  extends JpaRepository<Departments, Long> {
}
