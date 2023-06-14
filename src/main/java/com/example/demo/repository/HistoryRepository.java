package com.example.demo.repository;

import com.example.demo.Entity.Job_History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<Job_History, Long> {

    List<Job_History> findAllByEmployeeId(Long id);

}
