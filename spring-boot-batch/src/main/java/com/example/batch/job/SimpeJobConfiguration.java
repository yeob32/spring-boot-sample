package com.example.batch.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Program argument: --job.name=stepNextJob version=2
 *
 * 각 Step의 실행마다 새로운 빈을 만들기 때문에 지연 생성이 가능합니다.
 * 주의할 사항은 @StepScode는 기본 프록시 모드가 반환되는 클래스 타임을 참조하기 때문에 @StepScode를 사용하면 반드시 구현된 반환 타입을 명시해 변환해야합니다.
 *
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpeJobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job simpleJob1() {
        return jobBuilderFactory.get("simpleJob")
                .start(simpleStep1(null))
                .next(simpleStep2(null))
                .build();
    }

    @Bean
    @JobScope // Job 실행시점에 Bean이 생성 -> Bean의 생성 시점을 지정된 Scope가 실행되는 시점으로 지연
    public Step simpleStep1(@Value("#{jobParameters[requestDate]}") String requestDate) { // Double, Long, Date, String
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> { // Reader & Processor & Writer
                    log.info("simpleStep 2 !!!");
                    log.info("simpleStep 2 > requestDate : {}", requestDate);
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    @JobScope
    public Step simpleStep2(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return stepBuilderFactory.get("simpleStep1")
                .tasklet((contribution, chunkContext) -> { // Reader & Processor & Writer
                    log.info("simpleStep 2 !!!");
                    log.info("simpleStep 2 > requestDate : {}", requestDate);
                    return null; // return RepeatStatus.FINISHED;
                }).build();
    }
}
