package com.SwagLabs.Utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class JSONReader {
	public static List<userData> readJsonData(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(new File(filePath), new TypeReference<List<userData>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
