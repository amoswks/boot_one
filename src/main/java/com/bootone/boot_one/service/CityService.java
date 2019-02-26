package com.bootone.boot_one.service;
import com.bootone.boot_one.domain.City;
import org.springframework.stereotype.Service;

public interface CityService {
    /**
     * 根据城市 ID,查询城市信息
     *
     * @param id
     * @return
     */
   public City findCityById(Long id);
   public City findOne(Long id);
   public City findOne2(Long id);

}
