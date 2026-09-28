package com.example;

import com.example.mapper.UserMapper;
import com.example.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest // springboot 整合单元测试的注解
class SpringbootMybatisQuickstartApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testListUser() {
        List<User> userList = userMapper.list();
        userList.stream().forEach(user -> {
            System.out.println(user);
        });
    }

    @Test
    public void testJdbc() throws Exception {
        // 1、注册驱动
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 2、获取连接对象
        String url = "jdbc:mysql://127.0.0.1:3306/mybatis";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);

        // 3、获取执行 SQL 的对象 Statement，执行 SQL，返回结果
        String sql = "select * from user";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        // 4、封装结果数据
        ArrayList<User> userList = new ArrayList<>();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            short age = resultSet.getShort("age");
            short gender = resultSet.getShort("gender");
            String phone = resultSet.getString("phone");

            User user = new User(id, name, age, gender, phone);
            userList.add(user);
        }

        // 5、释放资源
        statement.close();
        connection.close();

        userList.stream().forEach(user -> {
            System.out.println(user);
        });
    }

}
