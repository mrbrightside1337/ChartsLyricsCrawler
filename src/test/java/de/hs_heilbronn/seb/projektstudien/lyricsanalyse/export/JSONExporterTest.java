package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model.Song;

class JSONExporterTest {

	private static final String FILE = "work/json-test-file.json";

	@BeforeEach
	void prepare() throws IOException {
		Files.deleteIfExists(Paths.get(FILE));
	}

	@Test
	void testExport() throws IOException {

		List<Song> allSongs = new ArrayList<>();
		Song song = new Song("title 1", "artist 1", 2001, "lyrics 1");
		allSongs.add(song);
		song = new Song("title 2", "artist 2", 2002, "lyrics 2");
		allSongs.add(song);

		Exporter csvExporter = new JSONExporter();
		Path exportPath = Paths.get(FILE);
		csvExporter.export(allSongs, exportPath);

		FileChannel imageFileChannel = FileChannel.open(exportPath);

		assertTrue(imageFileChannel.size() > 0);

		imageFileChannel.close();

		List<String> lines = Files.readAllLines(exportPath);

		assertEquals(1, lines.size());

		assertEquals(145, lines.get(0).length());

	}

}
