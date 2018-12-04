import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import crawler.ChartCrawler;
import crawler.LyricsCrawler;
import model.Song;

public class Application {

	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {

		List<Song> allSongs = ChartCrawler.crawl(2017, 2017);

		allSongs.forEach(LyricsCrawler::populateLyrics);

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		Gson gson = gsonBuilder.create();

		String finalJson = gson.toJson(allSongs);

		try (FileWriter fw = new FileWriter("target.json")) {
			fw.write(finalJson);
			fw.flush();
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		logger.info("Crawled {} songs", allSongs.size());
	}

}
