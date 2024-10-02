package com.example.meeting_scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.example.meeting_scheduler.entity")
public class MeetingSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingSchedulerApplication.class, args);
	}

}
