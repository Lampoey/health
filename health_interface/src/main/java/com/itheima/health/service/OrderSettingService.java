package com.itheima.health.service;

import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * 包名：com.itheima.health.service
 *
 * @Auther: Ou
 * 日期: 2020/11/25/23:26
 */
public interface OrderSettingService  {
    void add(List<OrderSetting> excelList) throws MyException;

    List<Map<String, Integer>> getDataByMonth(String month);

    void editNumberByDate(OrderSetting orderSetting)throws MyException;
}
