/**
 * 文  件   名：AppGlobal.java
 * 版          权： Copyright LiJinhua All Rights Reserved.
 * 描          述：日志输出类
 * 创  建   人： LiJinHua
 * 创建时间： 2014-5-31
 * 
 * 修   改  人：
 * 修改时间：
 * 修改内容：[修改内容]
 */
package com.groupseven.voicestamp.tools;

import android.content.Context;


public class AppGlobal {
	
	private static AppGlobal sInstance;

	private Context applicationContext;
	
	private AppGlobal() {
	}
	
	public static AppGlobal getInstance() {
		if (sInstance == null) {
			synchronized (AppGlobal.class) {
				if (sInstance == null) {
					sInstance = new AppGlobal();
				}
			}
		}
		return sInstance;
	}
	
	public void setApplicationContext(Context applicationContext){
		this.applicationContext = applicationContext;
	}
	
	public Context getApplicationContext() {
		return applicationContext;
	}
	
}
