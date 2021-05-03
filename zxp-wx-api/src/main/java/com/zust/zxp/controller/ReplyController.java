package com.zust.zxp.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.druid.sql.dialect.blink.parser.BlinkStatementParser;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zust.zxp.bean.ResultBean;
import com.zust.zxp.entity.Comment;
import com.zust.zxp.entity.Reply;
import com.zust.zxp.mapper.CommentMapper;
import com.zust.zxp.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
//@RestController
@Controller //需要重定向页面使用这个
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private CommentMapper commentMapper;

    private LocalDateTime time;

    @RequestMapping("/getAll")
    public @ResponseBody ResultBean getAllReply(@ModelAttribute("orderId") int orderId){
        HashMap<Object,Object> maps = new HashMap<>();
        QueryWrapper<Comment> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.orderByDesc("time")
                .eq("order_id",orderId);
        List<Comment> comments = commentMapper.selectList(queryWrapper1);
        for (Comment comment : comments) {

            QueryWrapper<Reply> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("comment_id",comment.getId())
                    .orderByAsc("time");
            List<Reply> replies = replyMapper.selectList(queryWrapper2);
            maps.put(comment,replies);
        }
        return ResultBean.ok(maps);

    }

    @RequestMapping("/addReply")
    public String addReplyToCom(int orderId,int comId,int replyId,String content,RedirectAttributes model){
        Integer uid = StpUtil.getLoginIdAsInt();

        //创建当前评论的时间
        time = LocalDateTime.now();

        Reply reply = new Reply();
        reply.setUid(uid);
        reply.setCommentId(comId);
        reply.setReplyId(replyId);
        reply.setTime(time);
        reply.setContent(content);

        replyMapper.insert(reply);

        model.addFlashAttribute("orderId",orderId);
        return "redirect:/reply/getAll";
    }

}
