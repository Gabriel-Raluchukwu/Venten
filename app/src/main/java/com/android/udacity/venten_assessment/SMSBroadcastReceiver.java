package com.android.udacity.venten_assessment;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;

import android.telephony.SmsMessage;
import android.os.Bundle;

import com.android.udacity.utilities.Constants;
import com.android.udacity.utilities.MessageFormatter;
import com.android.udacity.utilities.Persist;
import com.android.udacity.utilities.RegexMessageFormatter;

// Reciever class that listens to SMS broadcasts
public class SMSBroadcastReceiver extends BroadcastReceiver {
    private static final String TAG = "SMSBroadcastReciever";
    private static final String pdu = "pdus";
    Cursor result;
    SmsMessage smsMessage;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive: Broadcast SMS Message received ");

        // Get message and convert to SmsMessage format
        Bundle bundle = intent.getExtras();
        if(bundle != null){
            if (Build.VERSION.SDK_INT >= 19) { //KITKAT
               SmsMessage[] message = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                smsMessage = message[0];
            } else {

                Object pdus[] = (Object[]) bundle.get(pdu);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0],bundle.getString("format"));
                }else{
                    smsMessage = SmsMessage.createFromPdu((byte[]) pdus[0]);
                }
            }
        }

        Log.v("TAG", "handleSmsReceived" + (smsMessage.isReplace() ? "(replace)" : "") +
                " messageUri: " +
                ", address: " + smsMessage.getOriginatingAddress() +
                ", body: " + smsMessage.getMessageBody());

        //Get name of message sender from the Contacts content provider
        String contactName = getContactName(smsMessage.getOriginatingAddress(),context);

        //Save to Preferences only if message is from ven10
        if(contactName == "Ven10"){
            SaveToPreferences(context,smsMessage.getMessageBody());
        }
    }

    //Formats message and retrieves relevant information
    //Saves information to shared preferences
    private void SaveToPreferences(Context context,String messageBody){
        //Format
        Persist.SetDate(context, RegexMessageFormatter.retrieveDate(messageBody));
        Persist.SetTime(context,RegexMessageFormatter.retrieveTime(messageBody));
        Persist.SetCodedMessage(context,RegexMessageFormatter.retrievecodedMessage(messageBody));
        Persist.SetWidth(context,RegexMessageFormatter.retrieveWidth(messageBody));
        Persist.SetLength(context,RegexMessageFormatter.retrieveLength(messageBody));
        String [] colors = RegexMessageFormatter.retrieveColors(messageBody);
        Persist.SetColor1(context,colors[0]);
        Persist.SetColor2(context,colors[1]);
    }

    //For Querying SMS Content Provider
     public class Broadcast extends AsyncTask<Void,String, Cursor> {
        private Context context;
        private PendingResult pendingResult;

        private Broadcast(PendingResult pendingResult,Context context){
            this.context = context;
            this.pendingResult = pendingResult;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            //Retrieve Message from SMS content Provider and check if its from ven10
            ContentResolver contentResolver = context.getContentResolver();
            result = null;
            try{
                result = contentResolver.query(Telephony.Sms.Inbox.CONTENT_URI,null,null ,null,null);

            }catch(Exception e){
                Log.d("SMSBroadcastReceiver", e.getMessage());
            }
            return result;
        }

         @Override
         protected void onPostExecute(Cursor cursor) {
             super.onPostExecute(cursor);
             pendingResult.finish();
         }
     }

     //Gets contact name from Contacts Content provider
     public String getContactName(final String phoneNumber, Context context)
     {
         Uri uri=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,Uri.encode(phoneNumber));

         String[] projection = new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME};

         String contactName="";
         Cursor cursor=context.getContentResolver().query(uri,projection,null,null,null);

         if (cursor != null) {
             if(cursor.moveToFirst()) {
                 contactName=cursor.getString(0);
             }
             cursor.close();
         }

         return contactName;
     }
}
