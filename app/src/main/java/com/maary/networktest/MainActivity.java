package com.maary.networktest;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    TextView darkThemeStateView;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipeRefreshLayout = findViewById(R.id.swipe);

        textView = findViewById(R.id.text);
        darkThemeStateView = findViewById(R.id.theme_state);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!swipeRefreshLayout.isRefreshing()){
                    swipeRefreshLayout.setRefreshing(true);
                }
                textView.setText(checkNetworkState());
                textView.setText(checkDarkThemeState());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        textView.setText(checkNetworkState());
        darkThemeStateView.setText(checkDarkThemeState());

    }

    private String checkNetworkState(){
        String string;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        Network network = connectivityManager.getActiveNetwork();
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
        if (networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_NOT_METERED)){
            string = "NOT METERED";
        }else {
            string = "METERED";
        }
        return string;
    }

    private String checkDarkThemeState(){
        String darkModeState = "No Result";
        Configuration configuration = getResources().getConfiguration();
        int currentNightMode = configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                darkModeState = "Dark Mode OFF";
                break;
            case Configuration.UI_MODE_NIGHT_YES:
                darkModeState = "Dark Mode ON";
                break;
        }
        return darkModeState;
    }
}
