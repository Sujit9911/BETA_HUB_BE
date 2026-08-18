package com.beta.hub_backend.service;

import com.beta.hub_backend.dto.NoticeRequest;
import com.beta.hub_backend.dto.NoticeResponse;
import java.util.List;

public interface NoticeService {

    NoticeResponse createNotice(NoticeRequest request);

    List<NoticeResponse> getAllNotices();

    List<NoticeResponse> getPinnedNotices();

    List<NoticeResponse> getNoticesByType(String type);

    NoticeResponse updateNotice(Long id, NoticeRequest request);

    void deleteNotice(Long id);
}