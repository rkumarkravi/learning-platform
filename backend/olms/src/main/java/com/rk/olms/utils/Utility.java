package com.rk.olms.utils;

import com.google.gson.Gson;

import java.util.Base64;

public class Utility {
    public static Gson GSON=new Gson();
    public static Base64.Encoder B64EN=Base64.getEncoder();
    public static Base64.Decoder B64DE=Base64.getDecoder();
}
