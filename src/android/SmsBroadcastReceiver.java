package cordova.android.sms.plugin;
import org.apache.cordova.CallbackContext;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import org.apache.cordova.*;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;



public class SmsBroadcastReceiver extends BroadcastReceiver {
    private final String TAG = this.getClass().getSimpleName();
    private OnSmsReceivedListener listener = null;


    public void setOnSmsListener(Context context) {
        this.listener = (OnSmsReceivedListener) context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle myBundle = intent.getExtras();
        SmsMessage[] messages = null;
        Intent data;
        String sender;
        String message;

        if (myBundle != null) {
            Object[] pdus = (Object[]) myBundle.get("pdus");
            messages = new SmsMessage[pdus.length];

            for (int i = 0; i < messages.length; i++) {
                messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);

                String msgBody = messages[i].getMessageBody();
                String msgFromAddress =  messages[i].getOriginatingAddress();

                Log.d(TAG, "SmsReceiver: " + msgBody + " " + msgFromAddress);

                JSONObject obj = new JSONObject();
                try {
                    message = msgBody;
                    sender = msgFromAddress;
                    obj.put("msg", msgBody);
                    obj.put("sender", msgFromAddress);
                    if (listener != null) {
                        listener.onSmsReceived(msgFromAddress, msgBody);
                    }
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}