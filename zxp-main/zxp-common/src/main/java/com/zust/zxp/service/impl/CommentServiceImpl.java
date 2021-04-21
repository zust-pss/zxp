package com.zust.zxp.service.impl;

import com.zust.zxp.entity.Comment;
import com.zust.zxp.mapper.CommentMapper;
import com.zust.zxp.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pss
 * @since 2021-04-13
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
