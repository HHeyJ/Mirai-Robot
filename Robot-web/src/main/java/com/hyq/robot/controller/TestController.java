package com.hyq.robot.controller;

import com.hyq.robot.dao.BarPostDAO;
import com.hyq.robot.query.BarPostQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author nanke
 * @date 2020/7/14 下午4:54
 */
@RestController
public class TestController {

    @Resource
    private BarPostDAO barPostDAO;

    @RequestMapping("ttt")
    public Object ttt() {

        BarPostQuery query = new BarPostQuery();
        query.setPostUrl("https://tieba.baidu.com/p/6740879083");
        return barPostDAO.queryByCondition(query);
    }



}
