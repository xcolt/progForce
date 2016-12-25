package models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.progforce.andriykotsyuba.progforce.R;

public class RepoListAdapter extends ArrayAdapter<String>

{
    private final Context context;
    private final String[] values;

    public RepoListAdapter(Context context, String[] values) {
        super(context, R.layout.gitcell, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String repo_param[] = values[position].split("\\|");
        View rowView = inflater.inflate(R.layout.gitcell, parent, false);
        TextView textTitle = (TextView) rowView.findViewById(R.id.gittitle);
        textTitle.setText(repo_param[0]);

        TextView textLanguage = (TextView) rowView.findViewById(R.id.gitlanguage);
        textLanguage.setText(repo_param[1]);

        TextView textLink = (TextView) rowView.findViewById(R.id.gitlink);
        textLink.setText(repo_param[2]);

        TextView textStar = (TextView) rowView.findViewById(R.id.gitstar);
        textStar.setText(repo_param[2]);

        return rowView;
    }
}