package com.udacity.gradle.builditbigger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.example.android.androidjoketeller.JokeTellingActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
    private MyApi myApiService = null;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    private Exception mError = null;

    EndPointListener listener;

    public interface EndPointListener{
         void finishTask();
    }

    public EndpointsAsyncTask setListener(EndPointListener listener){
        this.listener = listener;
        return this;
    }

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0].first;
        String name = params[0].second;

        try {
            return myApiService.sayHi(name).execute().getData();
        } catch (IOException e) {
            mError = e;
            return e.getMessage();
        }
    }


    @Override
    protected void onPostExecute(String result) {
        if(listener!=null) this.listener.finishTask();
        Intent intent = new Intent(context, JokeTellingActivity.class);
        intent.putExtra(context.getResources().getString(R.string.joke_string), result);
        context.startActivity(intent);
    }
}


