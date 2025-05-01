package com.swe204.lrparser.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResourceUtil {
    public static BufferedReader getBufferedReader(String pathInResources) {
        InputStream inputStream = ResourceUtil.class.getClassLoader().getResourceAsStream(pathInResources);
        if (inputStream == null) {
            throw new RuntimeException("File not found in resources: " + pathInResources);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
