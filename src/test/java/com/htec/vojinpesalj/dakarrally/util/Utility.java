package com.htec.vojinpesalj.dakarrally.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utility {
    public static byte[] toJson(Object r) throws Exception {
        ObjectMapper map = new ObjectMapper();
        return map.writeValueAsString(r).getBytes();
    }
}
