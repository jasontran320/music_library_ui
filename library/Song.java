package library;

public class Song {
    String title, artist, category, notes;
    boolean share_privelages[];
    double run_time;

    public Song(String title, String artist, String category,
                String notes, boolean [] share, double time) {
        this.title = title; this.artist = artist;
        this. category = category; this.notes = notes;
        share_privelages = share;
        run_time = time;
    }

    public String getTitle() {
        return title;
    }

    public String toString() {
        return title;
    }
}
