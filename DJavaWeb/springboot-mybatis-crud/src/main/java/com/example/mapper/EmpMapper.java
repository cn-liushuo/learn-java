package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmpMapper {

    // 根据 ID 删除数据
    @Delete("delete from emp where id = #{id}")
    public void delete(Integer id);
    // public int delete(Integer id);

    // 新增员工
    @Options(useGeneratedKeys = true, keyProperty = "id") // 获取返回的主键
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "VALUES (#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    public void insert(Emp emp);

    // 更新员工
    @Update("update emp set username = #{username}, name = #{name}, gender = #{gender}, image = #{image}, job = #{job}, entrydate = #{entrydate}, dept_id = #{deptId}, update_time = #{updateTime} where id = #{id}")
    public void update(Emp emp);

}
