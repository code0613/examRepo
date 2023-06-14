package com.example.demo.repository;

import com.example.demo.Entity.Job_History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<Job_History, Long> {

}
