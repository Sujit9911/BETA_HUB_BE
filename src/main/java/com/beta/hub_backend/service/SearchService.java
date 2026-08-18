package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.SearchResponse;

public interface SearchService {
    SearchResponse search(String query);
}