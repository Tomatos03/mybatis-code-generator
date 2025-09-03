package org.demo.generator.controller;

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
@Slf4j
@RestController
@RequestMapping("/generator")
public class GeneratorController {
    @Autowired
    private GeneratorService generatorService;

    @GetMapping("/code/{tableName}")
    public void generatorCode(@PathVariable String tableName, HttpServletResponse response) throws Exception {
        generatorService.generatorCode(tableName, response);
    }
}
