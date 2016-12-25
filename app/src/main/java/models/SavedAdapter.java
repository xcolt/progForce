package models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.progforce.andriykotsyuba.progforce.R;

import java.io.InputStream;

public class SavedAdapter extends ArrayAdapter<String>

{
    private final Context context;
    private final String[] values;

    public SavedAdapter(Context context, String[] values) {
        super(context, R.layout.usercell, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String repo_param[] = values[position].split("\\|");

        View rowView = inflater.inflate(R.layout.usercell, parent, false);

        new DownloadImageTask((ImageView) rowView.findViewById(R.id.imageView)).execute(repo_param[8]);

        TextView textName = (TextView) rowView.findViewById(R.id.textName);
        textName.setText(repo_param[0] + ", " + repo_param[2] + ", " + repo_param[3]);

        TextView textFollower = (TextView) rowView.findViewById(R.id.textFollowers);
        textFollower.setText("Followers " + repo_param[4] + "");

        TextView textFollowing = (TextView) rowView.findViewById(R.id.textFollowing);
        textFollowing.setText("Following " + repo_param[5] + "");

        TextView textGists = (TextView) rowView.findViewById(R.id.textGists);
        textGists.setText("Public Gists " + repo_param[6] + "");

        TextView textRepos = (TextView) rowView.findViewById(R.id.textRepos);
        textRepos.setText("Public Repos " + repo_param[7] + "");

        return rowView;
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