package com.example.controller;

import com.example.pojo.Dept;
import com.example.pojo.Result;
import com.example.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 部门管理Controller
 */
@Slf4j
@RestController
public class DeptController {

    // private static Logger log = LoggerFactory.getLogger(DeptController.class);

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门数据
     *
     * @return
     */
    // @RequestMapping(value = "/depts", method = RequestMethod.GET) // 指定请求方式为 GET
    @GetMapping("/depts")
    public Result list() {
        log.info("查询全部部门数据");

        // 调用 service 查询部门数据
        List<Dept> deptList = deptService.list();

        return Result.success(deptList);
    }

    /**
     * 删除部门数据
     *
     * @param id
     * @return
     */
    @DeleteMapping("/depts/{id}")
    public Result delete(@PathVariable Integer id) {
        log.info("根据id删除部门:{}", id);
        // 调用 service 删除部门
        deptService.delete(id);
        return Result.success();
    }
}
