package com.tim.tsms.transpondsms.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

public class SendUtil {
    private static String TAG = "SendUtil";
    //转发发送配置
    public static Set<String> send_util_using = new HashSet<>();

    static Context context;

    public static void init(Context context1){
        context = context1;
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        send_util_using = sp.getStringSet(Define.SP_MSG_SEND_UTIL_USING_SET_KEY,send_util_using);
        Log.d(TAG,"init \nsend_util_using"+send_util_using.toString());
    }

    public static void set_send_util(String util_name,boolean add){
        Log.d(TAG,"set_send_util util_name:"+util_name+"add:"+Boolean.toString(add));
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        Set<String> msg_set_default = new HashSet<>();
        Set<String> util_using_set;
        util_using_set = sp.getStringSet(Define.SP_MSG_SEND_UTIL_USING_SET_KEY,msg_set_default);
        if(util_name.equals(Define.DingDing)||util_name.equals(Define.Email)){
            if(add){
                util_using_set.add(util_name);
            }else{
                util_using_set.remove(util_name);
            }

        }
        send_util_using = util_using_set;
        sp.edit().putStringSet(Define.SP_MSG_SEND_UTIL_USING_SET_KEY,util_using_set).apply();
    }

    public static void set_send_util_email(String host,String port,String from_add,String psw,String to_add){
        Log.d(TAG,"set_send_util_email host:"+host+"port"+port+"from_add"+from_add+"psw"+psw+"to_add"+to_add);
        //验证
        if(host.equals("")||port.equals("")||from_add.equals("")||psw.equals("")||to_add.equals("")){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        sp.edit()
                .putString(Define.SP_MSG_SEND_UTIL_EMAIL_HOST_KEY,host)
                .putString(Define.SP_MSG_SEND_UTIL_EMAIL_PORT_KEY,port)
                .putString(Define.SP_MSG_SEND_UTIL_EMAIL_FROMADD_KEY,from_add)
                .putString(Define.SP_MSG_SEND_UTIL_EMAIL_PSW_KEY,psw)
                .putString(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY,to_add)
                .apply();
    }

    public static String get_send_util_email(String key){
        Log.d(TAG,"get_send_util_email  key"+key);
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        String defaultstt ="";
        if(key.equals(Define.SP_MSG_SEND_UTIL_EMAIL_HOST_KEY)) defaultstt = "stmp服务器";
        if(key.equals(Define.SP_MSG_SEND_UTIL_EMAIL_PORT_KEY)) defaultstt = "端口";
        if(key.equals(Define.SP_MSG_SEND_UTIL_EMAIL_FROMADD_KEY)) defaultstt = "发送邮箱";
        if(key.equals(Define.SP_MSG_SEND_UTIL_EMAIL_PSW_KEY)) defaultstt = "密码";
        if(key.equals(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY)) defaultstt = "接收邮箱";
        return sp.getString(key,defaultstt);
    }

    public static void set_send_util_dingding(String dingdingtoken){
        Log.d(TAG,"set_send_util_dingding dingdingtoken:"+dingdingtoken);
        //验证
        if(dingdingtoken.equals("")){
            return;
        }
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        sp.edit().putString(Define.SP_MSG_SEND_UTIL_DINGDING_TOKEN_KEY,dingdingtoken).apply();
    }

    public static String get_send_util_dingding_token(){
        Log.d(TAG,"get_send_util_dingding ");
        SharedPreferences sp = context.getSharedPreferences(Define.SP_MSG,Context.MODE_PRIVATE);
        return sp.getString(Define.SP_MSG_SEND_UTIL_DINGDING_TOKEN_KEY,"");
    }

    public static void send_msg(String msg){
        if(send_util_using.contains(Define.DingDing)){
            try {
                DingdingMsg.sendMsg(msg);
            }catch (Exception e){
                Log.d(TAG,"发送出错："+e.getMessage());
            }

        }
        if(send_util_using.contains(Define.Email)){
            SendMailUtil.send(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY),"转发",msg);
        }

    }

}
