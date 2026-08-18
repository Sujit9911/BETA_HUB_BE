package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {

    private List<SearchResultItem> events;
    private List<SearchResultItem> team;
    private List<SearchResultItem> alumni;
    private List<SearchResultItem> templates;
    private List<SearchResultItem> notices;
}