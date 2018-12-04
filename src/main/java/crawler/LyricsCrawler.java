package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import lyricsApiModel.LyricsApiResponse;
import model.Song;

public class LyricsCrawler {

	private static final Logger logger = LoggerFactory.getLogger(LyricsCrawler.class);

	private LyricsCrawler() {
	}

	public static void populateLyrics(Song song) {

		logger.debug("Getting lyrics for {} - {}", song.getArtist(), song.getTitle());

		Gson gson = new Gson();

		String encodedArtist = null;
		String encodedTitle = null;
		try {
			encodedArtist = URLEncoder.encode(song.getArtist(), StandardCharsets.UTF_8.displayName());
			encodedTitle = URLEncoder.encode(song.getTitle(), StandardCharsets.UTF_8.displayName());
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
			return;
		}

		String sURL = "https://orion.apiseeds.com/api/music/lyric/" + encodedArtist + "/" + encodedTitle
				+ "?apikey=6CLzMHmRi8DbrpqGwuju8IJ1wkGZRbxrLgufXrIvR3CTBBtABqz90XddqwKTFyxH";

		InputStream contentInputStream = null;

		if (true) {
			HttpGet httpGet = new HttpGet(sURL);
			try (CloseableHttpClient httpclient = HttpClients.createDefault();
					CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
				HttpEntity httpEntity = httpResponse.getEntity();

				contentInputStream = httpEntity.getContent();

				StringBuilder sb = new StringBuilder();

				BufferedReader br = new BufferedReader(new InputStreamReader(contentInputStream));

				String line = br.readLine();
				while (line != null) {
					sb.append(line);
					line = br.readLine();
				}

				EntityUtils.consume(httpEntity);

				LyricsApiResponse jsonResponse = gson.fromJson(sb.toString(), LyricsApiResponse.class);

				if (jsonResponse != null && jsonResponse.getResult() != null
						&& jsonResponse.getResult().getTrack() != null
						&& jsonResponse.getResult().getTrack().getText() != null) {
					song.setLyrics(jsonResponse.getResult().getTrack().getText());
					logger.debug("Settings lyrics for {} - {}", song.getArtist(), song.getTitle());
				} else {
					logger.warn("Unable to set lyrics for {} - {}", song.getArtist(), song.getTitle());
				}

			} catch (ClientProtocolException e) {
				logger.error("ClientProtocolException", e);
			} catch (IOException e) {
				logger.error("IOException", e);
			}
		}

	}

}
