package com.example.batch.batch;

import com.example.batch.domain.pay.Payment;
import com.example.batch.domain.pay.PaymentRepository;
import com.example.batch.job.reader.PaymentPagingFailJobConfiguration;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThat;

@Disabled
@SpringBatchTest
@SpringBootTest(classes = PaymentPagingFailJobConfiguration.class)
//@ContextConfiguration(classes = PaymentPagingFailJobConfiguration.class)
//@TestPropertySource(properties = {"job.name=" + PaymentPagingFailJobConfiguration.JOB_NAME})
public class PaymentPagingFailJobConfigurationTest {

    @Autowired
//    @Qualifier("paymentPagingFailJob")
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private PaymentRepository paymentRepository;

    @Test
    public void 같은조건을읽고_업데이트할때() throws Exception {
        //given
        for (long i = 0; i < 50; i++) {
            paymentRepository.save(new Payment(i, false));
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("job.name", PaymentPagingFailJobConfiguration.JOB_NAME)
                .toJobParameters();

        //when
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob();
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        //then
        assertThat(jobExecution.getStatus()).isEqualTo(BatchStatus.COMPLETED);
        assertThat(paymentRepository.findAllBySuccessStatusIsTrue().size()).isEqualTo(50);
    }
}
