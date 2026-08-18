package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.TeamMemberRequest;
import com.beta.hub_backend.dto.TeamMemberResponse;
import com.beta.hub_backend.entity.AcademicYear;
import com.beta.hub_backend.entity.TeamMember;
import com.beta.hub_backend.entity.User;
import com.beta.hub_backend.repo.AcademicYearRepository;
import com.beta.hub_backend.repo.TeamMemberRepository;
import com.beta.hub_backend.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TeamServiceImpl implements TeamService {

    private final TeamMemberRepository teamMemberRepository;
    private final AcademicYearRepository academicYearRepository;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public TeamMemberResponse addMember(
            TeamMemberRequest request,
            MultipartFile photo
    ) throws IOException {

        String email = request.getEmail().trim().toLowerCase();

        User user = userRepository
                .findByEmailIgnoreCase(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "No registered BETA user found with email: " + email
                        )
                );

        AcademicYear academicYear =
                academicYearRepository
                        .findByYearLabel(request.getAcademicYearLabel())
                        .orElseGet(() -> {
                            AcademicYear newYear = new AcademicYear();
                            newYear.setYearLabel(request.getAcademicYearLabel());
                            return academicYearRepository.save(newYear);
                        });

        TeamMember member = new TeamMember();

        member.setName(request.getName());
        member.setEmail(email);
        member.setDesignation(request.getDesignation());
        member.setBranch(request.getBranch());
        member.setYear(request.getYear());
        member.setPassingYear(request.getPassingYear());
        member.setAcademicYear(academicYear);

        if (photo != null && !photo.isEmpty()) {
            Map<String, String> uploadResult =
                    cloudinaryService.uploadFile(photo, "team");

            member.setPhotoUrl(uploadResult.get("url"));
            member.setPhotoPublicId(uploadResult.get("publicId"));
        }

        user.setName(request.getName());
        user.setEmail(email);
        user.setBranch(request.getBranch());
        user.setYear(request.getYear());

        userRepository.save(user);

        TeamMember saved = teamMemberRepository.save(member);

        return toResponse(saved);
    }

    @Override
    public List<TeamMemberResponse> getMembersByYear(String yearLabel) {

        AcademicYear academicYear =
                academicYearRepository
                        .findByYearLabel(yearLabel)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Academic year not found: " + yearLabel
                                )
                        );

        return teamMemberRepository
                .findByAcademicYearId(academicYear.getId())
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllYearLabels() {

        return academicYearRepository
                .findAll()
                .stream()
                .map(AcademicYear::getYearLabel)
                .collect(Collectors.toList());
    }

    @Override
    public TeamMemberResponse updateMember(
            Long id,
            TeamMemberRequest request,
            MultipartFile photo
    ) throws IOException {

        TeamMember member =
                teamMemberRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Team member not found with id: " + id
                                )
                        );

        String oldEmail = member.getEmail();
        String newEmail = request.getEmail().trim().toLowerCase();

        User user =
                userRepository
                        .findByEmailIgnoreCase(oldEmail)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Registered BETA user not found for team member: "
                                                + oldEmail
                                )
                        );

        if (!oldEmail.equalsIgnoreCase(newEmail)) {

            userRepository
                    .findByEmailIgnoreCase(newEmail)
                    .ifPresent(existingUser -> {
                        if (!existingUser.getId().equals(user.getId())) {
                            throw new RuntimeException(
                                    "Another user already exists with email: "
                                            + newEmail
                            );
                        }
                    });
        }

        AcademicYear academicYear =
                academicYearRepository
                        .findByYearLabel(request.getAcademicYearLabel())
                        .orElseGet(() -> {
                            AcademicYear newYear = new AcademicYear();
                            newYear.setYearLabel(request.getAcademicYearLabel());
                            return academicYearRepository.save(newYear);
                        });

        member.setName(request.getName());
        member.setEmail(newEmail);
        member.setDesignation(request.getDesignation());
        member.setBranch(request.getBranch());
        member.setYear(request.getYear());
        member.setPassingYear(request.getPassingYear());
        member.setAcademicYear(academicYear);

        if (photo != null && !photo.isEmpty()) {

            if (member.getPhotoPublicId() != null) {
                cloudinaryService.deleteFile(
                        member.getPhotoPublicId()
                );
            }

            Map<String, String> uploadResult =
                    cloudinaryService.uploadFile(photo, "team");

            member.setPhotoUrl(uploadResult.get("url"));
            member.setPhotoPublicId(uploadResult.get("publicId"));
        }

        user.setName(request.getName());
        user.setEmail(newEmail);
        user.setBranch(request.getBranch());
        user.setYear(request.getYear());

        userRepository.save(user);

        TeamMember updated =
                teamMemberRepository.save(member);

        return toResponse(updated);
    }

    @Override
    public void deleteMember(Long id) throws IOException {

        TeamMember member =
                teamMemberRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Team member not found with id: " + id
                                )
                        );

        if (member.getPhotoPublicId() != null) {
            cloudinaryService.deleteFile(
                    member.getPhotoPublicId()
            );
        }

        teamMemberRepository.delete(member);
    }

    private TeamMemberResponse toResponse(TeamMember member) {

        return new TeamMemberResponse(
                member.getId(),
                member.getName(),
                member.getEmail(),
                member.getDesignation(),
                member.getBranch(),
                member.getYear(),
                member.getPassingYear(),
                member.getPhotoUrl(),
                member.getAcademicYear().getYearLabel()
        );
    }
}