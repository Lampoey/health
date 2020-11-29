package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;

import java.util.List;

/**
 * 包名：com.itheima.health.dao
 *
 * @Auther: Ou
 * 日期: 2020/11/21/18:10
 */
public interface CheckItemDao {

    List<CheckItem> findAll();
    void add(CheckItem checkItem);

    Page<CheckItem> findByCondition(String queryString);

    void deleteById(int id);

    int findCountByCheckItemId(int id);

    CheckItem findById(int id);

    void update(CheckItem checkItem);
}
