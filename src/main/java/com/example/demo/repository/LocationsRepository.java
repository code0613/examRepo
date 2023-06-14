package com.example.demo.repository;

import com.example.demo.Entity.Locations;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationsRepository  extends JpaRepository<Locations, Long> {
}
