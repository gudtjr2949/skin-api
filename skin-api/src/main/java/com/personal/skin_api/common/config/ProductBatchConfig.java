package com.personal.skin_api.common.config;

import com.personal.skin_api.common.batch.ProductItemProcessor;
import com.personal.skin_api.common.batch.ProductItemReader;
import com.personal.skin_api.common.batch.ProductItemWriter;
import com.personal.skin_api.product.repository.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class ProductBatchConfig {

    private final JobRepository jobRepository; // Job 관리
    private final PlatformTransactionManager transactionManager; // 트랜잭션 관리
    private final ProductItemReader productItemReader;
    private final ProductItemWriter productItemWriter;
    private final ProductItemProcessor processor;

    @Bean
    public Job boardJob() {
        return new JobBuilder("productJob", jobRepository)
                .start(boardStep()) // Step 설정
                .build();
    }

    @Bean
    public Step boardStep() {
        return new StepBuilder("productStep", jobRepository)
                .<Product, Product>chunk(1000, transactionManager) // Chunk 기반 처리
                .reader(productItemReader) // Reader 설정
                .processor(processor) // Processor 설정
                .writer(productItemWriter) // Writer 설정
                .build();
    }
}