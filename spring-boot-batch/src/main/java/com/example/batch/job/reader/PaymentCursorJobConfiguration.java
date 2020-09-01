package com.example.batch.job.reader;

import com.example.batch.domain.pay.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import static com.example.batch.job.reader.PaymentCursorJobConfiguration.JOB_NAME;

@Slf4j
@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(name = "job.name", havingValue = JOB_NAME)
public class PaymentCursorJobConfiguration {

    public static final String JOB_NAME = "paymentCursorJob";

    private final EntityManagerFactory entityManagerFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final DataSource dataSource;

    private final int chunkSize = 10;

    @Bean
    public Job paymentPagingJob() {
        return jobBuilderFactory.get(JOB_NAME)
                .start(paymentPagingStep())
                .build();
    }

    @Bean
    @JobScope
    public Step paymentPagingStep() {
        return stepBuilderFactory.get("paymentPagingStep")
                .<Payment, Payment>chunk(chunkSize)
                .reader(paymentPagingReader())
                .processor(paymentPagingProcessor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JdbcCursorItemReader<Payment> paymentPagingReader() {
        return new JdbcCursorItemReaderBuilder<Payment>()
                .sql("SELECT * FROM payment p WHERE p.success_status = false")
                .rowMapper(new BeanPropertyRowMapper<>(Payment.class))
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .name("paymentPagingReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Payment, Payment> paymentPagingProcessor() {
        return item -> {
            item.success();
            return item;
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Payment> writer() {
        JpaItemWriter<Payment> writer = new JpaItemWriter<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }
}
