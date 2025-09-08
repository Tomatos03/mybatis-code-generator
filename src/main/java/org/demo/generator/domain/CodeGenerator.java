package org.demo.generator.domain;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.demo.generator.config.code.GeneratorConfig;
import org.demo.generator.config.code.PathRule;
import org.demo.generator.entity.database.ColumnInfo;
import org.demo.generator.entity.GenerateParam;
import org.demo.generator.enums.GenerateTypeEnum;
import org.demo.generator.util.SpringContextUtil;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Stream;

import static org.demo.generator.enums.GenerateTypeEnum.*;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Slf4j
public class CodeGenerator {
    public static TemplateEngine templateEngine() {
        return SpringContextUtil.getBean(TemplateEngine.class);
    }

    public static GeneratorConfig generatorConfig() {
        return SpringContextUtil.getBean(GeneratorConfig.class);
    }

    ;

    public static String generateEntity(GenerateParam param) {
        log.info("生成{}表的Entity代码", param.getTableName());
        return templateEngine().process(
                "Entity",
                getEntityContext(param)
        );
    }

    public static String generateCondition(GenerateParam param) {
        log.info("生成{}表的Condition-Entity代码", param.getTableName());
        return templateEngine().process(
                "Condition",
                getConditionContext(param)
        );
    }

    public static String generateDTOEntity(GenerateParam param) {
        log.info("生成{}表的DTOEntity代码", param.getTableName());
        return templateEngine().process(
                "DTO",
                getDTOEntityContext(param)
        );
    }

    public static String generateService(GenerateParam param) {
        log.info("生成{}表的Service代码", param.getTableName());
        return templateEngine().process("Service", getServiceContext(param));
    }

    public static String generateIService(GenerateParam param) {
        log.info("生成{}表的Service接口代码", param.getTableName());
        return templateEngine().process("IService", getIServiceContext(param));
    }

    public static String generateController(GenerateParam param) {
        log.info("生成{}表的Controller代码", param.getTableName());
        return templateEngine().process("Controller", getControllerContext(param));
    }

    public static String generateMapper(GenerateParam param) {
        log.info("生成{}表的Mapper代码", param.getTableName());
        return templateEngine().process("Mapper", getMapperContext(param));
    }

    public static String generateMapperXml(GenerateParam param) {
        log.info("生成{}表的Mapper的XML代码", param.getTableName());
        return templateEngine().process("MapperImpl", getMapperXMLContext(param));
    }

    private static List<ColumnInfo> convert(List<ColumnInfo> columnInfos, boolean isFilterCommonField) {
        Stream<ColumnInfo> stream = columnInfos.stream()
                                               .peek(columnInfo -> {
                                                   columnInfo.setType(
                                                           DbTypeConvert.convertToJavaType(
                                                                   columnInfo.getType()));
                                                   columnInfo.setName(StrUtil.toCamelCase(
                                                           columnInfo.getDbName()));
                                                   // 当前列是否为String类型
                                                   columnInfo.setString(
                                                           "String".equals(columnInfo.getType()));
                                               });
        if (isFilterCommonField) {
            stream = stream.filter(columnInfo -> !generatorConfig()
                    .getClassIgnoreFields()
                    .contains(columnInfo.getName())
            );
        }
        return stream.toList();
    }

    private static Context getEntityContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);
        context.setVariable("tableComment", param.getTableComment());
        context.setVariable("columns", convert(param.getColumns(), true));

        String entityClassName = buildConcatenatedClassName(ENTITY, param.getClassName());
        context.setVariable("entityClassName", entityClassName);
        context.setVariable("entityModelName", getModelName(ENTITY));
        return context;
    }

    private static Context getDTOEntityContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);
        context.setVariable("tableComment", param.getTableComment());
        context.setVariable("columns", convert(param.getColumns(), true));

        String dtoEntityClassName = buildConcatenatedClassName(DTO, param.getClassName());
        context.setVariable("dtoEntityClassName", dtoEntityClassName);
        context.setVariable("dtoEntityModelName", getModelName(DTO));
        return context;
    }

    private static Context getConditionContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);
        context.setVariable("columns", convert(param.getColumns(), true));

        context.setVariable("conditionClassName", buildConcatenatedClassName(CONDITION,
                                                                             param.getClassName()));
        context.setVariable("conditionModelName", getModelName(CONDITION));
        return context;
    }

    private static Context getServiceContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);

        String mapperClassName = buildConcatenatedClassName(MAPPER, param.getClassName());
        String mapperInstance = StrUtil.lowerFirst(mapperClassName);

        context.setVariable("mapperClassName", mapperClassName);
        context.setVariable("mapperModelName", getModelName(MAPPER));
        context.setVariable("mapperInstance", mapperInstance);
        context.setVariable("serviceModelName", getModelName(SERVICE));
        context.setVariable("serviceClassName",
                            buildConcatenatedClassName(SERVICE, param.getClassName()));
        context.setVariable("serviceInterfaceClassName",
                            buildConcatenatedClassName(SERVICE_INTERFACE,
                                                       param.getClassName()));
        context.setVariable("serviceInterfaceModelName", getModelName(SERVICE_INTERFACE));
        context.setVariable("dtoClassName", buildConcatenatedClassName(DTO,
                                                                       param.getClassName()));
        context.setVariable("dtoModelName", getModelName(DTO));
        context.setVariable("entityClassName",
                            buildConcatenatedClassName(ENTITY, param.getClassName()));
        context.setVariable("entityModelName", getModelName(ENTITY));
        context.setVariable("conditionClassName", buildConcatenatedClassName(CONDITION,
                                                                             param.getClassName()));
        context.setVariable("conditionModelName", getModelName(CONDITION));
        return context;
    }

    private static Context getIServiceContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);
        String serviceInterfaceClassName = buildConcatenatedClassName(SERVICE_INTERFACE,
                                                                      param.getClassName());
        context.setVariable("serviceInterfaceClassName", serviceInterfaceClassName);
        context.setVariable("serviceInterfaceModelName", getModelName(SERVICE_INTERFACE));
        context.setVariable("dtoClassName", buildConcatenatedClassName(DTO,
                                                                       param.getClassName()));
        context.setVariable("dtoModelName", getModelName(DTO));
        context.setVariable("conditionClassName", buildConcatenatedClassName(CONDITION,
                                                                             param.getClassName()));
        context.setVariable("conditionModelName", getModelName(CONDITION));
        return context;
    }

    private static Context getControllerContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);
        context.setVariable("requestPath", param.getRequestPath());

        String serviceInstance = StrUtil.lowerFirst(
                buildConcatenatedClassName(SERVICE, param.getClassName()));

        context.setVariable("serviceInterfaceClassName",
                            buildConcatenatedClassName(SERVICE_INTERFACE, param.getClassName()));
        context.setVariable("serviceInterfaceModelName", getModelName(SERVICE_INTERFACE));
        context.setVariable("serviceInstance", serviceInstance);
        context.setVariable("controllerClassName",
                            buildConcatenatedClassName(CONTROLLER, param.getClassName()));
        context.setVariable("controllerModelName", getModelName(CONTROLLER));
        context.setVariable("dtoClassName", buildConcatenatedClassName(DTO,
                                                                       param.getClassName()));
        context.setVariable("dtoModelName", getModelName(DTO));
        context.setVariable("conditionClassName", buildConcatenatedClassName(CONDITION,
                                                                             param.getClassName()));
        context.setVariable("conditionModelName", getModelName(CONDITION));
        context.setVariable("commonRequestPath", generatorConfig().getCommonRequestPath());

        generatorConfig().getCustomMethodRequest()
                         .forEach((key, value) ->
                                          context.setVariable(key + "RequestPath", value)
                         );
        return context;
    }

    private static Context getMapperContext(GenerateParam param) {
        Context context = new Context();
        setCommonVar(context, param);

        context.setVariable("mapperClassName", buildConcatenatedClassName(MAPPER,
                                                                          param.getClassName()));
        context.setVariable("mapperModelName", getModelName(MAPPER));
        context.setVariable("entityClassName", buildConcatenatedClassName(ENTITY,
                                                                          param.getClassName()));
        context.setVariable("entityModelName", getModelName(ENTITY));
        context.setVariable("conditionClassName", buildConcatenatedClassName(CONDITION,
                                                                             param.getClassName()));
        context.setVariable("conditionModelName", getModelName(CONDITION));
        return context;
    }

    private static void setCommonVar(Context context, GenerateParam param) {
        context.setVariable("author", param.getAuthor());
        context.setVariable("nowDateTime", LocalDate.now()
                                                    .format(DateTimeFormatter.ofPattern(
                                                            "yyyy-MM-dd")));
        context.setVariable("packageName", param.getPackageName());
        context.setVariable("className", param.getClassName());
        generatorConfig().getCustomMethodName()
                         .forEach(context::setVariable);

        List<ColumnInfo> columns = param.getColumns();
        columns.get(columns.size() - 1)
               .setEndColumn(true);
    }

    private static Context getMapperXMLContext(GenerateParam param) {
        Context context = new Context();

        String className = param.getClassName();
        String packageName = param.getPackageName();

        String mapperFullClassName = getFullClassName(packageName, className, MAPPER);
        String entityFullClassName = getFullClassName(packageName, className, ENTITY);
        String conditionFullClassName = getFullClassName(packageName, className, CONDITION);

        context.setVariable("tableName", param.getTableName());
        context.setVariable("mapperFullClassName", mapperFullClassName);
        context.setVariable("entityFullClassName", entityFullClassName);
        context.setVariable("conditionEntityFullClassName", conditionFullClassName);
        context.setVariable("columns", convert(param.getColumns(), false));
        context.setVariable("className", param.getClassName());
        generatorConfig().getCustomMethodName()
                         .forEach(context::setVariable);
        return context;
    }

    private static String buildConcatenatedClassName(GenerateTypeEnum type, String ClassName) {
        PathRule pathRule = generatorConfig().getPathRuleMap()
                                             .get(type.getValue());
        return pathRule.getPrefix() + ClassName + pathRule.getSuffix();
    }

    private static String getModelName(GenerateTypeEnum type) {
        PathRule pathRule = generatorConfig().getPathRuleMap()
                                             .get(type.getValue());
        String modelPath = pathRule.getModelPath();
        return modelPath.replace("/", ".");
    }

    private static String getFullClassName(String packageName, String className,
                                           GenerateTypeEnum type) {
        return String.format("%s.%s.%s", packageName, getModelName(type),
                             buildConcatenatedClassName(type, className));
    }
}