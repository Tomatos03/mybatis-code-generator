package org.demo.generator.entity.database;


import lombok.*;

/**
 * 数据库表字段信息映射
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnInfo {
    /**
     * 字段名(转小驼峰后)
     */
    private String name;

    /**
     * 字段在数据库之中的名称
     */
    private String dbName;

    /**
     * 字段类型
     */
    private String type;

    /**
     * 是否为主键
     */
    private Boolean isPrimaryKey;

    /**
     * 是否允许为null
     */
    private Boolean isNull;

    /**
     * 字段注释
     */
    private String comment;

    private boolean isString;

    /**
     * 是否为最后一列
     */
    private boolean isEndColumn = false;
}
