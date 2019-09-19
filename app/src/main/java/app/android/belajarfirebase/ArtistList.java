package app.android.belajarfirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ArtistList extends ArrayAdapter<Artist> {
    private Activity context;
    private List<Artist> listArtist;

    public ArtistList(Activity context , List<Artist> listArtist){
        super(context,R.layout.list_data,listArtist);
        this.context = context;
        this.listArtist = listArtist;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewKelas = inflater.inflate(R.layout.list_data,null);

        TextView txtNamaArtist = (TextView) listViewKelas.findViewById(R.id.txtNamaArtist);
        TextView txtGenreArtist = (TextView) listViewKelas.findViewById(R.id.txtGenreArtist);

        Artist artist = listArtist.get(position);
        txtNamaArtist.setText(artist.getNamaArtist());
        txtGenreArtist.setText(artist.getGenreArtist());

        return  listViewKelas;
    }
}
