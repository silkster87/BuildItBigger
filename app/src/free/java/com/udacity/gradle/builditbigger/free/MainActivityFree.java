package com.udacity.gradle.builditbigger.free;

import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.javajoketeller.JokeTeller;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.R;

public class MainActivityFree extends AppCompatActivity {

    private InterstitialAd mInterstitial;
    public ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);

        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        AdRequest request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitial.loadAd(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {

        if(mInterstitial.isLoaded()){
            mInterstitial.show();
        }

        mInterstitial.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                spinner.setVisibility(View.VISIBLE);
                JokeTeller jokeTeller = new JokeTeller();
                String joke = jokeTeller.tellJoke();
                //  Toast.makeText(this, jokeTeller.tellJoke(), Toast.LENGTH_SHORT).show();
                EndpointsAsyncTask getTask = new EndpointsAsyncTask();
                getTask.setListener(new EndpointsAsyncTask.EndPointListener() {
                    @Override
                    public void finishTask() {
                        spinner.setVisibility(View.GONE);
                    }
                });
                getTask.execute(new Pair<>(getApplicationContext(), joke));
            }
        });

    }
}
