package export;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import model.Song;

public class CSVExporter implements Exporter {

	private static final Logger logger = LoggerFactory.getLogger(CSVExporter.class);

	@Override
	public void export(List<Song> songs, Path path) {

		ExporterUtils.createDirectoryIfNotExisting(path.getParent());

		try (BufferedWriter writer = Files.newBufferedWriter(path)) {

			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
					.withHeader("title", "artist", "year", "lyrics").withQuoteMode(QuoteMode.ALL).withDelimiter(';'));

			songs.forEach(s -> {
				try {
					csvPrinter.printRecord(s.getTitle(), s.getArtist(), s.getYear(), s.getLyrics());
				} catch (IOException e) {
					logger.error("IOException writing target files as CSV", e);
				}
			});

			csvPrinter.flush();
			csvPrinter.close();

		} catch (IOException e) {
			logger.error("IOException writing target files as CSV", e);
		}

	}

}
