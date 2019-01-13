package de.hs_heilbronn.seb.projektstudien.lyricsanalyse;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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

		int fromYear = 2018;
		int toYear = 2018;

		if (args.length > 0) {
			if (args.length == 2) {
				try {
					fromYear = Integer.parseInt(args[0]);
					toYear = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					logger.error("Arguments cannot be parsed to integer.");
					throw new IllegalArgumentException("Invalid arguments. Please try again.");
				}

				if (toYear < fromYear) {
					logger.error("Year to start crawling has to be less than year to end crawling.");
					throw new IllegalArgumentException("Invalid arguments. Please try again.");
				}
			}
			if (args.length == 1) {
				fromYear = Integer.parseInt(args[0]);
				toYear = fromYear;
			}
		}

		LyricsCrawler lyricsCrawler = new LyricsCrawler();

		logger.info("Start crawling from year {} to year {}", fromYear, toYear);

		List<Song> allSongs = ChartCrawler.crawl(fromYear, toYear);

		logger.info("Crawled {} songs", allSongs.size());

		allSongs.forEach(lyricsCrawler::populateLyrics);

		Path targetFile = Paths.get("target/target.csv");

		Exporter exporter = new CSVExporter();
		exporter.export(allSongs, targetFile);

		logger.info("Crawling finished.");

	}

}
