// src/main/java/com/example/adminservice/BusRouteModelRepository.java
package com.example.adminservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BusRouteModelRepository extends JpaRepository<BusRouteModel, Integer> {
    List<BusRouteModel> findBySourceAndDestination(String source, String destination);

    @Transactional
    void deleteBySourceAndDestination(String source, String destination);
}
