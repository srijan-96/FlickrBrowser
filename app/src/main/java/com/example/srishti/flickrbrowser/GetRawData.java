package com.example.srishti.flickrbrowser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Srishti on 16-12-2017.
 */
enum DownloadStatus {
    IDLE, PROCESSING, NOT_INITIALISED, FAILED_OR_EMPTY, OK
}

public class GetRawData {
    private String LOG_TAG = GetRawData.class.getSimpleName();
    private String mRawUrl;
    private String mData;
    private DownloadStatus mDownloadStatus;

    public GetRawData(String mRawUrl) {
        this.mRawUrl = mRawUrl;
        this.mDownloadStatus = DownloadStatus.IDLE;
    }

    public void reset() {
        this.mDownloadStatus = DownloadStatus.IDLE;
        this.mRawUrl = null;
        this.mData = null;
    }

    public String getmData() {
        return mData;
    }

    public DownloadStatus getmDownloadStatus() {
        return mDownloadStatus;
    }

    public void setmRawUrl(String mRawUrl) {
        this.mRawUrl = mRawUrl;
    }

    public void execute() {
        mDownloadStatus = DownloadStatus.PROCESSING;
        DownloadRawData downloadRawData = new DownloadRawData();
        downloadRawData.execute(mRawUrl);
    }


    /// Downloading the raw Data---> Using json file///


    public class DownloadRawData extends AsyncTask<String, Void, String> {

        protected void onPostExecute(String webData) {

            mData = webData;
            Log.v(LOG_TAG, "Data returned was: " + mData);
            if (mData == null) {
                if (mRawUrl == null) {
                    mDownloadStatus = DownloadStatus.NOT_INITIALISED;
                } else {
                    mDownloadStatus = DownloadStatus.FAILED_OR_EMPTY;
                }
            } else {
                // Success
                mDownloadStatus = DownloadStatus.OK;
            }


        }

        //Making the conection and recieving Data as String froom the json URL///
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            if (params == null)
                return null;

            try {
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream == null) {  // No Data
                    return null;
                }

                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {   // Reading and appending Data in the output String
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (IOException e) {          // Close Open Connections
                Log.e(LOG_TAG, "Error", e);    // LOG_TAG --> which class the error comes from
                e.printStackTrace();
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {            // Close Open Streams
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}