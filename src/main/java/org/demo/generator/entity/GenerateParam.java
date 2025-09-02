package org.demo.generator.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.demo.generator.entity.database.ColumnInfo;

import java.util.List;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/2
 */
@Getter
@Setter
@Builder
public class GenerateParam {
    private String packageName;
    private String tableComment;
    private String tableName;
    private String author;
    private String requestPath;
    private String className;
    private List<ColumnInfo> columns;
}
