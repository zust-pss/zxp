package com.zust.zxp.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.entity.Comment;
import com.zust.zxp.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
//@RestController
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    private LocalDateTime time;

    @RequestMapping("addcomment")
    public String addcomment(int orderId, String content, RedirectAttributes model){
        //创建当前评论的时间
        time = LocalDateTime.now();

        //封装成一个Comment对象
        Comment comment = new Comment();
        Integer uid = StpUtil.getLoginIdAsInt();
        comment.setUid(uid);
        comment.setOrderId(orderId);
        comment.setContent(content);
        comment.setTime(time);

        commentMapper.insert(comment);

        model.addFlashAttribute("orderId",orderId);
        return "redirect:/comment/selectAll";

    }

    @RequestMapping("selectAll")
    public @ResponseBody ResultBean selectAll(@ModelAttribute("orderId") int orderId){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("time")
                .eq("order_id",orderId);

        List<Map<String, Object>> comments = commentMapper.selectMaps(queryWrapper);
        Map<String,Object> map = new HashMap<>();
        map.put("comments",comments);
        return ResultBean.ok(map);
    }
}
