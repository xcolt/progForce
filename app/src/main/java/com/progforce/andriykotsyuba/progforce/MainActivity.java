package com.progforce.andriykotsyuba.progforce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import models.GitResult;
import models.Item;
import models.TargetAdapter;
import models.UserAdapter;
import rest.UserClient;
import rest.RestClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class MainActivity extends AppCompatActivity {
    private UserAdapter adapter ;
    List<Item> Users ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //insert icon to ActionBar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.icon);

        //initial ListView with users variant
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position, long id) {

                final String log_name = (String) ((TextView) itemClicked).getText();
                final Intent intent = new Intent(MainActivity.this, UserActivity.class);
                UserClient gitUserHubService = UserClient.retrofit.create(UserClient.class);

                final Call<TargetAdapter> callUser = gitUserHubService.userContributors(log_name);
                callUser.enqueue(new Callback<TargetAdapter>() {

                    @Override
                    public void onResponse(Response<TargetAdapter> response) {
                        String log_param = response.body().toString();
                        intent.putExtra("log_param", log_param);
                        intent.putExtra("user", log_name);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Throwable t) {

                    }
                });
            }
        });


        Users = new ArrayList<Item>();

        // initial search button
        Button buttonOK = (Button) findViewById(R.id.button) ;
        buttonOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);

                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);

                //insert progress item
                final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "loading...");
                final RestClient.GitApiInterface service = RestClient.getClient();

                final EditText editText = (EditText) findViewById(R.id.editText);
                Call<GitResult> call = service.getUsersNamed(editText.getText().toString());
                call.enqueue(new Callback<GitResult>() {
                    @Override
                    public void onResponse(Response<GitResult> response) {
                        dialog.dismiss();
                        if (response.isSuccess()) {
                            GitResult result = response.body();
                            Users = result.getItems();
                            adapter = new UserAdapter(MainActivity.this, Users);
                            listView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }
}
