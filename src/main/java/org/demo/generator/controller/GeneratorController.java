package org.demo.generator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.demo.generator.service.GeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Tag(name = "代码生成接口")
@Slf4j
@RestController
@RequestMapping("/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;

    @Operation(summary = "单表生成")
    @GetMapping("/code/{tableName}")
    @Parameter(name = "tableName", description = "数据库表名", required = true, example =
            "sys_user")
    public void generatorCode(@PathVariable String tableName, HttpServletResponse response) throws Exception {
        generatorService.generatorCode(tableName, response);
    }

    @Operation(summary = "多表生成",description = "一次性生成多个表的代码")
    @Parameter(name = "tableNames", description = "数据库中多个表的名称(以逗号分割)", required = true, example =
            "sys_user, sys_job, sys_dept")
    @GetMapping("/code/batch/{tableNames}")
    public void batchGeneratorCode(@PathVariable String tableNames, HttpServletResponse response) throws Exception {
        generatorService.batchGeneratorCode(tableNames, response);
    }
}
