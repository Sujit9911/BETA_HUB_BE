package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumniResponse {

    private Long id;
    private String name;
    private String batch;
    private String domain;
    private String contactNumber;
    private String email;
    private String company;
    private String photoUrl;
}