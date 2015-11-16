package com.mycompany.coupple;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;

import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONObject;


public class LoginFragment extends Fragment{

    private static final String NAME = "name";
    private static final String ID = "id";
    private static final String PICTURE = "picture";
    private static final String GENDER = "gender";
    private static final String EMAIL = "email";
    private static final String BIRTHDAY = "birthday";

    private static final String FIELDS = "fields";

    private static final String REQUEST_FIELDS =
            TextUtils.join(",", new String[]{ID, NAME, PICTURE, GENDER, EMAIL, BIRTHDAY});


    private JSONObject user;
    private TextView userNameView;
    private ProfilePictureView profilePictureView;


    private Profile pendingUpdateForUser;
    private ProfileTracker profileTracker;
    private AccessTokenTracker accessTokenTracker;
    private CallbackManager callbackManager;
    private LoginButton loginButton;
    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_login, container, false);
            loginButton = (LoginButton) view.findViewById(R.id.login_button);
            loginButton.setReadPermissions("user_friends", "email", "user_birthday");
            // If using in a fragment
            loginButton.setFragment(this);
            Log.i("MainActivity", "OnCreateView OK2");
            callbackManager = CallbackManager.Factory.create();
            // Callback registration
            Log.i("MainActivity","OnCreateView OK3");

            if(isLoggedIn()){
                Log.i("MainActivity","IS LOG IN");
                getUserInfo();
                Intent intent = new Intent(getContext(), DefineSOActivity.class);
                Log.i("MainActivity","Connexion OK");
                intent.putExtra("json", user.toString());
                startActivity(intent);
            }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Toast.makeText(getActivity(), "Login successful" + loginResult.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getContext(), DefineSOActivity.class);
                    Log.i("MainActivity","Connexion OK");
                    intent.putExtra("json", user.toString());
                    startActivity(intent);
                }

                @Override
                public void onCancel() {
                    Log.i("MainActivity","Connexion NONOK");
                }

                @Override
                public void onError(FacebookException exception) {
                    Log.i("MainActivity","Connexion ERROR");
                }
            });
            return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();
    }

    @Override
    public void onStart() {
        super.onStart();
        getUserInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        accessTokenTracker.stopTracking();
    }

    public boolean isLoggedIn(){
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken!=null;
    }


    private void getUserInfo(){
        Log.i("MainActivity","GetUserInfo");
        final AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null) {
            Log.i("MainActivity","AccessTokenNotNull");
            GraphRequest request = GraphRequest.newMeRequest(
                    accessToken, new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject me, GraphResponse response) {
                            user = me;
                        }
                    });
            Bundle parameters = new Bundle();
            parameters.putString(FIELDS, REQUEST_FIELDS);
            request.setParameters(parameters);
            GraphRequest.executeBatchAsync(request);
        } else {
            user = null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,
                resultCode, data);
    }

}
