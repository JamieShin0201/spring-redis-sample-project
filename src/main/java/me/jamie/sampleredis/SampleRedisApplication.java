package me.jamie.sampleredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SampleRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SampleRedisApplication.class, args);
    }

}
