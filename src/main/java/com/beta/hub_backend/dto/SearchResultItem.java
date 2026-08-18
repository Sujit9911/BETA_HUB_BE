package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultItem {

    private Long id;
    private String title;   // event title, member name, alumni name, template title, notice title
    private String subtitle; // category, designation, company, etc. — extra context
    private String type;     // "EVENT", "TEAM", "ALUMNI", "TEMPLATE", "NOTICE"
}