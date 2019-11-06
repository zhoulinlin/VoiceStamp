/**
 * 文  件   名：JSONTool.java
 * 版          权： Copyright PingAn Technology All Rights Reserved.
 * 描          述：
 * 创  建   人： LIJINHUA922   E-mail:LIJINHUA922@pingan.com.cn
 * 创建时间： 2014-8-7 下午9:10:19
 * 
 * 修   改  人：
 * 修改时间：
 * 修改内容：[修改内容]
 */
package com.groupseven.voicestamp.tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class JSONTool {


    /**
     * 从JSONObject中获取keys的值
     * @param json
     * @param keys
     * @return
     */
    public static Object getValuseFromJSONObject(JSONObject json, String...keys){
    	Object valuse = null;
    	if(json == null || keys == null){
    		return valuse;
    	}
    	try {
    		int size = keys.length;
    		JSONObject jsonObject = json;
    		for(int i=0;i<size;i++){
    			String key = keys[i];
    			if(i <(size-1)){
    				jsonObject = jsonObject.getJSONObject(key);
    			}else{
    				//最后一个
    				valuse = jsonObject.get(key);
    			}
    		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return valuse;
    }
    
    /**
     * JSONObject 转换成 HashMap
     * @return
     */
    public static HashMap<String, Object> getMapFromJSONObject(JSONObject json){
    	HashMap<String, Object> valuse = null;
    	if(json == null){
    		return valuse;
    	}
    	Iterator<?> it = json.keys();
    	if(it == null){
    		return valuse;
    	}
    	valuse = new HashMap<String, Object>();
    	while(it.hasNext()){
    		//依次将json中的值移到HashMap中
    		String key = (String) it.next();
    		valuse.put(key,json.optString(key));
    	}
		return valuse;
    }
}
