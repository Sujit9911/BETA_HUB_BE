package com.beta.hub_backend.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public Map<String, String> uploadFile(
            MultipartFile file,
            String folder
    ) throws IOException {

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

        if (!"ok".equals(result.get("result"))) {
            cloudinary.uploader().destroy(
                    publicId,
                    ObjectUtils.asMap(
                            "resource_type", "raw"
                    )
            );
        }
    }
}