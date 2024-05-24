package com.example.springbootdemo.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.springbootdemo.common.Result;
import com.example.springbootdemo.config.AuthAccess;
import com.example.springbootdemo.entity.Files;
import com.example.springbootdemo.service.IFileService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;


/**
 * 文件上传接口
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${files.upload.path}")
    private String fileUploadPath;

    @Value("${server.ip}")
    private String serverIP;
    @Value("${server.port}")
    private String serverPort;

    @Resource
    private IFileService fileService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public static final String FILES_KEY="FILES_FRONT_ALL";

    @PostMapping("/upload")
    public Result upload(@RequestParam MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        String type = FileUtil.extName(filename);
        long size = file.getSize();


        //获取上次文件的MD5，判断文件是否相同
        String md5 = SecureUtil.md5(file.getInputStream());
        Files one = getFiles(md5);
        String url;
        if (one != null) {
            url = one.getUrl();
        } else {
            //定义文件的唯一标识码
            String uuid = IdUtil.fastSimpleUUID();
            String fileUuid = uuid + StrUtil.DOT + type;
            File uploadFile = new File(fileUploadPath + fileUuid);

            if (!uploadFile.getParentFile().exists()) {
                uploadFile.getParentFile().mkdirs();
            }
            url = "http://"+serverIP+":"+serverPort+"/file/" + fileUuid;
            //存储到磁盘
            file.transferTo(uploadFile);
        }

        //存储数据库
        Files saveFile = new Files();
        saveFile.setName(filename);
        saveFile.setType(type);
        saveFile.setSize(size / 1024);
        saveFile.setUrl(url);
        saveFile.setMd5(md5);
        fileService.save(saveFile);

        flushRedis(FILES_KEY);

        return Result.success(url);
    }

    private Files getFiles(String md5) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("md5", md5);
        List<Files> one = fileService.list(wrapper);
        return one.isEmpty() ? null : one.get(0);
    }


    /**
     * @param fileUuid 文件名加上后缀 xxx.jpg
     */
    @GetMapping("/{fileUuid}")
    public void downloadFile(@PathVariable String fileUuid, HttpServletResponse response) throws IOException {
        File uploadFile = new File(fileUploadPath + fileUuid);
        //设置输出流的格式
        ServletOutputStream outputStream = response.getOutputStream();
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileUuid, "UTF-8"));
        response.setContentType("application/octet-stream");
        //读取文件的字节流
        outputStream.write(FileUtil.readBytes(uploadFile));
        outputStream.flush();
        outputStream.close();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum,
                           @RequestParam Integer pageSize,
                           @RequestParam(defaultValue = "") String name) {
        QueryWrapper<Files> wrapper = new QueryWrapper<>();
        wrapper.eq("is_delete", false);
        if (!name.isEmpty()) {
            wrapper.like("name", name);
        }
        wrapper.orderByDesc("id");
        return Result.success(fileService.page(new Page<>(pageNum, pageSize), wrapper));
    }

    @PostMapping("/del/batch")
//    @CacheEvict(value = "files" ,key = "'frontAll'",beforeInvocation = false)//清空缓存
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        List<Files> files = fileService.listByIds(ids);
        for (Files file : files) {
            file.setIsDelete(true);
        }
        flushRedis(FILES_KEY);
        return Result.success(fileService.updateBatchById(files));
    }

    @DeleteMapping("/{id}")
//    @CachePut(value = "files",key = "'frontAll'")//单个挑缓存更新，操作成功后重新赋值
    public Result delete(@PathVariable Integer id) {
        Files files = fileService.getById(id);
        files.setIsDelete(true);
//        fileService.updateById(files);
        flushRedis(FILES_KEY);
        return Result.success(fileService.list());
    }

    @PostMapping("/update")
    public Result save(@RequestBody Files files) {
        return Result.success(fileService.saveOrUpdate(files));
    }
    @GetMapping("/all")
    @AuthAccess
//    @Cacheable(value = "files",key = "'frontAll'")
    public Result frontAll() {

        //1、从缓存获取数据
        String jsonStr = stringRedisTemplate.opsForValue().get(FILES_KEY);
        List<Files> list;
        if(StrUtil.isBlank(jsonStr)){//2、判断是否存在
            list = fileService.list();
            stringRedisTemplate.opsForValue().set(FILES_KEY,JSONUtil.toJsonStr(list));
        }else{
            list= JSONUtil.toBean(jsonStr, new TypeReference<List<Files>>() {
            },true);
        }

        return Result.success(list);
    }


    private void flushRedis(String key){
        stringRedisTemplate.delete(key);
    }

}
