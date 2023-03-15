package com.yuhua.amlsys;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        String url = "jdbc:mysql:///amldb";
        String username = "root";
        String password = "123456";
        String moduleName = "sys";
        String mapperLocation = "E:\\AmlSys\\AmlSysBackEnd\\AmlSys\\src\\main\\resources\\mapper\\" + moduleName;
        String tables = "user,role,menu,user_role,role_menu";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("yuhua") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("E:\\AmlSys\\AmlSysBackEnd\\AmlSys\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.yuhua.amlsys") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables) // 设置需要生成的表名
                            .addTablePrefix("y_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
