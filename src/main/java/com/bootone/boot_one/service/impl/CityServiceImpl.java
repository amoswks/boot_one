package com.bootone.boot_one.service.impl;

import com.bootone.boot_one.dao.CityDao;
import com.bootone.boot_one.domain.City;
import com.bootone.boot_one.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    @Cacheable(value = "people", key = "#id")
    public City findCityById(Long id) {
        String key = "city_" + id;
        ValueOperations<String, City> operations = redisTemplate.opsForValue();

        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            City city = operations.get(key);
            System.out.println("在redis缓存中取到数据");
            return city;
        }
        // 从 DB 中获取城市信息
        City city = cityDao.findById(id);
        operations.set(key, city, 10, TimeUnit.SECONDS);
        return city;
    }

    @Override
    @Cacheable(value = "people", key = "#id")
    public City findOne(Long id) {
        City city = cityDao.findById(id);
        System.out.println("为id、key为:" + id + "数据做了缓存");
        ValueOperations<String, City> operations = redisTemplate.opsForValue();

        // System.out.println("123"+operations.get("1"));
        return city;
    }

    @Override
    @Cacheable(value = "people", key = "#id")
    public City findOne2(Long id) {
        City city = cityDao.findById(id);
        System.out.println("为id、key为:" + id + "数据做了缓存");
        ValueOperations<String, City> operations = redisTemplate.opsForValue();
        // System.out.println("123"+operations.get("1"));

        return city;
    }

}
