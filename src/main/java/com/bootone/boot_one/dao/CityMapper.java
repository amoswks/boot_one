package com.bootone.boot_one.dao;

import com.bootone.boot_one.domain.City;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface CityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city
     *
     * @mbg.generated Tue Feb 26 21:44:14 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city
     *
     * @mbg.generated Tue Feb 26 21:44:14 CST 2019
     */
    int insert(City record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city
     *
     * @mbg.generated Tue Feb 26 21:44:14 CST 2019
     */
    City selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city
     *
     * @mbg.generated Tue Feb 26 21:44:14 CST 2019
     */
    List<City> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table city
     *
     * @mbg.generated Tue Feb 26 21:44:14 CST 2019
     */
    int updateByPrimaryKey(City record);


    City findById(Long id);
}