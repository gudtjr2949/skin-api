package com.personal.skin_api.common.file.service;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class S3ServiceTest {

    @Autowired
    private S3Service s3Service;

    @Test
    void S3에_zip_파일을_저장한다() throws IOException {
        // given
        ClassPathResource resource = new ClassPathResource("test.zip");

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "test.zip",
                MediaType.APPLICATION_OCTET_STREAM_VALUE,
                resource.getInputStream()
        );

        // when
        String uploadedUrl = s3Service.uploadFile(file);

        // then
        assertThat(uploadedUrl).isNotEmpty();
    }
}