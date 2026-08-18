package com.beta.hub_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "team_members")
@Data
public class TeamMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String designation;

    private String branch;

    private String year;

    @Column(name = "passing_year")
    private String passingYear;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "photo_public_id")
    private String photoPublicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "academic_year_id", nullable = false)
    private AcademicYear academicYear;
}