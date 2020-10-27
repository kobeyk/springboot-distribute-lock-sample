package com.appleyk.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>Json序列化工具类</p>
 *
 * @author Appleyk
 * @version V.0.1.1
 * @blob https://blog.csdn.net/Appleyk
 * @date created on 14:01 2020/2/26
 */
public class JsonUtils {

    /**定义jackson对象*/
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // 忽略JSON中没有的字段，防止反序列化对象的时候报找不到属性字段的异常
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 如果json字符串中含有新行时，加上这个
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 空值转换异常
        MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    }

    /**
     * 将对象转换成json字符串。
     */
    public static String object2Json(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据反序列化的类型将对象转成json串
     */
    public static String object2JsonWithType(Object data, TypeReference typeReference){
        try {
            return MAPPER.writerFor(typeReference).writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json结果集转化为对象
     */
    public static <T> T json2Pojo(String jsonData, Class<T> beanType) {
        try {
            return MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将json数据转换成pojo对象list
     */
    public static <T> List<T> json2List(String jsonData, Class<T> beanType) {
        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        try {
            return MAPPER.readValue(jsonData, javaType);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * json字符串转Map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> parseMap(String jsonStr) throws IOException {
        return MAPPER.readValue(jsonStr, Map.class);
    }

    public static List<String> parseList(String jsonStr) throws IOException {
        return MAPPER.readValue(jsonStr, new TypeReference<List<String>>() {});
    }

}
