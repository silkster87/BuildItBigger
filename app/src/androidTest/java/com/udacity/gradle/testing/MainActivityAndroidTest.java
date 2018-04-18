package com.udacity.gradle.testing;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v4.util.Pair;
import android.test.AndroidTestCase;
import android.text.TextUtils;

import com.example.android.javajoketeller.JokeTeller;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;


@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidTest extends AndroidTestCase{


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testVerifyAsyncTaskTakesNonEmptyString(){
        JokeTeller jokeTeller = new JokeTeller();
        String joke = jokeTeller.tellJoke();
        String result = null;
        Context context = mActivityTestRule.getActivity().getApplicationContext();

        try{
            EndpointsAsyncTask jokeTask = new EndpointsAsyncTask();
            jokeTask.execute(new Pair<>(context, joke));
            result = jokeTask.get(30, TimeUnit.SECONDS);
        } catch (Exception e){
            e.printStackTrace();
            fail("Timed out");
        }

        assertFalse(TextUtils.isEmpty(result));
    }
}
