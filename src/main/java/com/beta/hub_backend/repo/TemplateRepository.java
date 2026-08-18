package com.beta.hub_backend.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.beta.hub_backend.dto.SearchResultItem;
import com.beta.hub_backend.entity.Event;
import com.beta.hub_backend.entity.Template;
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findByCategory(String category);

    List<Template> findByTitleContainingIgnoreCase(String title);
}