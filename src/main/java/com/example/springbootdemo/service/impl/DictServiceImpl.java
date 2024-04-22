package com.example.springbootdemo.service.impl;

import com.example.springbootdemo.entity.Dict;
import com.example.springbootdemo.mapper.DictMapper;
import com.example.springbootdemo.service.IDictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-17
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements IDictService {

}
