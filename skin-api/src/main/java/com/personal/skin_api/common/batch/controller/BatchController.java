package com.personal.skin_api.common.batch.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @GetMapping("/add-product")
    public String addProduct() throws Exception {
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("run.id", UUID.randomUUID().toString()) // 매 실행마다 새로운 ID 생성
                .toJobParameters();

        jobLauncher.run(jobRegistry.getJob("productJob"), jobParameters);

        return "OK";
    }
}
