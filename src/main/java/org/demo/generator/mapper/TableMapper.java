package org.demo.generator.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.demo.generator.entity.database.TableInfo;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Mapper
public interface TableMapper {
    /**
     * 根据数据库表名称, 查询表的信息
     *
     * @param tableName 数据库表名
     * @return TableInfo 数据库表信息对象
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/9/1 15:12
     */
    TableInfo query(String tableName);
}
