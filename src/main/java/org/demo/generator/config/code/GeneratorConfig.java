package org.demo.generator.config.code;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "code-generator")
public class GeneratorConfig {
    private String author;
    private String packageName;
    private String tablePrefix;
    private Set<String> classIgnoreFields;
    private Map<String, PathRule> pathRuleMap;
    private Map<String, String> customMethodName = Map.of(
            "add", "add",
            "delete", "delete",
            "update", "update",
            "query", "query"
    );
}