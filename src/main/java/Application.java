import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import crawler.ChartCrawler;
import crawler.LyricsCrawler;
import export.CSVExporter;
import export.Exporter;
import model.Song;

public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		logger.info("Application started...");

		List<Song> allSongs = ChartCrawler.crawl(2017, 2017);

		logger.info("Crawled {} songs", allSongs.size());

		allSongs.forEach(LyricsCrawler::populateLyrics);

		Path targetFile = Paths.get("target/target.csv");

		Exporter exporter = new CSVExporter();
		exporter.export(allSongs, targetFile);

		logger.info("Crawling finished.");

	}

}
