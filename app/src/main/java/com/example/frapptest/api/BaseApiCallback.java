package com.example.frapptest.api;


import com.example.frapptest.AppOperationAware;
import com.example.frapptest.BaseActivity;
import com.example.frapptest.R;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public abstract class BaseApiCallback<T> implements Callback<T> {
    private WeakReference<AppOperationAware> contextWeakReference;
    private boolean finishOnFailure;

    public BaseApiCallback(AppOperationAware ctx){
        this.contextWeakReference = new WeakReference<AppOperationAware>(ctx);
    }

    public BaseApiCallback(AppOperationAware ctx, boolean finishOnFailure){
        this(ctx);
        this.finishOnFailure = finishOnFailure;
    }
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (contextWeakReference == null || contextWeakReference.get() == null || call.isCanceled()) return;
        if ((contextWeakReference.get()).isSuspended()) return;
        boolean isSuccess = response.isSuccessful();
        if (!updateProgress(!isSuccess)) return;
        if (isSuccess){
            onSuccess(response.body());
        }else{
            onFailure(response.code(), response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        if (contextWeakReference == null || contextWeakReference.get() == null || call.isCanceled()) return;
        if ((contextWeakReference.get()).isSuspended()) return;
        if (!updateProgress(true)) return;
        BaseActivity activity = contextWeakReference.get().getCurrentActivity();
        String title = activity.getString(R.string.error);
        //String msg = activity.getString(R.string.error_desc);
        //contextWeakReference.get().showAlertDialog(title, msg, finishOnFailure);
    }


    public void onFailure(int httpErrorCode, String msg) {
        BaseActivity activity = contextWeakReference.get().getCurrentActivity();
        String title = activity.getString(R.string.error);
        String errMsg = "Something went wrong please try again later";
        contextWeakReference.get().showAlertDialog(title, errMsg, finishOnFailure);
    }

    public abstract void onSuccess(T t);

    public abstract boolean updateProgress(boolean error);
}
