package cordova.android.sms.plugin;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.IntentFilter;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.speech.tts.TextToSpeech;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Profile;
import android.net.Uri;
import android.database.Cursor;
import android.content.ContentResolver;

import com.red_folder.phonegap.plugin.backgroundservice.BackgroundService;

public class MyService extends BackgroundService implements OnSmsReceivedListener, TextToSpeech.OnInitListener {

	private final static String TAG = MyService.class.getSimpleName();

	private String mHelloTo = "World";
	private SmsBroadcastReceiver mSMSreceiver;
	private IntentFilter mIntentFilter;
	private TextToSpeech mTts;
    private final int CHECK_CODE = 0x1;

	@Override
    public void onCreate(){
        super.onCreate();

        mTts = new TextToSpeech(this,
                this  // OnInitListener
                );
	      mTts.setSpeechRate(0.8f);
	      Log.v(TAG, "oncreate_service");

        //SMS event receiver
        mSMSreceiver = new SmsBroadcastReceiver();
        mSMSreceiver.setOnSmsListener(this);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        registerReceiver(mSMSreceiver, mIntentFilter);

    }

    @Override
	public void onStart(Intent intent, int startId) {


	    this.speak("Demarrage");

	    Log.v(TAG, "onstart_service");
	    super.onStart(intent, startId);
	}

	@Override
	public void onInit(int status) {
	    Log.v(TAG, "oninit");
	     if (status == TextToSpeech.SUCCESS) {
	            int result = mTts.setLanguage(new Locale("FR", "fr"));
	            if (result == TextToSpeech.LANG_MISSING_DATA ||
	                result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.v(TAG, "Language is not available.");
	            }
	        } else {
	            Log.v(TAG, "Could not initialize TextToSpeech.");
	        }
	}

	public void speak(String str) {
      mTts.speak(str,
                TextToSpeech.QUEUE_ADD,
                null);
	}

	public void playSilence(int duration){
		mTts.playSilence(duration, TextToSpeech.QUEUE_ADD, null);
	}

    @Override
	public void onSmsReceived(String sender, String message) {
	    Log.d(TAG, sender + " " + message);
	    this.doWork();
	    Log.d(TAG, "Nouveau message de " + this.getContactName(sender));
	    speak("Nouveau message de " + this.getContactName(sender));
	    playSilence(1000);
	    speak(message);
	}

	private String getContactName(String phone){
		// Uri sms = Uri.parse("content://sms/inbox");
  //       ContentResolver cr = this.getContentResolver();
  //       Cursor c = cr.query(sms, null, null, null, null);
  //       for (int i = 0; i < c.getColumnCount(); i++)
  //       {
  //           Log.v(TAG, c.getColumnName(i).toString());
  //       }

  //       if(c.moveToFirst()){
	 //    	Log.d(TAG, c.getString(0));
	 //    	Log.v(TAG, c.getString(1));
	 //    	Log.v(TAG, c.getString(2));
	 //    	Log.v(TAG, c.getString(3));
	 //    	Log.v(TAG, c.getString(4));
	 //    	Log.v(TAG, c.getString(5));
	 //    	Log.v(TAG, c.getString(6));
	 //    	c.close();
	 //        return c.getString(0);
	 //    }else {
	 //    	c.close();
	 //        return phone;
	 //    }

		String name = phone;
		Uri uri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phone));
		ContentResolver cr = this.getContentResolver();
		Cursor c = cr.query(uri, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);

        if(c.moveToFirst()){
        	name = c.getString(0);
        	Log.d(TAG, c.getString(0));
        }
        c.close();

        return name;



	}

	@Override
	protected JSONObject doWork() {
		JSONObject result = new JSONObject();

		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String now = df.format(new Date(System.currentTimeMillis()));

			String msg = "Hello " + this.mHelloTo + " - its currently " + now;
			result.put("Message", msg);
			//speaker.speak("You have a new message from" + "JOE" + "!");
			Log.d(TAG, msg);
		} catch (JSONException e) {
		}

		return result;
	}

	@Override
	protected JSONObject getConfig() {
		JSONObject result = new JSONObject();

		try {
			result.put("HelloTo", this.mHelloTo);
		} catch (JSONException e) {
		}

		return result;
	}

	@Override
	protected void setConfig(JSONObject config) {
		try {
			if (config.has("HelloTo"))
				this.mHelloTo = config.getString("HelloTo");
		} catch (JSONException e) {
		}

	}

	@Override
	protected JSONObject initialiseLatestResult() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onTimerEnabled() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onTimerDisabled() {
		// TODO Auto-generated method stub

	}


}
