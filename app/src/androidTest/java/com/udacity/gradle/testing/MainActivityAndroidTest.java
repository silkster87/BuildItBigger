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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;


@RunWith(AndroidJUnit4.class)
public class MainActivityAndroidTest extends AndroidTestCase{

    private JokeTeller jokeTeller = new JokeTeller();
    private String joke = jokeTeller.tellJoke();
    private String result = null;

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);


    @Test
    public void testVerifyJokeIsNotNull(){
        assertFalse(TextUtils.isEmpty(joke));
    }

    @Test
    public void testVerifyAsyncTaskTakesNonEmptyString(){
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
        assertFalse(result.contains("error"));
    }
}
