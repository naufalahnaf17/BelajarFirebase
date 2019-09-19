package app.android.belajarfirebase;

public class Song {
    String idSong;
    String judulSong;
    int ratingSong;

    public Song(String idSong, String judulSong, int ratingSong) {
        this.idSong = idSong;
        this.judulSong = judulSong;
        this.ratingSong = ratingSong;
    }

    public Song(){}

    public String getIdSong() {
        return idSong;
    }

    public String getJudulSong() {
        return judulSong;
    }

    public int getRatingSong() {
        return ratingSong;
    }
}
