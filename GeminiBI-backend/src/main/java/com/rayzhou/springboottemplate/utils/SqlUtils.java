package com.rayzhou.springboottemplate.utils;

import org.apache.commons.lang3.StringUtils;

public class SqlUtils {

    public static boolean validSortField(String sortField) {
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}
