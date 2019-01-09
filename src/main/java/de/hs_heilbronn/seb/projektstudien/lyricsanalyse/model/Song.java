package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model;

public class Song {

	private String title;
	private String artist;
	private int year;
	private String lyrics;

	public Song() {
	}

	public Song(String title, String artist, int year, String lyrics) {
		this.title = title;
		this.artist = artist;
		this.year = year;
		this.lyrics = lyrics;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getLyrics() {
		return lyrics;
	}

	public void setLyrics(String lyrics) {
		this.lyrics = lyrics;
	}

	@Override
	public String toString() {
		return "\nSong [title=" + title + ", artist=" + artist + ", year=" + year + ", lyrics=" + lyrics + "]";
	}

}
