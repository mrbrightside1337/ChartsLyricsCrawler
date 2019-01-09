package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.lyricsApiModel;

public class Result {

	private Artist artist;
	private Track track;
	private Copyright copyright;

	private double probability;
	private double similarity;

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public Track getTrack() {
		return track;
	}

	public void setTrack(Track track) {
		this.track = track;
	}

	public Copyright getCopyright() {
		return copyright;
	}

	public void setCopyright(Copyright copyright) {
		this.copyright = copyright;
	}

	public double getProbability() {
		return probability;
	}

	public void setProbability(int probability) {
		this.probability = probability;
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(int similarity) {
		this.similarity = similarity;
	}

}
