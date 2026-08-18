package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberResponse {

    private Long id;
    private String name;
    private String email;
    private String designation;
    private String branch;
    private String year;
    private String passingYear;
    private String photoUrl;
    private String academicYearLabel;
}