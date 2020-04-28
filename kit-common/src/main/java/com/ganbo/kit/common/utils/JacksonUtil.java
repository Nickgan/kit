package com.ganbo.kit.common.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * 采用Jackson实现的json工具类
 *
 * @author jianglongtao
 * @date 2018/12/27
 */
@Slf4j
public class JacksonUtil {

    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        // 设置默认的日期转换格式
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * 将目标对象序列化为字节流
     */
    public static byte[] toJSONBytes(Object object) throws JsonProcessingException {
        byte[] byteArr = MAPPER.writeValueAsBytes(object);
        return byteArr;
    }

    /**
     * 将目标对象序列化为字符串
     */
    public static String toJSONString(Object object) throws JsonProcessingException {
        String strArr = MAPPER.writeValueAsString(object);
        return strArr;
    }

    /**
     * 从字节流反序列化，得到目标类型的对象
     */
    public static <T> T fromJSONBytes(byte[] arg0, Class<T> arg1)
            throws IOException {
        T obj = MAPPER.readValue(arg0, arg1);
        return obj;
    }

    /**
     * 从字节流反序列化，得到目标类型的对象
     */
    public static <T> Object fromJSONBytes(byte[] arg0, TypeReference<T> arg1)
            throws IOException {
        Object obj = MAPPER.readValue(arg0, arg1);
        return obj;
    }

    /**
     * 从字符串反序列化，得到目标类型的对象
     */
    public static <T> T fromJSONString(String arg0, Class<T> arg1)
            throws IOException {
        T obj = MAPPER.readValue(arg0, arg1);
        return obj;
    }

    /**
     * 从字符串反序列化，得到目标类型的对象
     */
    public static <T> Object fromJSONString(String arg0, TypeReference<T> arg1)
            throws IOException {
        Object obj = MAPPER.readValue(arg0, arg1);
        return obj;
    }

    /**
     * 从字符串反序列化，得到目标类型的对象
     */
    public static <T> T convertJSonStringToObject(String arg0, Class<T> arg1) {
        if (Objects.isNull(arg0)) {
            return null;
        }
        T obj = null;
        try {
            obj = MAPPER.readValue(arg0, arg1);
        } catch (IOException e) {
            log.error("JacksonUtil.convertJSonStringToObject IOException:{}", e);
        }
        return obj;
    }

}
