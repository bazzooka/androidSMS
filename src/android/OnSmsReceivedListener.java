package cordova.android.sms.plugin;

public interface OnSmsReceivedListener {
    public void onSmsReceived(String sender, String message);
}