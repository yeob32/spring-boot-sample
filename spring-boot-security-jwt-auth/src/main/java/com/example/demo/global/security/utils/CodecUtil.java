package com.example.demo.global.security.utils;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@UtilityClass
public class CodecUtil {

    public static String decodeBase64(String encoded) {
        if(StringUtils.isEmpty(encoded)) return "";
        byte[] encodedBytes = encoded.getBytes(StandardCharsets.UTF_8);
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(encodedBytes));
    }
}
