package app.android.belajarfirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class SongList extends ArrayAdapter<Song> {
    private Activity context;
    private List<Song> listSong;

    public SongList(Activity context , List<Song> listSong){
        super(context,R.layout.list_song,listSong);
        this.context = context;
        this.listSong = listSong;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewMurid = inflater.inflate(R.layout.list_song,null);

        TextView txtJudul = (TextView) listViewMurid.findViewById(R.id.txtJudul);
        TextView txtRating = (TextView) listViewMurid.findViewById(R.id.txtRating);

        Song song = listSong.get(position);
        txtJudul.setText(song.getJudulSong());
        txtRating.setText(String.valueOf(song.getRatingSong()));

        return listViewMurid;
    }
}
