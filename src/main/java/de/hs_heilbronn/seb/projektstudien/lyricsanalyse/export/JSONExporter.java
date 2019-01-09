package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model.Song;

public class JSONExporter implements Exporter {

	private static final Logger logger = LoggerFactory.getLogger(JSONExporter.class);

	@Override
	public void export(List<Song> allSongs, Path path) {

		ExporterUtils.createDirectoryIfNotExisting(path.getParent());

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.disableHtmlEscaping();
		Gson gson = gsonBuilder.create();

		String finalJson = gson.toJson(allSongs);

		try (FileWriter fw = new FileWriter(path.toFile())) {
			fw.write(finalJson);
			fw.flush();
		} catch (IOException e) {
			logger.error("IOException", e);
		}

	}

}
