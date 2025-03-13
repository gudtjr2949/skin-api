package com.personal.skin_api.product.repository.entity.fileurl;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
public class FileUrl {

    @Column(name = "FILE_URL")
    private String fileUrl;

    public FileUrl(final String fileUrl) {
        validate(fileUrl);
        this.fileUrl = fileUrl;
    }

    private void validate(final String fileUrl) {
        FileUrlStrategyContext.runStrategy(fileUrl);
    }

    public String getFileUrl() {
        return fileUrl;
    }
}
