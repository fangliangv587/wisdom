package com.xz.cenco.weed.aiaixg;

import okhttp3.Headers;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


/**
 * Created by Administrator on 2018/4/25.
 */
public class Util {


    public static String getHeaderValue(Response response,String key) {
        if (response == null || key == null) {
            return "";
        }
        Headers headers = response.headers();
        if (headers == null && headers.size() == 0) {
            return "";
        }


        Set<String> names = headers.names();
        Iterator<String> iterator = names.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            List<String> values = headers.values(next);
            if (values != null && values.size() != 0) {
                String value = null;
                if (values.size() == 1) {
                    value = values.get(0);
                } else {
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < values.size(); i++) {
                        String s = values.get(i);
                        sb.append(s);
                        sb.append(";");
                    }
                    value = sb.toString();

                    String[] split = value.split(";");
                    if (split != null && split.length != 0) {
                        for (String part : split) {
                            if (part.contains("=")) {
                                String[] split1 = part.split("=");
                                if (split1 != null && split1.length == 2) {
                                    String k1 = split1[0];
                                    String v1 = split1[1];

                                        if (k1.equals(key)) {
                                            return v1;
                                        }
                                }
                            }
                        }
                    }
                }
//                System.out.println(next+":"+value);
                    if (next.equals(key)) {
                        return value;
                    }
            }
        }

        return "";
    }
}
