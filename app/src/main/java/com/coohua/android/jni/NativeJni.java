package com.coohua.android.jni;

import android.util.Base64;

public class NativeJni
{
  private static final int KEY_BASE_KEY = 3;
  private static final int KEY_COMMON = 0;
  private static final int KEY_SHARE = 2;
  private static final int KEY_TASK = 1;

  static
  {
    try
    {
      System.loadLibrary("native-jni");

    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }

  public static String getBaseKey()
  {
    return new String(getEncryteKey(3));
  }

  public static byte[] getCommonKey()
  {
    return Base64.decode(getEncryteKey(0), 8);
  }

  private static native byte[] getEncryteKey(int paramInt);

  public static String getShareKey()
  {
    return new String(getEncryteKey(2));
  }

  public static String getTaskKey()
  {
    return new String(getEncryteKey(1));
  }
}