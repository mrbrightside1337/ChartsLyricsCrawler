package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

	private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);

	private Properties properties;

	public ConfigReader() {
		logger.debug("Setting up config reader...");

		properties = new Properties();
		InputStream inputStream = this.getClass().getResourceAsStream("/configuration.properties");
		try {
			properties.load(inputStream);
		} catch (IOException e) {
			logger.error("Unable to read config properties.", e);
		}

		logger.debug("Config reader is ready!");
	}

	public Optional<String> getStringValue(String key) {
		return Optional.ofNullable(properties.getProperty(key));
	}

}
