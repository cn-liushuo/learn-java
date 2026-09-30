package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

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

    // 方案三：开启 mybatis 的驼峰命名自动映射的开关 --- a_column ------> aColumn
    // 根据 ID 查询员工
    @Select("select * from emp where id = #{id}")
    public Emp getById(Integer id);

    // 方案一：给字段起别名，让别名与实体类属性一致
    // @Select("select id, username, password, name, gender, image, job, entrydate, dept_id deptId, create_time createTime, update_time updateTime from emp where id = #{id}")
    // public Emp getById(Integer id);

    // 方案二：通过 @Results，@Result 注解手动映射封装
    // @Results({
    //         @Result(column = "dept_id", property = "deptID"),
    //         @Result(column = "create_time", property = "createTime"),
    //         @Result(column = "update_time", property = "updateTIme")
    // })
    // @Select("select * from emp where id = #{id}")
    // public Emp getById(Integer id);

    // 条件查询员工
    // @Select("select * from emp where name like  '%${name}%' and gender = #{gender} and " +
    //         "entrydate between #{begin} and #{end} order by update_time desc")
    // public List<Emp> list(@Param("name") String name, @Param("gender") Short gender,
    //                       @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    // @Select("select * from emp where name like concat('%', #{name}, '%') and gender = #{gender} and " +
    //         "entrydate between #{begin} and #{end} order by update_time desc")
    // public List<Emp> list(@Param("name") String name, @Param("gender") Short gender,
    //                       @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    // 动态条件查询
    public List<Emp> list(@Param("name") String name, @Param("gender") Short gender,
                          @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    // 动态更新员工信息
    public void update2(Emp emp);

    // 批量删除员工
    public void deleteByIds(@Param("ids") List<Integer> ids);

}
