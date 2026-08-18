package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    List<TeamMember> findByNameContainingIgnoreCase(String name);

    List<TeamMember> findByAcademicYearId(Long academicYearId);

    boolean existsByAcademicYearId(Long academicYearId);
}