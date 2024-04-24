package com.example.springbootdemo.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.sql.Types;
import java.util.Collections;

/**
 * 代码生成器
 */
public class CodeGenerator {
    public static void main(String[] args) {
        generator();
    }

    private static void generator(){
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/demo?serverTimezone=GMT%2b8", "root", "White1229&")
                .globalConfig(builder -> {
                    builder.author("white") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .outputDir("E:\\project\\java\\SpringBootDemo\\src\\main\\java\\"); // 指定输出目录
                })
                .dataSourceConfig(builder -> builder.typeConvertHandler((globalConfig, typeRegistry, metaInfo) -> {
                    int typeCode = metaInfo.getJdbcType().TYPE_CODE;
                    if (typeCode == Types.SMALLINT) {
                        // 自定义类型转换
                        return DbColumnType.INTEGER;
                    }
                    return typeRegistry.getColumnType(metaInfo);

                }))
                .packageConfig(builder -> {
                    builder.parent("com.example.springbootdemo") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "E:\\project\\java\\SpringBootDemo\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_course") // 设置需要生成的表名
                            .addTablePrefix("t_", "sys_")// 设置过滤表前缀
                            .controllerBuilder().enableRestStyle()//rest样式
                            .enableHyphenStyle()//驼峰命名
                            .controllerBuilder().enableFileOverride()
                            .mapperBuilder().enableFileOverride()
                            .serviceBuilder().enableFileOverride()
                            .entityBuilder().enableLombok().enableFileOverride();
                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
