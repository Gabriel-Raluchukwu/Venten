package com.android.udacity.venten_assessment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.udacity.utilities.Constants;
import com.android.udacity.utilities.MessageFormatter;
import com.android.udacity.utilities.Persist;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    public static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 1000;

    SharedPreferences preferences;

    String currentColor;

    TextView colorDisplayTextView;
    TextView codedMessageTextView;
    TextView dateTextView;
    TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferences = getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE);

       //Initialize TextViews;
        colorDisplayTextView = findViewById(R.id.colorDisplay);
        codedMessageTextView = findViewById(R.id.codedMessage);
        dateTextView = findViewById(R.id.dateView);
        timeTextView = findViewById(R.id.timeView);

        PermissionRequest();


        if (preferences.getBoolean(Constants.RESET,true)){
            Persist.SetUpPreferences(this);
        }

        SetUpLayout();
    }

    public void PermissionRequest(){
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.RECEIVE_SMS)){

                new AlertDialog.Builder(this)
                        .setTitle(R.string.sms_permission_dialog_title)
                        .setMessage(R.string.permission_dialog_message)
                        .setPositiveButton(R.string.permission_dialog_positive_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestPermission();
                            }
                        })
                        .setNegativeButton(R.string.permission_dialog_negative_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            }else if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_CONTACTS)) {

                new AlertDialog.Builder(this)
                        .setTitle(R.string.contacts_permission_dialog_title)
                        .setMessage(R.string.permission_dialog_message)
                        .setPositiveButton(R.string.permission_dialog_positive_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RequestPermission();
                            }
                        })
                        .setNegativeButton(R.string.permission_dialog_negative_action, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else{
                RequestPermission();
            }
        }
        else{
            Log.i(TAG, "RequestPermission: PERMISSION GRANTED");
        }
    }

    private void RequestPermission(){
        ActivityCompat.requestPermissions(this ,new String[]{Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,
                Manifest.permission.READ_CONTACTS},
                MY_PERMISSIONS_REQUEST_RECEIVE_SMS);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_RECEIVE_SMS:
               for(int i = 0; i < permissions.length; i++){
                   if (grantResults.length > 0
                           && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                       Toast.makeText(this,R.string.permission_granted,Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(this,
                               R.string.permission_denied,
                               Toast.LENGTH_SHORT).show();
                   }
               }
        }
    }

    private void SetUpLayout(){
        ConstraintLayout.LayoutParams layoutParameters = (ConstraintLayout.LayoutParams) colorDisplayTextView.getLayoutParams();
        layoutParameters.width = pxToDp(Integer.parseInt(preferences.getString(Constants.PREFERENCE_WIDTH,Constants.DEFAULT_WIDTH)));
        layoutParameters.height = pxToDp(Integer.parseInt(preferences.getString(Constants.PREFERENCE_LENGTH,Constants.DEFAULT_LENGTH)));
        colorDisplayTextView.setLayoutParams(layoutParameters);
        currentColor = preferences.getString(Constants.PREFERENCE_COLOR1,Constants.DEFAULT_COLOR1);
        SetBackgroundColor(currentColor);

        SetMessageTextView();
        SetDateTextView();
        SetTimeTextView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        if (selectedMenuItem == R.id.action_toggle_menu_button){
            String color1 = preferences.getString(Constants.PREFERENCE_COLOR1,Constants.DEFAULT_COLOR1);
            String color2 = preferences.getString(Constants.PREFERENCE_COLOR2,Constants.DEFAULT_COLOR2);
           if (currentColor == color1 ){
               currentColor = color2;
               SetBackgroundColor(currentColor);
           }else if(currentColor == color2){
               currentColor = color1;
               SetBackgroundColor(currentColor);
           }
           else{
               SetBackgroundColor(currentColor);
           }
        }
        return super.onOptionsItemSelected(item);
    }

    private void SetMessageTextView(){
        String codedMessage = preferences.getString(Constants.PREFERENCE_CODED_MESSAGE,Constants.DEFAULT_CODED_MESSAGE);
        codedMessageTextView.setText(codedMessage);
    }

    private void SetDateTextView(){
        String finalDate = "";
        Date dateObject = null;
        String date = preferences.getString(Constants.PREFERENCE_DATE,Constants.DEFAULT_DATE);
        DateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy");
        try{
            dateObject = dateFormatter.parse(date);
        }catch (ParseException e){
            //
            finalDate = date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateObject);
        switch (calendar.get(Calendar.DAY_OF_MONTH)){
            case 1 :
                finalDate = new SimpleDateFormat("dd'st' MMMM yyyy").format(dateObject);
                break;
            case 2 :
                finalDate = new SimpleDateFormat("dd'nd' MMMM yyyy").format(dateObject);
                break;
            case 3 :
                finalDate = new SimpleDateFormat("dd'rd' MMMM yyyy").format(dateObject);
                break;
            default:
                finalDate = new SimpleDateFormat("dd'th' MMMM yyyy").format(dateObject);
        }

        dateTextView.setText(getString(R.string.date_text_view,finalDate));
        //dateTextView.setText("Date: " + finalDate);
    }

    private void SetTimeTextView(){
        String time = preferences.getString(Constants.PREFERENCE_TIME,Constants.DEFAULT_TIME);
        timeTextView.setText(getString(R.string.time_text_view,time));
       // dateTextView.setText("Time: " + time);
    }

    private int pxToDp(int parameter ){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (parameter * scale * 0.5f);
    }

    private void SetBackgroundColor(String color){
        String setColor = FormatColor(color);
        colorDisplayTextView.setBackgroundColor(Color.parseColor(setColor));
    }

    private String FormatColor(String color){
        StringBuilder builder = new StringBuilder(color);
        StringBuilder formattedColor = builder.insert(0,"#");
        return formattedColor.toString();
    }
}

