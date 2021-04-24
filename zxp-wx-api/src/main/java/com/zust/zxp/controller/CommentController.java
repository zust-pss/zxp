package com.zust.zxp.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.entity.Comment;
import com.zust.zxp.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    private LocalDateTime time;

    @PostMapping("create")
    public ResultBean addcomment(int orderId,String content){
        //创建当前评论的时间
        time = LocalDateTime.now();

        //封装成一个Comment对象
        Comment comment = new Comment();
        Integer id = StpUtil.getLoginIdAsInt();
        comment.setUid(id);
        comment.setOrderId(orderId);
        comment.setContent(content);
        comment.setTime(time);

        //创建评论
        commentMapper.insert(comment);

        //创建的评论按时间排序，并且只显示当前订单下的评论
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("time")
                .eq("order_id",orderId);


        //创建完评论立即显示所有评论
        List<Map<String, Object>> comments = commentMapper.selectMaps(queryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("comments",comments);
        return ResultBean.ok(map);
    }
}
