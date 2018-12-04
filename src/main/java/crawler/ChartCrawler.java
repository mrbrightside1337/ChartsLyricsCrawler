package crawler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Song;

public class ChartCrawler {

	private static final Logger logger = LoggerFactory.getLogger(ChartCrawler.class);

	private ChartCrawler() {
	}

	public static List<Song> crawl(int fromYear, int toYear) {

		List<Song> allSongs = new ArrayList<>();

		int currentYear = fromYear;
		int finalYear = toYear;

		while (currentYear <= finalYear) {

			logger.debug("Crawling year: {}", currentYear);

			Document doc = null;
			try {
				doc = Jsoup.connect("https://www.billboard.com/charts/year-end/" + currentYear + "/hot-100-songs")
						.get();
			} catch (IOException e) {
				logger.error("Error connecting to the billboard page.", e);
			}

			Song currentSong = null;

			if (doc != null) {
				Elements infoDivs = doc.select("div.ye-chart-item__text");
				for (Element infoDiv : infoDivs) {
					currentSong = new Song();
					currentSong.setTitle(infoDiv.select("div.ye-chart-item__title").text());
					currentSong.setArtist(infoDiv.select("div.ye-chart-item__artist").text());
					currentSong.setYear(currentYear);
					allSongs.add(currentSong);
					logger.debug("Adding {}", currentSong);
				}
			}

			currentYear++;
		}

		return allSongs;
	}

}
