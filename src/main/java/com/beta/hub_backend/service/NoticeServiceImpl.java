package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.NoticeRequest;
import com.beta.hub_backend.dto.NoticeResponse;
import com.beta.hub_backend.entity.Notice;
import com.beta.hub_backend.repo.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService {

    private final NoticeRepository noticeRepository;

    @Override
    public NoticeResponse createNotice(NoticeRequest request) {
        Notice notice = new Notice();
        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setType(request.getType());
        notice.setPinned(request.isPinned());

        Notice saved = noticeRepository.save(notice);
        return toResponse(saved);
    }

    @Override
    public List<NoticeResponse> getAllNotices() {
        return noticeRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeResponse> getPinnedNotices() {
        return noticeRepository.findByPinnedTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<NoticeResponse> getNoticesByType(String type) {
        return noticeRepository.findByType(type)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public NoticeResponse updateNotice(Long id, NoticeRequest request) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));

        notice.setTitle(request.getTitle());
        notice.setContent(request.getContent());
        notice.setType(request.getType());
        notice.setPinned(request.isPinned());

        Notice updated = noticeRepository.save(notice);
        return toResponse(updated);
    }

    @Override
    public void deleteNotice(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notice not found with id: " + id));
        noticeRepository.delete(notice);
    }

    private NoticeResponse toResponse(Notice notice) {
        return new NoticeResponse(
                notice.getId(),
                notice.getTitle(),
                notice.getContent(),
                notice.getType(),
                notice.isPinned(),
                notice.getCreatedAt()
        );
    }
}