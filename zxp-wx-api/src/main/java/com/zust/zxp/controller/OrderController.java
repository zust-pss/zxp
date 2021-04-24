package com.zust.zxp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.entity.Order;
import com.zust.zxp.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @PostMapping("getNoOverdue")
    public ResultBean getNoOverdue(int current, int size){
        //创建一个当前时间
        Date nowdate = new Date();

        //current 当前页号    size 每页显示条数；
        Page<Order> page = new Page<>(current,size);

        //条件：订单正常并且当前时间处于订单创建时间和过期时间之间；
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("create_time",nowdate)
                .ge("expire_time",nowdate)
                .eq("order_status",0)
                .orderByDesc("create_time");

        //查询出指定页数，指定条数的数据；
        IPage<Map<String, Object>> iPage = orderMapper.selectMapsPage(page, queryWrapper);
        Map<String,Object> maps = new HashMap<>();
        maps.put("iPage",iPage);
        return ResultBean.ok(maps);
    }


}
