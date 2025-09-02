package org.demo.generator.config.code;

import lombok.Getter;
import lombok.Setter;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/2
 */
@Getter
@Setter
public class PathRule {
    private String modelPath;
    private String prefix = "";
    private String suffix = "";
}
