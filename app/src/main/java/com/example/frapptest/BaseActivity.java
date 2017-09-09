package com.example.frapptest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements AppOperationAware {

    private boolean isActivitySuspended;
    @Nullable
    private ProgressDialog mProgressDialog;

    @LayoutRes
    protected abstract int getMainLayout();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getMainLayout());
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mHelper.onStart(this);
        isActivitySuspended = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        //mHelper.onStop(this);
        isActivitySuspended = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mHelper.onResume(this);
        isActivitySuspended = false;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivitySuspended = true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        isActivitySuspended = false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        isActivitySuspended = false;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        isActivitySuspended = true;
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public boolean isSuspended() {
        return false;
    }

    @Override
    public void setSuspended(boolean state) {

    }

    @Override
    public boolean isOffline() {
        return false;
    }

    @Override
    public void showAlertDialog(String title, String msg, boolean finish) {

    }

    @Override
    public BaseActivity getCurrentActivity() {
        return null;
    }
}
