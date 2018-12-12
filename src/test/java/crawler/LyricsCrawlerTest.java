package crawler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Song;

class LyricsCrawlerTest {

	@Test
	void testPopulateLyrics() {

		Song song = new Song("Something just like this", "The Chainsmokers", 1234, "");

		LyricsCrawler.populateLyrics(song);

		assertTrue(song.getLyrics().length() > 0);
		assertTrue(song.getLyrics().startsWith("I've been reading books of old"));

	}

	@Test
	void lyricsNotAvailable() {

		Song song = new Song("NoRealSong", "NoRealArtist", 1234, "");

		LyricsCrawler.populateLyrics(song);

		assertEquals(0, song.getLyrics().length());

	}

	@Test
	void featuringFilteringWorksCorrectly() {

		Song song = new Song("Something just like this", "The Chainsmokers Featuring Coldplay", 1234, "");

		LyricsCrawler.populateLyrics(song);

		assertTrue(song.getLyrics().length() > 0);
		assertTrue(song.getLyrics().startsWith("I've been reading books of old"));

	}

	@Test
	void ampersandFilteringWorksCorrectly() {

		Song song = new Song("Something just like this", "The Chainsmokers & Coldplay", 1234, "");

		LyricsCrawler.populateLyrics(song);

		assertTrue(song.getLyrics().length() > 0);
		assertTrue(song.getLyrics().startsWith("I've been reading books of old"));

	}

}