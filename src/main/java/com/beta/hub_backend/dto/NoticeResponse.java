package com.beta.hub_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoticeResponse {

    private Long id;
    private String title;
    private String content;
    private String type;
    private boolean pinned;
    private LocalDateTime createdAt;
}