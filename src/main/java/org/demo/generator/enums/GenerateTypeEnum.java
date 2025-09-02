package org.demo.generator.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.demo.generator.domain.CodeGenerator;
import org.demo.generator.entity.GenerateParam;

import java.util.function.Function;

/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/2
 */
@Getter
@AllArgsConstructor
public enum GenerateTypeEnum {
    ENTITY    ("entity", CodeGenerator::generateEntity),
    CONDITION ("condition-entity", null),
    DTO       ("dto-entity", CodeGenerator::generateDTOEntity),
    MAPPER    ("mapper", CodeGenerator::generateMapper),
    MAPPER_XML    ("mapper-xml", CodeGenerator::generateMapperXml),
    SERVICE_INTERFACE ("service-interface", CodeGenerator::generateIService),
    SERVICE   ("service", CodeGenerator::generateService),
    CONTROLLER("controller", CodeGenerator::generateController);

    private final String value;
    private final Function<GenerateParam, String> generator;
}
