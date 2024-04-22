package com.example.springbootdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.springbootdemo.entity.Files;
import com.example.springbootdemo.mapper.FileMapper;
import com.example.springbootdemo.service.IFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author white
 * @since 2024-04-13
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, Files> implements IFileService {


}
