package com.example.uberv.accountmanagerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    /*
    A module to handle a specific account type. The AccountManager find the appropriate
    AccountAuthenticator talks with it to perform all the actions on the account type.
    The AccountAuthenticator knows which activity to show the user for entering his
    credentials and where to find any stored auth-token that the server has returned previously.
    This can be common to many different services under a single account type. For instance,
    Google’s authenticator on Android is authenticating Google Mail service (Gmail) along with
    other Google services such as Google Calendar and Google Drive.

    AccountAuthenticatorActivity - Base class for the “sign-in/create account” activity
    to be called by the authenticator when the user needs to identify himself.
    The activity is in charge of the sign-in or account creation process against
    the server and return an auth-token back to the calling authenticator.

    First time logging-in:
        •The app asks the AccountManager for an auth-token.
        •The AccountManager asks the relevant AccountAuthenticator if it has a token for us.
        •Since it has none (there’s no logged-in user), it show us a AccountAuthenticatorActivity that will allow the user to log-in.
        •The user logs-in and auth-token is returned from the server.
        •The auth-token is stored for future use in the AccountManager.
        •The app gets the auth-token it requested
        •Everyone’s happy!
        In case the user has already logged-in, we would get the auth-token back already on the second step.
        You can read more about authenticating using OAuth2 here.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
