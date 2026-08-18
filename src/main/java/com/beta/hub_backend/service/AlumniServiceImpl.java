package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AlumniRequest;
import com.beta.hub_backend.dto.AlumniResponse;
import com.beta.hub_backend.entity.Alumni;
import com.beta.hub_backend.repo.AlumniRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlumniServiceImpl implements AlumniService {

    private final AlumniRepository alumniRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public AlumniResponse addAlumni(AlumniRequest request, MultipartFile photo) throws IOException {

        Alumni alumni = new Alumni();
        alumni.setName(request.getName());
        alumni.setBatch(request.getBatch());
        alumni.setDomain(request.getDomain());
        alumni.setContactNumber(request.getContactNumber());
        alumni.setEmail(request.getEmail());
        alumni.setCompany(request.getCompany());

        if (photo != null && !photo.isEmpty()) {
            Map<String, String> uploadResult = cloudinaryService.uploadFile(photo, "alumni");
            alumni.setPhotoUrl(uploadResult.get("url"));
            alumni.setPhotoPublicId(uploadResult.get("publicId"));
        }

        Alumni saved = alumniRepository.save(alumni);
        return toResponse(saved);
    }

    @Override
    public List<AlumniResponse> getAllAlumni() {
        return alumniRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AlumniResponse getAlumniById(Long id) {
        Alumni alumni = alumniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumni not found with id: " + id));
        return toResponse(alumni);
    }

    @Override
    public List<AlumniResponse> filterByBatch(String batch) {
        return alumniRepository.findByBatch(batch)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlumniResponse> filterByDomain(String domain) {
        return alumniRepository.findByDomainContainingIgnoreCase(domain)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AlumniResponse> filterByCompany(String company) {
        return alumniRepository.findByCompanyContainingIgnoreCase(company)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public AlumniResponse updateAlumni(Long id, AlumniRequest request, MultipartFile photo) throws IOException {

        Alumni alumni = alumniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumni not found with id: " + id));

        alumni.setName(request.getName());
        alumni.setBatch(request.getBatch());
        alumni.setDomain(request.getDomain());
        alumni.setContactNumber(request.getContactNumber());
        alumni.setEmail(request.getEmail());
        alumni.setCompany(request.getCompany());

        if (photo != null && !photo.isEmpty()) {
            if (alumni.getPhotoPublicId() != null) {
                cloudinaryService.deleteFile(alumni.getPhotoPublicId());
            }
            Map<String, String> uploadResult = cloudinaryService.uploadFile(photo, "alumni");
            alumni.setPhotoUrl(uploadResult.get("url"));
            alumni.setPhotoPublicId(uploadResult.get("publicId"));
        }

        Alumni updated = alumniRepository.save(alumni);
        return toResponse(updated);
    }

    @Override
    public void deleteAlumni(Long id) throws IOException {
        Alumni alumni = alumniRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumni not found with id: " + id));

        if (alumni.getPhotoPublicId() != null) {
            cloudinaryService.deleteFile(alumni.getPhotoPublicId());
        }
        alumniRepository.delete(alumni);
    }

    private AlumniResponse toResponse(Alumni alumni) {
        return new AlumniResponse(
                alumni.getId(),
                alumni.getName(),
                alumni.getBatch(),
                alumni.getDomain(),
                alumni.getContactNumber(),
                alumni.getEmail(),
                alumni.getCompany(),
                alumni.getPhotoUrl()
        );
    }
}