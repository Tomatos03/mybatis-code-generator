package org.demo.generator.entity.database;

import lombok.*;

import java.util.List;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableInfo {
    /**
     * 数据库表名
     */
    private String tableName;
//
//    /**
//     * 表的注释
//     */
//    private String tableComment;
//
//    /**
//     * 表的主键字段名
//     */
//    private String primaryKey;

    /**
     * 表的所有字段信息
     */
    private List<ColumnInfo> columns;
}
