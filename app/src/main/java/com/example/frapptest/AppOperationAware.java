package com.example.frapptest;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public interface AppOperationAware {
    boolean isSuspended();
    void setSuspended(boolean state);
    boolean isOffline();
    void showAlertDialog(String title, String msg, boolean finish);
    BaseActivity getCurrentActivity();
}
