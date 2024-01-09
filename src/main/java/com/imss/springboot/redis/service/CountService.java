package com.imss.springboot.redis.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CountService {
	
	private static final String Count_Hits = "hits_count";
	
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	public CountService(RedisTemplate<String, String>redisTemlate)
	{
		this.redisTemplate=redisTemlate;	
	}
	
	private String getHitsKey(LocalDate date) {
        return Count_Hits + date.toString();
	}
	
	public void recordMobNumHits(String msisdn) {
        String key = getHitsKey(LocalDate.now()); 
        redisTemplate.opsForHash().increment(key, msisdn, 1);
	}
	public long getHitsCount(String msisdn, LocalDate date)
	{
		String key = getHitsKey(date);
		Object count = redisTemplate.opsForHash().get(key, msisdn);
		if(count != null)
		{
			return Long.parseLong(count.toString());
		}
		return 0;
		
	}
}
 