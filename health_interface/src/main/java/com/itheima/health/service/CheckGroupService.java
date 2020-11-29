package com.itheima.health.service;

import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.exception.MyException;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

/**
 * 包名：com.itheima.health.service
 *
 * @Auther: Ou
 * 日期: 2020/11/23/14:39
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(QueryPageBean queryPageBean);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdByCheckGroupId(int id);

    void update(CheckGroup checkGroup, Integer[] checkitemIds);

    void deleteById(int id) throws MyException;

    List<CheckGroup> findAll();

}
