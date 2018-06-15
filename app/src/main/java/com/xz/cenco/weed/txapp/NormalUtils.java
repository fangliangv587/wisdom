package com.xz.cenco.weed.txapp;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class NormalUtils {
    public static String getUTF8XMLString(String xml) {
        StringBuffer sb = new StringBuffer();
        sb.append(xml);
        String xmString = "";
        try {
             xmString = new String(sb.toString().getBytes("UTF-8"));
           String xmlUTF8 = URLEncoder.encode(xmString, "UTF-8");
            return xmlUTF8;
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
