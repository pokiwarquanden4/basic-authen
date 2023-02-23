package com.example.basicauthen.repository;

import com.example.basicauthen.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlanRepository extends JpaRepository<Plan,Long> {
}
