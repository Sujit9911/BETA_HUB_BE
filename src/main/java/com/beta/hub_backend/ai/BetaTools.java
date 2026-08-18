package com.beta.hub_backend.ai;

import com.beta.hub_backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BetaTools {

    private final EventService eventService;
    private final AlumniService alumniService;
    private final TeamService teamService;
    private final NoticeService noticeService;
    private final TemplateService templateService;

    @Tool(description = "Get all upcoming/past BETA events with title, category, date, coordinator")
    public Object getAllEvents() {
        return eventService.getAllEvents();
    }

    @Tool(description = "Get events filtered by category, e.g. Technical, Workshop, Cultural")
    public Object getEventsByCategory(String category) {
        return eventService.getEventsByCategory(category);
    }

    @Tool(description = "Search alumni by domain, e.g. Software Development, Embedded Systems")
    public Object findAlumniByDomain(String domain) {
        return alumniService.filterByDomain(domain);
    }

    @Tool(description = "Search alumni by company name")
    public Object findAlumniByCompany(String company) {
        return alumniService.filterByCompany(company);
    }

    @Tool(description = "Get all alumni records")
    public Object getAllAlumni() {
        return alumniService.getAllAlumni();
    }

    @Tool(description = "Get BETA core committee/team members for a given academic year, e.g. 2026-27")
    public Object getTeamByYear(String academicYearLabel) {
        return teamService.getMembersByYear(academicYearLabel);
    }

    @Tool(description = "Get list of all academic years BETA has team records for")
    public Object getAllAcademicYears() {
        return teamService.getAllYearLabels();
    }

    @Tool(description = "Get pinned/important BETA notices")
    public Object getPinnedNotices() {
        return noticeService.getPinnedNotices();
    }

    @Tool(description = "Get all BETA document templates, e.g. event proposal, sponsorship letter formats")
    public Object getAllTemplates() {
        return templateService.getAllTemplates();
    }
}