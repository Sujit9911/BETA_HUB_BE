package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.TeamMemberRequest;
import com.beta.hub_backend.dto.TeamMemberResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface TeamService {

    TeamMemberResponse addMember(TeamMemberRequest request, MultipartFile photo) throws IOException;

    List<TeamMemberResponse> getMembersByYear(String yearLabel);

    List<String> getAllYearLabels();

    TeamMemberResponse updateMember(Long id, TeamMemberRequest request, MultipartFile photo) throws IOException;

    void deleteMember(Long id) throws IOException;
}