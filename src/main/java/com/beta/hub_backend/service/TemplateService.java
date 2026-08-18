package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.TemplateRequest;
import com.beta.hub_backend.dto.TemplateResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TemplateService {

    TemplateResponse createTemplate(TemplateRequest request, MultipartFile file) throws IOException;

    List<TemplateResponse> getAllTemplates();

    TemplateResponse getTemplateById(Long id);

    List<TemplateResponse> getTemplatesByCategory(String category);

    TemplateResponse updateTemplate(Long id, TemplateRequest request, MultipartFile file) throws IOException;

    void deleteTemplate(Long id) throws IOException;
}