package com.imss.springboot.redis.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CountService2 {
	
    private static final String Count_Hits = "hits_count";
	
	private final RedisTemplate<String, String> redisTemplate;
    
	@Autowired
	public CountService2(RedisTemplate<String, String> redisTemplate)
	{
		this.redisTemplate=redisTemplate;
	}
    
	private String getHitsKey(LocalDate date) {
        return Count_Hits + date.toString();
	}
	
	private String getHitsKey(String msisdn) {
        return Count_Hits + msisdn;
	}

	public void recordMsisdnHit(String msisdn) {
        String key = getHitsKey(LocalDate.now());
        String mobileHitsKey = getHitsKey(msisdn);
        if (!redisTemplate.hasKey(key)) {
            redisTemplate.opsForValue().set(key, "1");
        } else {
            redisTemplate.opsForValue().increment(key);
        }
        redisTemplate.opsForValue().set(mobileHitsKey, key);
    }
	
	 public Long getMobileHitCount(String msisdn) {
	        String mobileHitsKey = getHitsKey(msisdn);
	        String hitCountKey = redisTemplate.opsForValue().get(mobileHitsKey);
	        System.out.println("+++++++++++" + hitCountKey);
	        if (hitCountKey != null) {
	        	System.out.println(redisTemplate.opsForValue().get(hitCountKey));
	            return Long.parseLong(redisTemplate.opsForValue().get(hitCountKey));
	        }
	        return 0L;
	    }
}
