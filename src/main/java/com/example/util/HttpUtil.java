package com.example.util;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class HttpUtil {

    public static String postHttpRequest(String url, String postStr) {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod(url);
        try {
            if(postStr != null){
                StringRequestEntity se = new StringRequestEntity(postStr ,"application/json" ,"UTF-8");
                method.setRequestEntity(se);
            }
            method.setRequestHeader("Content-Type","application/json");
            String responseStr = null;

            client.executeMethod(method);
            if("ISO-8859-1".equals(method.getResponseCharSet())){
                responseStr = new String(method.getResponseBody(), "UTF-8");
            }else{
                responseStr = method.getResponseBodyAsString();
            }
            method.releaseConnection();
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("访问接口失败");
        }
    }
    public static String getHttpRequest(String url, NameValuePair[] nameValuePairs){
        HttpClient client = new HttpClient();
        GetMethod method = new GetMethod(url);
        method.setQueryString(nameValuePairs);
        String responseStr = null;
        try {
            client.executeMethod(method);
            responseStr = method.getResponseBodyAsString();
            method.releaseConnection();
            return responseStr;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("访问接口失败");
        }
    }
}
