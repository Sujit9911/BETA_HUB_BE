package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.TemplateRequest;
import com.beta.hub_backend.dto.TemplateResponse;
import com.beta.hub_backend.entity.Template;
import com.beta.hub_backend.repo.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public TemplateResponse createTemplate(TemplateRequest request, MultipartFile file) throws IOException {

        Map<String, String> uploadResult = cloudinaryService.uploadFile(file, "templates");

        Template template = new Template();
        template.setTitle(request.getTitle());
        template.setDescription(request.getDescription());
        template.setCategory(request.getCategory());
        template.setFileUrl(uploadResult.get("url"));
        template.setFilePublicId(uploadResult.get("publicId"));

        Template saved = templateRepository.save(template);
        return toResponse(saved);
    }

    @Override
    public List<TemplateResponse> getAllTemplates() {
        return templateRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TemplateResponse getTemplateById(Long id) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + id));
        return toResponse(template);
    }

    @Override
    public List<TemplateResponse> getTemplatesByCategory(String category) {
        return templateRepository.findByCategory(category)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TemplateResponse updateTemplate(Long id, TemplateRequest request, MultipartFile file) throws IOException {

        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + id));

        template.setTitle(request.getTitle());
        template.setDescription(request.getDescription());
        template.setCategory(request.getCategory());

        // Only replace the file if a new one was actually uploaded
        if (file != null && !file.isEmpty()) {
            cloudinaryService.deleteFile(template.getFilePublicId());
            Map<String, String> uploadResult = cloudinaryService.uploadFile(file, "templates");
            template.setFileUrl(uploadResult.get("url"));
            template.setFilePublicId(uploadResult.get("publicId"));
        }

        Template updated = templateRepository.save(template);
        return toResponse(updated);
    }

    @Override
    public void deleteTemplate(Long id) throws IOException {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with id: " + id));

        cloudinaryService.deleteFile(template.getFilePublicId());
        templateRepository.delete(template);
    }

    private TemplateResponse toResponse(Template template) {
        return new TemplateResponse(
                template.getId(),
                template.getTitle(),
                template.getDescription(),
                template.getCategory(),
                template.getFileUrl(),
                template.getCreatedAt()
        );
    }
}