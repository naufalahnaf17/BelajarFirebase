package app.android.belajarfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String ID_ARTIST = "idartist";
    public static final String NAMA_ARTIST = "namaartist";
    public static final String GENRE_ARTIST = "genreartist";

    private DatabaseReference db;
    ListView listViewArtist;
    List<Artist> listArtist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseDatabase.getInstance().getReference("artist");
        listViewArtist = (ListView)findViewById(R.id.listArtist);
        final EditText eNamaArtist = (EditText) findViewById(R.id.eNamaArtist);
        final Spinner spinner = (Spinner) findViewById(R.id.spinnerGenre);
        Button btnAddData = (Button) findViewById(R.id.btnAddData);

        listArtist = new ArrayList<>();

        btnAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String namaArtist = eNamaArtist.getText().toString().trim();
                String genreArtist = spinner.getSelectedItem().toString().trim();

                String idArtist = db.push().getKey();
                Artist artist = new Artist(idArtist , namaArtist , genreArtist);
                db.child(idArtist).setValue(artist);

                Toast.makeText(MainActivity.this, "Tambah data berhasil", Toast.LENGTH_SHORT).show();


            }
        });

        listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = listArtist.get(i);
                Intent intent = new Intent(getApplicationContext(),SongActivity.class);
                intent.putExtra(ID_ARTIST,artist.getIdArtist());
                intent.putExtra(NAMA_ARTIST,artist.getNamaArtist());
                intent.putExtra(GENRE_ARTIST,artist.getGenreArtist());

                startActivity(intent);
            }
        });

        listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = listArtist.get(i);

                showDialogWhatToDo(artist.getIdArtist());

                return false;
            }
        });

    }

    private void showDialogWhatToDo(final String idArtist) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.update_or_delete,null);
        dialogBuilder.setView(dialogView);

        Button btnDoUpdate = (Button) dialogView.findViewById(R.id.doUpdate);
        Button btnDoDelete = (Button) dialogView.findViewById(R.id.doDelete);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnDoUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogUpdateArtist(idArtist);
                alertDialog.dismiss();
            }
        });

        btnDoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteArtist(idArtist);
                alertDialog.dismiss();
            }
        });
    }

    private void showDialogUpdateArtist(final String idArtist) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.edit_artist_layout,null);
        dialogBuilder.setView(dialogView);

        final EditText updateNama = (EditText)dialogView.findViewById(R.id.updateNama);
        final Spinner updateGenre = (Spinner) dialogView.findViewById(R.id.updateGenre);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.btnUpdate);

        dialogBuilder.setTitle("Update Artist ");
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namaArtist = updateNama.getText().toString();
                String namaGenre = updateGenre.getSelectedItem().toString();

                updateArtist(idArtist , namaArtist , namaGenre);
                alertDialog.dismiss();
            }
        });
    }

    private boolean updateArtist(String idArtist , String namaArtist , String namaGenre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("artist").child(idArtist);
        Artist artist = new Artist(idArtist , namaArtist , namaGenre);
        databaseReference.setValue(artist);

        Toast.makeText(this, "Behasil Update Data", Toast.LENGTH_SHORT).show();

        return true;
    }

    private void deleteArtist(String idArtist){
        DatabaseReference dArtist = FirebaseDatabase.getInstance().getReference("artist").child(idArtist);
        DatabaseReference dSong = FirebaseDatabase.getInstance().getReference("Song").child(idArtist);
        dArtist.removeValue();
        dSong.removeValue();

        Toast.makeText(this, "Berhasil Hapus Data", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listArtist.clear();

                for (DataSnapshot kelasSnapShot : dataSnapshot.getChildren()){
                    Artist artist = kelasSnapShot.getValue(Artist.class);
                    listArtist.add(artist);
                }

                ArtistList Adapter = new ArtistList(MainActivity.this,listArtist);
                listViewArtist.setAdapter(Adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
