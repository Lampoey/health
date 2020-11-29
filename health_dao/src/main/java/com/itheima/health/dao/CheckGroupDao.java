package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 包名：com.itheima.health.dao
 *
 * @Auther: Ou
 * 日期: 2020/11/23/14:48
 */
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void addCheckGroupCheckItem(@Param("checkGroupId") Integer checkGroupId,@Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findByCondition(String queryString);

    CheckGroup findById(int id);

    List<Integer> findCheckItemIdByCheckGroupId(int id);

    void update(CheckGroup checkGroup);

    void deleteCheckGroupCheckItem(Integer id);

    int findSetmealCountByCheckGroupId(int id);

    void deleteById(int id);

    List<CheckGroup> findAll();

}
