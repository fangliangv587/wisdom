package com.xz.cenco.weed.coohua;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2018/5/4.
 */

public class Util {


    public static void test(){

    }

    public static byte[] adddd(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    {
        if ((paramArrayOfByte1 == null) || (paramArrayOfByte2 == null))
            return null;
        try
        {
            paramArrayOfByte1 = a(paramArrayOfByte1, paramArrayOfByte2);
            return paramArrayOfByte1;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Key a(byte[] paramArrayOfByte)
    {
        return new SecretKeySpec(paramArrayOfByte, "AES");
    }

    public static byte[] a(byte[] paramArrayOfByte, Key paramKey, String paramString)
            throws Exception
    {
        Cipher  cipher = Cipher.getInstance(paramString);
        cipher.init(1, paramKey);
        return cipher.doFinal(paramArrayOfByte);
    }

    public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
            throws Exception
    {
        return a(paramArrayOfByte1, paramArrayOfByte2, "AES/ECB/PKCS5Padding");
    }

    public static byte[] a(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
            throws Exception
    {
        return a(paramArrayOfByte1, a(paramArrayOfByte2), paramString);
    }
}
