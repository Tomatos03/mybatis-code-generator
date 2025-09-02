package org.demo.generator.domain;

import java.util.Map;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
public final class DbTypeConvert {
    private DbTypeConvert() {}

    private static final Map<String, String> DB_TYPE_TO_JAVA_TYPE = Map.ofEntries(
            Map.entry("varchar", "String"),
            Map.entry("char", "String"),
            Map.entry("text", "String"),
            Map.entry("int", "Integer"),
            Map.entry("bigint", "Long"),
            Map.entry("tinyint", "Integer"),
            Map.entry("smallint", "Integer"),
            Map.entry("datetime", "LocalDateTime"),
            Map.entry("timestamp", "LocalDateTime"),
            Map.entry("date", "LocalDate"),
            Map.entry("decimal", "BigDecimal")
    );

    public static String convertToJavaType(String dbType) {
        return DB_TYPE_TO_JAVA_TYPE.getOrDefault(dbType, dbType);
    }
}
