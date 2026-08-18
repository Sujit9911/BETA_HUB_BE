package com.beta.hub_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    private static final Set<String> PHOTO_TYPES = Set.of(
            "image/jpeg",
            "image/png",
            "image/webp"
    );

    private static final Set<String> DOCUMENT_TYPES = Set.of(
            "application/pdf",
            "application/msword",
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
    );

    public Map<String, String> uploadFile(
            MultipartFile file,
            String folder
    ) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is required");
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            throw new IllegalArgumentException("File size must not exceed 10MB");
        }

        String contentType = file.getContentType();

        if (folder.contains("photos")) {
            if (contentType == null || !PHOTO_TYPES.contains(contentType)) {
                throw new IllegalArgumentException(
                        "Only JPG, PNG and WEBP images are allowed"
                );
            }
        }

        if (folder.contains("documents")) {
            if (contentType == null || !DOCUMENT_TYPES.contains(contentType)) {
                throw new IllegalArgumentException(
                        "Only PDF, DOC and DOCX files are allowed"
                );
            }
        }

        Map<String, Object> uploadResult = cloudinary.uploader().upload(
                file.getBytes(),
                ObjectUtils.asMap(
                        "folder", "beta-hub/" + folder,
                        "resource_type", "auto"
                )
        );

        Map<String, String> result = new HashMap<>();

        result.put(
                "url",
                uploadResult.get("secure_url").toString()
        );

        result.put(
                "publicId",
                uploadResult.get("public_id").toString()
        );

        return result;
    }

    public void deleteFile(String publicId) throws IOException {

        Map<String, Object> result = cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap(
                        "resource_type", "image"
                )
        );

        String status = String.valueOf(result.get("result"));

        if (!"ok".equals(status)) {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "raw"
                    )
            );
        }
    }
}