package com.beta.hub_backend.repo;

import com.beta.hub_backend.entity.Alumni;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlumniRepository extends JpaRepository<Alumni, Long> {
	List<Alumni> findByNameContainingIgnoreCase(String name);
    List<Alumni> findByBatch(String batch);

    List<Alumni> findByDomainContainingIgnoreCase(String domain);

    List<Alumni> findByCompanyContainingIgnoreCase(String company);
}