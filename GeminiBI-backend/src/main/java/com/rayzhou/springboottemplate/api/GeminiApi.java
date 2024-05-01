package com.rayzhou.springboottemplate.api;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.rayzhou.springboottemplate.api.Constant.ACCESS_KEY;

@Service
public class GeminiApi {

    public static final String SPLIT = "-----";

    private static final String URL = String.format("https://generativelanguage.googleapis.com/v1beta/models/" +
            "gemini-1.5-pro-latest:generateContent?key=%s", ACCESS_KEY);

    private static final List<Map<String, String>> SAFETY_SETTINGS = List.of(
            Map.of("category", "HARM_CATEGORY_HARASSMENT",
                    "threshold", "BLOCK_MEDIUM_AND_ABOVE"),
            Map.of("category", "HARM_CATEGORY_HATE_SPEECH",
                    "threshold", "BLOCK_MEDIUM_AND_ABOVE"),
            Map.of("category", "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                    "threshold", "BLOCK_MEDIUM_AND_ABOVE"),
            Map.of("category", "HARM_CATEGORY_DANGEROUS_CONTENT",
                    "threshold", "BLOCK_MEDIUM_AND_ABOVE")
    );

    private static final Map<String, Object> GENERATION_CONFIG = Map.of(
            "temperature", 1,
            "topK", 0,
            "topP", 0.95,
            "maxOutputTokens", 8192,
            "stopSequences", Collections.emptyList()
    );


    public String generate(String goal, String data) {
        LinkedHashMap<String, Object> hashMap = new LinkedHashMap<>();
        List<Map<String, String>> parts = List.of(
                Map.of("text", "input: You are a data scientist and a software frontend engineer."),
                Map.of("text", "output: "),
                Map.of("text", "output: input: I will give you data in following format.\\nAnlysis Goal:\\n{The goal of requirement of this data analysis}\\nRaw Data: \\n{data in csv format and using , to split}"),
                Map.of("text", "output: "),
                Map.of("text", "input: Please use the data I provided to generate output in following format. \n-----\n{data analysis conclusion in one paragraph}\n-----\n{data visualization code in Echart V5 option config javascript code}\n-----\nPlease do not output any other info."),
                Map.of("text", "output: "),
                Map.of("text", String.format("input: Anlysis Goal:\n%s\nRaw Data: \n%s", goal, data)),
                Map.of("text", "output: ")
                );

        hashMap.put("parts", parts);
        String json = JSONUtil.toJsonStr(Map.of(
                "contents", List.of(hashMap),
                "generationConfig", GENERATION_CONFIG,
                "safetySettings", SAFETY_SETTINGS
                ));
        String result =  HttpRequest.post(URL)
                .contentType("application/json")
                .body(json)
                .execute()
                .body();

        return result;

    }
}
