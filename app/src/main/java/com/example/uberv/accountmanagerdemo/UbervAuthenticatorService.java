package com.example.uberv.accountmanagerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Authentication service that makes our {@link UbervAccountAuthenticator} available to other apps
 * through returning it from the onBin() method
 */
public class UbervAuthenticatorService extends Service {
    public static final String LOG_TAG = UbervAuthenticatorService.class.getSimpleName();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(LOG_TAG, "onBind(), returning authenticator binder");
        UbervAccountAuthenticator authenticator = new UbervAccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
