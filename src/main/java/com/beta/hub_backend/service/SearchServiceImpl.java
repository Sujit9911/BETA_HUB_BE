package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.SearchResponse;
import com.beta.hub_backend.dto.SearchResultItem;
import com.beta.hub_backend.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final EventRepository eventRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final AlumniRepository alumniRepository;
    private final TemplateRepository templateRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public SearchResponse search(String query) {

        List<SearchResultItem> events = eventRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .map(e -> new SearchResultItem(e.getId(), e.getTitle(), e.getCategory(), "EVENT"))
                .collect(Collectors.toList());

        List<SearchResultItem> team = teamMemberRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(m -> new SearchResultItem(m.getId(), m.getName(), m.getDesignation(), "TEAM"))
                .collect(Collectors.toList());

        List<SearchResultItem> alumni = alumniRepository.findByNameContainingIgnoreCase(query)
                .stream()
                .map(a -> new SearchResultItem(a.getId(), a.getName(), a.getCompany(), "ALUMNI"))
                .collect(Collectors.toList());

        List<SearchResultItem> templates = templateRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .map(t -> new SearchResultItem(t.getId(), t.getTitle(), t.getCategory(), "TEMPLATE"))
                .collect(Collectors.toList());

        List<SearchResultItem> notices = noticeRepository.findByTitleContainingIgnoreCase(query)
                .stream()
                .map(n -> new SearchResultItem(n.getId(), n.getTitle(), n.getType(), "NOTICE"))
                .collect(Collectors.toList());

        return new SearchResponse(events, team, alumni, templates, notices);
    }
}