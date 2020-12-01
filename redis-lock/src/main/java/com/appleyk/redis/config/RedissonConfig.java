package com.appleyk.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redisson() throws IOException {
        Config config =Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

}