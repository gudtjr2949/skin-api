package com.personal.skin_api.product.repository.entity.fileurl;

import com.personal.skin_api.common.exception.RestApiException;

import static com.personal.skin_api.common.exception.product.FileUrlErrorCode.FILE_URL_CANNOT_BE_NULL;

public class FileUrlNullStrategy implements FileUrlValidationStrategy {

    /**
     * fileUrl의 null 여부를 검증한다.
     * @param fileUrl null 여부를 검증할 파일 경로
     */
    @Override
    public void validate(final String fileUrl) {
        if (fileUrl == null) {
            throw new RestApiException(FILE_URL_CANNOT_BE_NULL);
        }
    }
}
