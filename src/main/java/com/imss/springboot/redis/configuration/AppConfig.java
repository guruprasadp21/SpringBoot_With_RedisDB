package com.imss.springboot.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import com.imss.springboot.redis.model.Employee;

@Configuration
@EnableRedisRepositories
public class AppConfig {

	@Bean
	public RedisConnectionFactory redisConnectionFactory()
	{
		return new LettuceConnectionFactory();
	}
	
	@Bean
	public RedisTemplate<String , Employee> redisTemplet()
	{
		RedisTemplate<String, Employee> template = new RedisTemplate<>();
		template.setConnectionFactory(redisConnectionFactory());
		return template;
		
	}
	
}
