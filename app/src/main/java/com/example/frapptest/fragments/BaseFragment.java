package com.example.frapptest.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.frapptest.AppOperationAware;
import com.example.frapptest.BaseActivity;
import com.example.frapptest.R;
import com.example.frapptest.utilities.Utils;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class BaseFragment extends Fragment implements AppOperationAware {
    private boolean isFragmentSuspended;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isFragmentSuspended = false;
    }

    @Override
    public void onResume() {
        super.onResume();
        isFragmentSuspended = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        isFragmentSuspended = true;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isFragmentSuspended = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        isFragmentSuspended = false;
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean isSuspended() {
        return getCurrentActivity() != null && getCurrentActivity().isSuspended() || isFragmentSuspended;
    }

    @Override
    public void setSuspended(boolean state) {
        this.isFragmentSuspended = state;
    }

    @Override
    public boolean isOffline() {
        return getContext() != null && !Utils.isConnectionOnline(getContext());
    }

    @Override
    public void showAlertDialog(String title, String msg, boolean finish) {

    }

    @Override
    public BaseActivity getCurrentActivity() {
        if (getActivity() == null) return null;
        return (BaseActivity) getActivity();
    }


    protected void toggleProgress(boolean show) {
        toggleProgress(show, null);
    }

    protected void toggleProgress(boolean show, @Nullable String msg) {
        if (getCurrentActivity() != null) {
            getCurrentActivity().toggleProgress();
        }
    }

    public void handleOfflineException(boolean finish) {
        showAlertDialog(getString(R.string.offline_title),
                getString(R.string.offline_message), finish);
    }

    public void handleOfflineException() {
        handleOfflineException(false);
    }
}
