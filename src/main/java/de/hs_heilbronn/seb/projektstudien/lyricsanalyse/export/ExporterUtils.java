package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExporterUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExporterUtils.class);

	public static void createDirectoryIfNotExisting(Path directory) {

		if (directory == null) {
			return;
		}

		if (Files.notExists(directory)) {
			try {
				logger.debug("Target directory doesn't exist and will be created.");
				Files.createDirectories(directory);
			} catch (IOException e) {
				logger.error("Error creating target directory.", e);
			}
		} else {
			logger.debug("Target directory does exist.");
		}
	}

}
