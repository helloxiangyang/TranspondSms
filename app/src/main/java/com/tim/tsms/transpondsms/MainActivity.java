package com.tim.tsms.transpondsms;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tim.tsms.transpondsms.utils.Define;
import com.tim.tsms.transpondsms.utils.SendHistory;
import com.tim.tsms.transpondsms.utils.SendMailUtil;
import com.tim.tsms.transpondsms.utils.SendUtil;

public class MainActivity extends AppCompatActivity {

    private IntentFilter intentFilter;
    private SMSBroadcastReceiver smsBroadcastReceiver;
    private TextView textv_msg;
    private CheckBox cb_dingding;
    private CheckBox cb_email;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"oncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textv_msg=(TextView) findViewById(R.id.textv_msg);
        cb_dingding=(CheckBox) findViewById(R.id.checkDingding);
        cb_email=(CheckBox) findViewById(R.id.checkEmail);

        textv_msg.setMovementMethod(ScrollingMovementMethod.getInstance());
        textv_msg.setText(SendHistory.getHistory());


        if(SendUtil.send_util_using.contains(Define.DingDing)){
            cb_dingding.setChecked(true);
        }else{cb_dingding.setChecked(false);}

        if(SendUtil.send_util_using.contains(Define.Email)){
            cb_email.setChecked(true);
        }else{cb_email.setChecked(false);}

        cb_dingding.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    SendUtil.set_send_util(Define.DingDing,true);
                }else{
                    SendUtil.set_send_util(Define.DingDing,false);
                }
            }
        });
        cb_email.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if(isChecked){
                    SendUtil.set_send_util(Define.Email,true);
                }else{
                    SendUtil.set_send_util(Define.Email,false);
                }
            }
        });

//        intentFilter=new IntentFilter();
//        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
//        intentFilter.addAction(MessageBroadcastReceiver.ACTION_DINGDING);
//        smsBroadcastReceiver=new SMSBroadcastReceiver();
//        //动态注册广播
//        registerReceiver(smsBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG,"onDestroy");
        super.onDestroy();
        //取消注册广播
        unregisterReceiver(smsBroadcastReceiver);
    }

    public void sendMsg(View view){
        try{
            //6位数随机数
//            DingdingMsg.sendMsg(Integer.toString((int) (Math.random()*9+1)*100000));
            SendUtil.send_msg(Integer.toString((int) (Math.random()*9+1)*100000));
//            SendMailUtil.send("1547681531@qq.com","s","2");
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void editSendUtil(View view){
        if(view.getId()==R.id.floatingActionButtonEmail){
            final AlertDialog.Builder alertDialog71 = new AlertDialog.Builder(MainActivity.this);
            View view1 = View.inflate(MainActivity.this, R.layout.activity_alter_dialog_setview_email, null);

            final EditText editTextEmailHost = view1.findViewById(R.id.editTextEmailHost);
            editTextEmailHost.setText(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_HOST_KEY));
            final EditText editTextEmailPort = view1.findViewById(R.id.editTextEmailPort);
            editTextEmailPort.setText(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_PORT_KEY));
            final EditText editTextEmailFromAdd = view1.findViewById(R.id.editTextEmailFromAdd);
            editTextEmailFromAdd.setText(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_FROMADD_KEY));
            final EditText editTextEmailPsw = view1.findViewById(R.id.editTextEmailPsw);
            editTextEmailPsw.setText(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_PSW_KEY));
            final EditText editTextEmailToAdd = view1.findViewById(R.id.editTextEmailToAdd);
            editTextEmailToAdd.setText(SendUtil.get_send_util_email(Define.SP_MSG_SEND_UTIL_EMAIL_TOADD_KEY));

            Button bu = view1.findViewById(R.id.buttonemailok);
            alertDialog71
                    .setTitle(R.string.setemailtitle)
                    .setIcon(R.mipmap.ic_launcher)
                    .setView(view1)
                    .create();
            final AlertDialog show = alertDialog71.show();
            bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendUtil.set_send_util_email(editTextEmailHost.getText().toString(),editTextEmailPort.getText().toString(),editTextEmailFromAdd.getText().toString(),editTextEmailPsw.getText().toString(),editTextEmailToAdd.getText().toString());
                    show.dismiss();
                }
            });
        }
        if(view.getId()==R.id.floatingActionButtonDingding){
            final AlertDialog.Builder alertDialog7 = new AlertDialog.Builder(MainActivity.this);
            View view1 = View.inflate(MainActivity.this, R.layout.activity_alter_dialog_setview_dingding, null);
            final EditText et = view1.findViewById(R.id.editTextDingdingToken);
            et.setText(SendUtil.get_send_util_dingding_token());
            Button bu = view1.findViewById(R.id.buttondingdingok);
            alertDialog7
                    .setTitle(R.string.setdingdingtitle)
                    .setIcon(R.mipmap.dingding)
                    .setView(view1)
                    .create();
            final AlertDialog show = alertDialog7.show();
            bu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SendUtil.set_send_util_dingding(et.getText().toString());
                    show.dismiss();
                }
            });
        }

        Toast.makeText(this,"ttt"+view.getId(),Toast.LENGTH_LONG).show();

    }

    public void showMsg(View view){
        Log.d(TAG,"showMsg");
        String showMsg =SendHistory.getHistory();
        textv_msg.setText(showMsg);

    }

}
