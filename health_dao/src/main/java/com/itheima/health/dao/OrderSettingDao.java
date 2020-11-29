package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 包名：com.itheima.health.dao
 *
 * @Auther: Ou
 * 日期: 2020/11/25/23:31
 */
public interface OrderSettingDao {
    OrderSetting findByOrderDate(Date orderDate);

    void updateNumber(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    List<Map<String, Integer>> getDataByMonth(String month);
}
