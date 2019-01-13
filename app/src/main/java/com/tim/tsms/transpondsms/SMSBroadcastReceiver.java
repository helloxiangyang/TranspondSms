package com.tim.tsms.transpondsms;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;

import com.tim.tsms.transpondsms.utils.DingdingMsg;
import com.tim.tsms.transpondsms.utils.SendUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SMSBroadcastReceiver  extends BroadcastReceiver {
    private String TAG = "SMSBroadcastReceiver";
    @Override
    public void onReceive(Context arg0, Intent intent) {
        Log.d(TAG,"onReceive intent "+intent.getAction());
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){

            Object[] object=(Object[]) intent.getExtras().get("pdus");
            StringBuilder sb=new StringBuilder();
            for (Object pdus : object) {
                byte[] pdusMsg=(byte[]) pdus;
                SmsMessage sms=SmsMessage.createFromPdu(pdusMsg);
                String mobile=sms.getOriginatingAddress();//发送短信的手机号
                String content=sms.getMessageBody();//短信内容
                //下面是获取短信的发送时间
                Date date=new Date(sms.getTimestampMillis());
                String date_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                //追加到StringBuilder中
                sb.append("短信发送号码："+mobile+"\n短信内容："+content+"\n发送时间："+date_time+"\n\n");

            }
            SendUtil.send_msg(sb.toString());

            Log.d(TAG,"短信："+sb.toString());
        }

    }

}
