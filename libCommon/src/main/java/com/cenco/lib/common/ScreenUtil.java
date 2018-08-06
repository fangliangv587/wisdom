package com.cenco.lib.common;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class ScreenUtil {

	private static final String TAG = ScreenUtil.class.getSimpleName();

	private static int screenWidth = 0;
	private static int screenHeight = 0;
	private static int screenDensityDpi = 0;

	@SuppressWarnings("deprecation")
	private static void getScreenSize(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);

		int width = wm.getDefaultDisplay().getWidth();
		int height = wm.getDefaultDisplay().getHeight();
		screenWidth = width;
		screenHeight = height;
	}

	/**
	 * 获取状态栏高度
	 * @param context
	 * @return
	 */
	public static  int getStatusBarHeight(Context context){
		int result = 0;
		int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
		if (resourceId > 0) {
			result = context.getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}
	
	/**
	 * 获取屏幕宽度
	 * @param context
	 * @return
	 */
	public static int getScreenWidth(Context context){
		if (screenWidth == 0) {
			getScreenSize(context);
			return screenWidth;
		}
		return screenWidth;
	}
	
	/**
	 * 获取屏幕高度
	 * @param context
	 * @return
	 */
	public static int getScreenHeight(Context context){
		if (screenHeight == 0) {
			getScreenSize(context);
			return screenHeight;
		}
		return screenHeight;
	}

	public static int getScreenDensity(Activity context){
		if (screenDensityDpi == 0){
			DisplayMetrics metrics = new DisplayMetrics();
			context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
			screenDensityDpi = metrics.densityDpi;
			return screenDensityDpi;
		}
		return screenDensityDpi;

	}
	
	/**
	 * 输出等比例的宽高最小值
	 * @param context
	 * @param rate 0-1
	 * @return
	 */
	public static int getEqualSize(Context context, float rate){
		if (screenHeight == 0 || screenWidth == 0) {
			getScreenSize(context);
		}
		
		int w = (int) (screenWidth * rate);
		int h = (int) (screenHeight * rate);
		
		return w < h ? w : h;
		
	}


	
	
}
