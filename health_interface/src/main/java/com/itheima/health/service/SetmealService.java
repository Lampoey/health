package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.Setmeal;

import java.util.List;

/**
 * 包名：com.itheima.health.service
 *
 * @Auther: Ou
 * 日期: 2020/11/24/20:31
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    Setmeal findById(int id);

    List<Integer> findCheckgroupIdsBySetmealId(int id);

    void update(Setmeal setmeal, Integer[] checkgroupIds);

    void deleteById(int id) throws MyException;
    /*
    * 查询所有套餐
    * */
    List<Setmeal> findAll();

    Setmeal findDetailById(int id);
}
