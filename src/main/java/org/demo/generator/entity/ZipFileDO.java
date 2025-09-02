package org.demo.generator.entity;

import lombok.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZipFileDO {
    private String filePath;
    private String content;
}
