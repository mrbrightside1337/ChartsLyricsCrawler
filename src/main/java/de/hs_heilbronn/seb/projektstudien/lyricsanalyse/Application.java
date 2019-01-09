package de.hs_heilbronn.seb.projektstudien.lyricsanalyse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.crawler.ChartCrawler;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.crawler.LyricsCrawler;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export.CSVExporter;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export.Exporter;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model.Song;

public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		logger.info("Application started...");

		LyricsCrawler lyricsCrawler = new LyricsCrawler();

		List<Song> allSongs = ChartCrawler.crawl(2017, 2017);

		logger.info("Crawled {} songs", allSongs.size());

		allSongs.forEach(lyricsCrawler::populateLyrics);

		Path targetFile = Paths.get("target/target.csv");

		Exporter exporter = new CSVExporter();
		exporter.export(allSongs, targetFile);

		logger.info("Crawling finished.");

	}

}
