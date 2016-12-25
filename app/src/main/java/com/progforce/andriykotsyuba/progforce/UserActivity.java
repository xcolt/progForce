package com.progforce.andriykotsyuba.progforce;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

import models.RepoListAdapter;
import models.ReposAdapter;
import rest.ReposClient;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;

public class UserActivity extends AppCompatActivity {
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.dispatchMenuVisibilityChanged(true);

        user = getIntent().getExtras().getString("user");
        String log_param[] = getIntent().getExtras().getString("log_param").split("\\|");
        ReposClient gitHubService = ReposClient.retrofit.create(ReposClient.class);

        new DownloadImageTask((ImageView) findViewById(R.id.imageView)).execute(log_param[8]);

        actionBar.setTitle(log_param[1]);

        TextView textName = (TextView) findViewById(R.id.textName);
        textName.setText(log_param[0] + ", " + log_param[2] + ", " + log_param[3]);

        TextView textFollower = (TextView) findViewById(R.id.textFollowers);
        textFollower.setText("Followers " + log_param[4] + "");

        TextView textFollowing = (TextView) findViewById(R.id.textFollowing);
        textFollowing.setText("Following " + log_param[5] + "");

        TextView textGists = (TextView) findViewById(R.id.textGists);
        textGists.setText("Public Gists " + log_param[6] + "");

        TextView textRepos = (TextView) findViewById(R.id.textRepos);
        textRepos.setText("Public Repos " + log_param[7] + "");

        final Call<List<ReposAdapter>> call = gitHubService.repoContributors(user);

        final ProgressDialog dialog = ProgressDialog.show(this, "", "loading...");

        final ListView listView = (ListView) findViewById(R.id.listView);

        call.enqueue(new Callback<List<ReposAdapter>>() {

            @Override
            public void onResponse(Response<List<ReposAdapter>> response) {
                dialog.dismiss();

                String textBody = response.body().toString();
                textBody = textBody.replace("[","").replace("]","");
                String repo_list[] = textBody.split(",");

                listView.setAdapter(new RepoListAdapter(UserActivity.this, repo_list));
            }

            @Override
            public void onFailure(Throwable t) {
                dialog.dismiss();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sp = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        switch (item.getItemId()) {
            case R.id.browser:
                final Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse("https://github.com/" + user));
                this.startActivity(intent);
                return true;
            case R.id.save:
                SharedPreferences.Editor editor = sp.edit();
                editor.putString(user, getIntent().getExtras().getString("log_param"));
                editor.commit();
                return true;
            case R.id.share:
                Intent intentNext = new Intent(UserActivity.this, SavedActivity.class);
                startActivity(intentNext);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
