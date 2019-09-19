package app.android.belajarfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SongActivity extends AppCompatActivity {
    private DatabaseReference db;
    EditText inputJudul;
    SeekBar inputRating;
    List<Song> listLagu;
    ListView listViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        inputJudul = (EditText)findViewById(R.id.namaLagu);
        inputRating = (SeekBar)findViewById(R.id.rating);
        listViewSong = (ListView)findViewById(R.id.listViewLagu);

        listLagu = new ArrayList<>();

        Intent intent = getIntent();
        String idArtist = intent.getStringExtra(MainActivity.ID_ARTIST);
        String namaArtist = intent.getStringExtra(MainActivity.NAMA_ARTIST);

        TextView txtNamaArtist = (TextView)findViewById(R.id.txtNama);
        txtNamaArtist.setText(namaArtist);

        db = FirebaseDatabase.getInstance().getReference("Song").child(idArtist);

        Button btnTambahLagu = (Button)findViewById(R.id.btnTambahLagu);
        btnTambahLagu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul = inputJudul.getText().toString();
                int rating = inputRating.getProgress();

                String idLagu = db.push().getKey();
                Song song = new Song(idLagu,judul,rating);
                db.child(idLagu).setValue(song);

                Toast.makeText(SongActivity.this, "Berhasil", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        db.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listLagu.clear();

                for (DataSnapshot muridSnapShot : dataSnapshot.getChildren()){
                    Song song = muridSnapShot.getValue(Song.class);
                    listLagu.add(song);
                }

                SongList adapter = new SongList(SongActivity.this,listLagu);
                listViewSong.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SongActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
