package org.demo.generator.service;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.demo.generator.config.code.GeneratorConfig;
import org.demo.generator.config.code.PathRule;
import org.demo.generator.entity.database.TableInfo;
import org.demo.generator.entity.GenerateParam;
import org.demo.generator.entity.ZipFileDO;
import org.demo.generator.enums.GenerateTypeEnum;
import org.demo.generator.mapper.TableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 *
 *
 * @author : Tomatos
 * @date : 2025/9/1
 */
@Service
public class GeneratorService {
    @Autowired
    private TableMapper tableMapper;
    @Autowired
    private GeneratorConfig config;

    public void generatorCode(String tableName, HttpServletResponse response) throws Exception {
        TableInfo tableInfo = tableMapper.query(tableName);
        String className = StrUtil.upperFirst(
                StrUtil.removePrefix(tableName, config.getTablePrefix()));

        List<ZipFileDO> files = Arrays.stream(GenerateTypeEnum.values())
                                      .filter(type -> type.getGenerator() != null)
                                      .map(type -> buildZipFile(type,
                                                                tableName,
                                                                className,
                                                                tableInfo))
                                      .toList();

        outZipToBrowser(response, files);
    }

    private ZipFileDO buildZipFile(GenerateTypeEnum type,
                                   String tableName,
                                   String className,
                                   TableInfo tableInfo) {
        String packageName = config.getPackageName();
        String fullPath = getFullPath(packageName, className, type);

        GenerateParam param = GenerateParam.builder()
                                           .author(config.getAuthor())
                                           .packageName(packageName)
                                           .className(className)
                                           .tableName(tableName)
                                           .columns(tableInfo.getColumns())
                                           .build();

        String source = type.getGenerator().apply(param);
        return new ZipFileDO(fullPath, source);
    }

    // [com/demo] [/service] [/(prefix + entityName + suffix)]
    // [com/demo] [/service/impl] [/(prefix + entityName + suffix)]
    // [com/demo] [/entity/] [/(prefix + entityName + suffix)]
    // [packPath] + [modelPath] + [filename]
    private String getFullPath(String packageName, String className, GenerateTypeEnum type) {
        PathRule pathRule = config.getPathRuleMap().get(type.getValue());

        String packagePath = packageName.replace('.', '/');
        String modelPath = pathRule.getModelPath();
        String fileName = pathRule.getPrefix() + className + pathRule.getSuffix();
        String fileExtraName = GenerateTypeEnum.MAPPER_XML == type ? "xml" : "java";

        return String.format("%s/%s/%s.%s", packagePath, modelPath, fileName, fileExtraName);
    }

    private void outZipToBrowser(HttpServletResponse response, List<ZipFileDO> files) throws IOException {
        setDownloadResponseHeaders(response);
        // 将产生的DO文件写入到响应的输出流之中
        writeFileToZip(response, files);
    }

    private void writeFileToZip(HttpServletResponse response, List<ZipFileDO> files) throws IOException {
        try (OutputStream os = response.getOutputStream();
             ZipOutputStream zos = new ZipOutputStream(os)) {

            for (ZipFileDO file : files) {
                zos.putNextEntry(new ZipEntry(file.getFilePath()));
                zos.write(file.getContent().getBytes());
                zos.closeEntry();
            }
        }
    }

    private void setDownloadResponseHeaders(HttpServletResponse response) {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"code.zip\"");
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());
    }
}
