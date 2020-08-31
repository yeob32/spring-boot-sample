package com.example.demo;

import com.example.demo.config.Company;
import com.example.demo.config.CompanyProperties;
import com.example.demo.config.Employee;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@EnableConfigurationProperties(CompanyProperties.class)
@SpringBootApplication
public class SpringInitializeApplication {

	/**
	 * 스프링 부트 초기화 방법
	 * 1. ApplicationRunner
	 * 2. CommandLineRunner
	 * 3. ApplicationReadyEvent
	 *
	 * @param args
	 */

	public static void main(String[] args) {
		ConfigurableApplicationContext cac = SpringApplication.run(SpringInitializeApplication.class, args);
		cac.addApplicationListener((ApplicationListener<MyEvent>) myEvent -> System.out.println("myEvent : " + myEvent.getMessage()));
		cac.publishEvent(new MyEvent(cac, "Hello SpringBoot Event !!!"));

		Company company = cac.getBean("company", Company.class);
		System.out.println(company);

		Employee employee = cac.getBean("employee", Employee.class);
		System.out.println(employee);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onMyEvent() {
		System.out.println("Application Ready !!!!!");
	}

	@MyEventListener
//    @EventListener
//    @EventListener(MyEvent.class) -> 이벤트가 발생했다는것만 알고 싶을 때
	public void onMyEvent(MyEvent myEvent) {
		System.out.println("Hello onMyEvent message : " + myEvent.getMessage());
	}

	static class MyEvent extends ApplicationEvent {

		private final String message;

		public MyEvent(Object source, String message) {
			super(source);
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

	@Target(ElementType.METHOD)
	@Retention(RetentionPolicy.RUNTIME)
	@EventListener
	@interface MyEventListener {

	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> System.out.println("Hello applicationRunner !!");
	}

	@Bean
	public CommandLineRunner commandLineRunner() {
		return args -> System.out.println("Hello CommandLineRunner !!");
	}
}
