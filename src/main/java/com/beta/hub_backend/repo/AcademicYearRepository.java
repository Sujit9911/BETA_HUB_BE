package com.beta.hub_backend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beta.hub_backend.entity.AcademicYear;
@Repository
public interface AcademicYearRepository extends JpaRepository<AcademicYear, Long> {

    Optional<AcademicYear> findByYearLabel(String yearLabel);

    boolean existsByYearLabel(String yearLabel);
}