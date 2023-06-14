package com.example.demo.repository;

import com.example.demo.Entity.Jobs;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JobsRepository extends JpaRepository<Jobs, Long> {
    Optional<Jobs> findByJobId(String name);
}
