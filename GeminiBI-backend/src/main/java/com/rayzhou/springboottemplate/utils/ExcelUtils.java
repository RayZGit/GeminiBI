package com.rayzhou.springboottemplate.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ExcelUtils {

    /**
     * excelToCsv
     * @param multipartFile
     * @return csv data
     */
    public static String excelToCsv(MultipartFile multipartFile) {

        List<Map<Integer, String>> list = null;
        try {
            list = EasyExcel.read(multipartFile.getInputStream())
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();
        } catch (IOException e) {
            log.error("csv fail to convert");
            throw new RuntimeException(e);
        }
        if (CollectionUtils.isEmpty(list)) {
            return "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        LinkedHashMap<Integer, String> headerMap = (LinkedHashMap) list.get(0);
        List<String> headerList = headerMap.values().stream().filter(ObjectUtils::isNotEmpty)
                .collect(Collectors.toList());
        stringBuilder.append(StringUtils.join(headerList, ","));

        for(int i = 1; i < list.size(); i++) {
            LinkedHashMap<Integer, String> dataMap = (LinkedHashMap) list.get(i);
            List<String> dataList = dataMap.values().stream().filter(ObjectUtils::isNotEmpty)
                    .collect(Collectors.toList());
            stringBuilder.append(StringUtils.join(dataList, ","));
        }

        return stringBuilder.toString();
    }
}
