package com.itheima.mapper;

import com.itheima.pojo.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DeptMapper {
    //查询全部部门数据
    @Select("select * from tlias_dept")
    List<Dept> list();

    //删除部门
    @Delete("delete from tlias_dept where id = #{id}")
    void delete(Integer id);

    //新增部门
    @Insert("insert into tlias_dept(name, create_time, update_time) values (#{name},#{createTime},#{updateTime})")
    void save(Dept dept);
}



