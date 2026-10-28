package com.itheima;

import com.example.EnableHeaderConfig;
import com.example.HeaderConfig;
import com.example.MyImportSelector;
import com.example.TokenParser;
import org.dom4j.io.SAXReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;


// 方案一：扫描第三方包（使用繁琐、性能低，不推荐）
// @ComponentScan({"com.itheima", "com.example"})

// 方案二：@Import 导入普通类 / 配置类 / ImportSelector 实现类
// @Import({TokenParser.class, HeaderConfig.class})
// @Import({TokenParser.class}) // 导入普通类，交给IOC容器管理
// @Import({HeaderConfig.class}) // 导入配置类，，交给IOC容器管理
// @Import(MyImportSelector.class) // 导入ImportSelector接口的实现类

// 方案三：使用第三方提供的 @EnableXxx 注解（推荐），内部通过 MyImportSelector 导入 HeaderConfig
@EnableHeaderConfig
@Import(TokenParser.class) // HeaderConfig 未声明 TokenParser，需单独导入
@SpringBootApplication
public class SpringbootWebConfig2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootWebConfig2Application.class, args);
    }

    // 声明第三方bean —— 不建议放在这里，建议和配置文件放在一起
    // @Bean // 将当前方法的返回值对象交给IOC容器管理，成为IOC容器bean
    // public SAXReader saxReader() {
    //     return new SAXReader();
    // }

}
