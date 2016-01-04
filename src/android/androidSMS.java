package cordova.android.sms.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaWebView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.IntentFilter;
import android.util.Log;

import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Profile;
import android.net.Uri;
import android.database.Cursor;
import android.content.ContentResolver;

/**
 * This class echoes a string called from JavaScript.
 */
public class androidSMS extends CordovaPlugin {

   
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	return true;
    }

    private void coolMethod(String message, CallbackContext callbackContext) {
        
    }
}
