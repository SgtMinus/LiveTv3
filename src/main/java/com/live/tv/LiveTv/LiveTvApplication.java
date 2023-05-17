package com.live.tv.LiveTv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LiveTvApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiveTvApplication.class, args);
	}

}
