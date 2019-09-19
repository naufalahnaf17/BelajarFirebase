package app.android.belajarfirebase;

public class Artist {

    String idArtist;
    String namaArtist;
    String genreArtist;

    public Artist(String idArtist, String namaArtist, String genreArtist) {
        this.idArtist = idArtist;
        this.namaArtist = namaArtist;
        this.genreArtist = genreArtist;
    }

    public Artist(){}

    public String getIdArtist() {
        return idArtist;
    }

    public String getNamaArtist() {
        return namaArtist;
    }

    public String getGenreArtist() {
        return genreArtist;
    }
}
