package com.example.uberv.accountmanagerdemo;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

public class UbervAccountAuthenticator extends AbstractAccountAuthenticator {
    public static final String LOG_TAG = UbervAccountAuthenticator.class.getSimpleName();

    private Context mContext;

    public UbervAccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        Log.d(LOG_TAG, "editProperties() for account type: " + accountType);
        return null;
    }

    // called when the user wants to log-in and add a new account to the device
    // need to return a Bundle with the Intent to start our _AccountAuthenticatorActivity_
    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d(LOG_TAG, "addAccount() for account type: " + accountType + ", authToketType=" + authTokenType + ", requiredFeatures: " + requiredFeatures);

        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(AuthenticatorActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        Log.d(LOG_TAG, "confirmCredentials() for account: " + account.type);
        return null;
    }

    // gets a stored auth-token for the account type from a previous successful log-in on this device
    // if there's no such thing - the user will be prompted to log-in
    // after a successful sign-in,the requesting app will get the long-awaited auth-token
    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d(LOG_TAG, "getAuthToken() for account: " + account.type + ", authTokenType=" + authTokenType);

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        // Lets give another try to authenticate the user
        if (TextUtils.isEmpty(authToken)) {
            final String password = am.getPassword(account);
            if (password != null) {
                // perform a new server request for an auth token
//                authToken = sServerAuthenticate.userSignIn(account.name, password, authTokenType);
                authToken = "stub_token";
            }
        }

        // if we got a new authToken or have stored one
        if (!TextUtils.isEmpty(authToken)) {
            // we get have a stored auth token - return it
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            // diagram: get auth token from KEY_AUTHTOKEN
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AuthenticatorActivity.ARG_ACCOUNT_TYPE, account.type);
        intent.putExtra(AuthenticatorActivity.ARG_AUTH_TYPE, authTokenType);
        final Bundle bundle = new Bundle();
        // diagram: Response includes KEY_INTENT? => AccountManager will launch authenticator intent
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;

    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        Log.d(LOG_TAG, "getAuthTokenLabel() for " + authTokenType);
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d(LOG_TAG, "getAuthTokenLabel() for account" + account.type + ", authTokenType=" + authTokenType);
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        Log.d(LOG_TAG, "hasFeatures() for account" + account.type + ", features=" + features);
        return null;
    }
}
