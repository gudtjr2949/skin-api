package com.personal.skin_api.product.repository.entity.fileurl;

import com.personal.skin_api.common.exception.RestApiException;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class FileUrlTest {

    @Test
    void 파일경로가_null_일_경우_예외가_발생한다() {
        // given
        String nullFileUrl = null;

        // when & then
        assertThatThrownBy(() -> new FileUrl(nullFileUrl))
                .isInstanceOf(RestApiException.class);
    }

    @Test
    void 파일경로가_정상적으로_생성된다() {
        // given
        String normalFileUrl = "fileUrl";

        // when
        FileUrl fileUrl = new FileUrl(normalFileUrl);

        // then
        assertThat(fileUrl.getFileUrl()).isEqualTo(normalFileUrl);
    }
}