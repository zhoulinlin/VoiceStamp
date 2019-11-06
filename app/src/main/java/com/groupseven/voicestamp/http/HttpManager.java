package com.groupseven.voicestamp.http;




import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class HttpManager {


//    /**
//     * 发送https请求共用体
//     */
//    public static JSONObject sendPost(String url, String parame, Map<String,Object> pmap) throws IOException, KeyManagementException, NoSuchAlgorithmException, NoSuchProviderException {
//        // 请求结果
//        JSONObject json = new JSONObject();
//        PrintWriter out = null;
//        BufferedReader in = null;
//        String result = "";
//        URL realUrl;
//        HttpsURLConnection conn;
//        String method = "POST";
//        //查询地址
//        String queryString = url;
//        //请求参数获取
//        String postpar = "";
//        //字符串请求参数
//        if(parame!=null){
//            postpar = parame;
//        }
//        // map格式的请求参数
//        if(pmap!=null){
//            StringBuffer mstr = new StringBuffer();
//            for(String str:pmap.keySet()){
//                String val = (String) pmap.get(str);
//                try {
//                    val= URLEncoder.encode(val,"UTF-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
//                mstr.append(str+"="+val+"&");
//            }
//            // 最终参数
//            postpar = mstr.toString();
//            int lasts=postpar.lastIndexOf("&");
//            postpar=postpar.substring(0, lasts);
//        }
//        if(method.toUpperCase().equals("GET")){
//            queryString+="?"+postpar;
//        }
//        SSLSocketFactory ssf= HttpsClientUtils.getSSFactory();
//        try {
//            realUrl= new URL(queryString);
//            conn = (HttpsURLConnection)realUrl.openConnection();
//            conn.setSSLSocketFactory(ssf);
//            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//            conn.setRequestProperty("accept", "*/*");
//            conn.setRequestProperty("connection", "Keep-Alive");
//            conn.setRequestProperty("user-agent",
//                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
//            if(method.toUpperCase().equals("POST")){
//                conn.setDoOutput(true);
//                conn.setDoInput(true);
//                conn.setUseCaches(false);
//                out = new PrintWriter(conn.getOutputStream());
//                out.print(postpar);
//                out.flush();
//            }else{
//                conn.connect();
//            }
//            in = new BufferedReader(
//                    new InputStreamReader(conn.getInputStream(),"utf-8"));
//            String line;
//            while ((line = in.readLine()) != null) {
//                result += line;
//            }
//            json = JSONObject.fromObject(result);
//        }finally {
//            try {
//                if (out != null) {
//                    out.close();
//                }
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return json;
//    }

}
