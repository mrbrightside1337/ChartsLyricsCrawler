package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

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

import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.lyricsApiModel.LyricsApiResponse;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model.Song;
import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.util.ConfigReader;

public class LyricsCrawler {

	private static final Logger logger = LoggerFactory.getLogger(LyricsCrawler.class);

	private String apiKey;

	private static final String PROPERTY_KEY_API_KEY = "api.key.apiseeds.lyrics";

	public LyricsCrawler() {
		ConfigReader configReader = new ConfigReader();
		Optional<String> apiKeyOptinal = configReader.getStringValue(PROPERTY_KEY_API_KEY);
		if (apiKeyOptinal.isPresent()) {
			apiKey = apiKeyOptinal.get();
		} else {
			logger.error("Api key for lyrics crawler is not specified.");
			throw new IllegalStateException("Api key for lyrics crawler is not specified.");
		}

	}

	public void populateLyrics(Song song) {

		logger.debug("Getting lyrics for {} - {}", song.getArtist(), song.getTitle());

		try {

			String responseJSON = requestLyrics(song);
			setRequestedLyrics(song, responseJSON);

		} catch (UnsupportedEncodingException | CrawlingException e) {
			logger.warn("Lyrics for {} - {} haven't been set.", song.getArtist(), song.getTitle(), e);
		}

	}

	/**
	 * Builds the request URL for the lyrics api request. The most important thing
	 * that this method does is to encode the title and the artist of the song with
	 * UTF-8.
	 * 
	 * @param song
	 * @return RequestURL with encoded title and artist
	 * @throws UnsupportedEncodingException
	 */
	private String buildRequestURL(Song song) throws UnsupportedEncodingException {

		String filteredArtist = filterArtistString(song.getArtist());

		String encodedArtist = URLEncoder.encode(filteredArtist, StandardCharsets.UTF_8.displayName());
		String encodedTitle = URLEncoder.encode(song.getTitle(), StandardCharsets.UTF_8.displayName());

		return "https://orion.apiseeds.com/api/music/lyric/" + encodedArtist + "/" + encodedTitle + "?apikey=" + apiKey;
	}

	/**
	 * Filters the artist string. If there is an ampersand or the substring
	 * "Featuring" the following chars are cut out.
	 * 
	 * @param artist
	 * @return filtered artist
	 */
	private static String filterArtistString(String artist) {

		if (artist.contains("Featuring")) {
			return artist.substring(0, artist.indexOf("Featuring"));
		}
		if (artist.contains("&")) {
			return artist.substring(0, artist.indexOf('&'));
		}

		return artist;

	}

	/**
	 * 
	 * @param song
	 * @param responseJSON
	 */
	private static void setRequestedLyrics(Song song, String responseJSON) {
		Gson gson = new Gson();

		LyricsApiResponse lyricsApiResponse = null;

		try {
			lyricsApiResponse = gson.fromJson(responseJSON, LyricsApiResponse.class);
		} catch (Exception e) {
			logger.error("Exception: {}", e.getMessage(), e);
		}

		if (lyricsApiResponse != null && lyricsApiResponse.getResult() != null
				&& lyricsApiResponse.getResult().getTrack() != null
				&& lyricsApiResponse.getResult().getTrack().getText() != null) {
			song.setLyrics(lyricsApiResponse.getResult().getTrack().getText());
			logger.debug("Settings lyrics for {} - {}", song.getArtist(), song.getTitle());
		} else {
			logger.warn("Unable to set lyrics for {} - {}", song.getArtist(), song.getTitle());
		}

	}

	/**
	 * Requests the lyrics for the given song.
	 * 
	 * @param song
	 * @return String with the response as a JSON.
	 * @throws Exception
	 * @throws UnsupportedEncodingException if artist or title of the song contain
	 *                                      unsupported characters to be encoded
	 *                                      with UTF-8
	 * @throws CrawlingException
	 */
	private String requestLyrics(Song song) throws UnsupportedEncodingException, CrawlingException {

		String responseJSON = null;

		String requestURL = buildRequestURL(song);

		HttpGet httpGet = new HttpGet(requestURL);
		try (CloseableHttpClient httpclient = HttpClients.createDefault();
				CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {

			if (httpResponse.getStatusLine().getStatusCode() >= 300) {
				logger.warn("Status is {}", httpResponse.getStatusLine().getStatusCode());
				throw new CrawlingException("Request was unsuccessful.");
			}

			HttpEntity httpEntity = httpResponse.getEntity();

			InputStream contentInputStream = httpEntity.getContent();

			StringBuilder sb = new StringBuilder();

			BufferedReader br = new BufferedReader(new InputStreamReader(contentInputStream));

			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}

			EntityUtils.consume(httpEntity);

			// Remove line breaks from lyric text
			responseJSON = sb.toString().replace("\n", " ");

			return responseJSON;

		} catch (ClientProtocolException e) {
			logger.error("ClientProtocolException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		}

		return responseJSON;
	}

}
