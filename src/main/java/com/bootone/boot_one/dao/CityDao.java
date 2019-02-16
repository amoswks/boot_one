package com.bootone.boot_one.dao;

import com.bootone.boot_one.domain.City;

public interface CityDao {
    /**
     * 获取城市信息列表
     *
     * @return
     */

    City findById(Long id);

}
