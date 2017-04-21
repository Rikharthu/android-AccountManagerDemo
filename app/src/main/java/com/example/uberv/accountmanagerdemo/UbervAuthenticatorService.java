package com.example.uberv.accountmanagerdemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import static android.accounts.AccountManager.ACTION_AUTHENTICATOR_INTENT;

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

        // we shall return authenticator's binder when intent action is ACTION_AUTHENTICATOR_INTENT
        if (intent.getAction().equals(ACTION_AUTHENTICATOR_INTENT)) {
            UbervAccountAuthenticator authenticator = new UbervAccountAuthenticator(this);
            return authenticator.getIBinder();
        }

        return null;
    }
}
