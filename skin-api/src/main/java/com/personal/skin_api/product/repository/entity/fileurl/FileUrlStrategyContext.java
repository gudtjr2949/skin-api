package com.personal.skin_api.product.repository.entity.fileurl;

import java.util.List;

public class FileUrlStrategyContext {

    private static final List<FileUrlValidationStrategy> fileValidationStrategies = List.of(
            new FileUrlNullStrategy()
    );

    static void runStrategy(final String fileUrl) {
        fileValidationStrategies.stream().forEach(strategy -> strategy.validate(fileUrl));
    }
}
