package com.zust.zxp.service.impl;

import com.zust.zxp.entity.Order;
import com.zust.zxp.mapper.OrderMapper;
import com.zust.zxp.service.OrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
