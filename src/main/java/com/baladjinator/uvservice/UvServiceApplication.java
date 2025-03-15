package com.baladjinator.uvservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@EnableCaching
public class UvServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UvServiceApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new JedisConnectionFactory();
//    }
//
//    @Bean
//    public RedisTemplate<String, String> redisTemplate() {
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory());
//        return template;
//    }

}
