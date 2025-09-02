package org.demo.generator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */

@Configuration
public class ThymeleafConfig {
//    @Bean
//    public ClassLoaderTemplateResolver textTemplateResolver() {
//        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
//        resolver.setPrefix("templates/");
//        resolver.setSuffix(".thymeleaf"); // 文件扩展名称
//        resolver.setTemplateMode(TemplateMode.TEXT); // 设置纯文本模式
//        resolver.setCharacterEncoding(StandardCharsets.UTF_8.toString());
//        return resolver;
//    }

    @Bean
    public ClassLoaderTemplateResolver javaTemplateResolver() {
        ClassLoaderTemplateResolver resolver = createBaseResolver();
        resolver.setSuffix(".java.thymeleaf");
        resolver.setOrder(1); // 设置优先级
        return resolver;
    }

    @Bean
    public ClassLoaderTemplateResolver xmlTemplateResolver() {
        ClassLoaderTemplateResolver resolver = createBaseResolver();
        resolver.setSuffix(".xml.thymeleaf");
        resolver.setOrder(2);
        return resolver;
    }

    private ClassLoaderTemplateResolver createBaseResolver() {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix("templates/");
        resolver.setTemplateMode(TemplateMode.TEXT);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.toString());
        resolver.setCheckExistence(true); // 确保模板存在才解析
        resolver.setCacheable(false);
        return resolver;
    }
}
