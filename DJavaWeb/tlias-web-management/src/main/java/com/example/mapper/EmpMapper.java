package com.example.mapper;

import com.example.pojo.Emp;
import org.apache.ibatis.annotations.*;

import java.time.LocalDate;
import java.util.List;

/**
 * 员工管理
 */
@Mapper
public interface EmpMapper {

    /**
     * 查询总记录数
     *
     * @return
     */
    // @Select("select count(*) from emp;")
    // public Long count();

    /**
     * 进行分页查询获取列表数据
     *
     * @param start
     * @param pageSize
     * @return
     */
    // @Select("select * from emp limit #{start},#{pageSize}")
    // public List<Emp> page(@Param("start") Integer start, @Param("pageSize") Integer pageSize);

    /**
     * 员工信息查询
     *
     * @return
     */
    // @Select("select * from emp")
    public List<Emp> list(@Param("name") String name, @Param("gender") Short gender, @Param("begin") LocalDate begin, @Param("end") LocalDate end);

    /**
     * 批量删除
     *
     * @param ids
     */
    void delete(@Param("ids") List<Integer> ids);

    /**
     * 新增员工
     *
     * @param emp
     */
    @Insert("insert into emp(username, name, gender, image, job, entrydate, dept_id, create_time, update_time)" +
            "values(#{username}, #{name}, #{gender}, #{image}, #{job}, #{entrydate}, #{deptId}, #{createTime}, #{updateTime})")
    void insert(Emp emp);

    /**
     * 根据id查询员工
     *
     * @param id
     * @return
     */
    @Select("select * from emp where id = #{id}")
    Emp getById(Integer id);

    /**
     * 根据id修改员工
     * @param emp
     */
    void update(Emp emp);

    /**
     * 根据用户名密码查询员工
     * @param emp
     * @return
     */
    @Select("select * from emp where username = #{username} and password = #{password}")
    Emp getByUsernameAndPassword(Emp emp);
}
