package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.AlumniRequest;
import com.beta.hub_backend.dto.AlumniResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AlumniService {

    AlumniResponse addAlumni(AlumniRequest request, MultipartFile photo) throws IOException;

    List<AlumniResponse> getAllAlumni();

    AlumniResponse getAlumniById(Long id);

    List<AlumniResponse> filterByBatch(String batch);

    List<AlumniResponse> filterByDomain(String domain);

    List<AlumniResponse> filterByCompany(String company);

    AlumniResponse updateAlumni(Long id, AlumniRequest request, MultipartFile photo) throws IOException;

    void deleteAlumni(Long id) throws IOException;
}