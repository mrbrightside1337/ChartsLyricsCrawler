package export;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import model.Song;

class CSVExporterTest {

	private static final String FILE = "work/csv-test-file.csv";

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

		Exporter csvExporter = new CSVExporter();
		Path exportPath = Paths.get(FILE);

		csvExporter.export(allSongs, exportPath);
		List<String> lines = null;

		lines = Files.readAllLines(Paths.get(FILE));

		assertEquals(3, lines.size());
		assertEquals("\"title\";\"artist\";\"year\";\"lyrics\"", lines.get(0));
		assertEquals("\"title 1\";\"artist 1\";\"2001\";\"lyrics 1\"", lines.get(1));
		assertEquals("\"title 2\";\"artist 2\";\"2002\";\"lyrics 2\"", lines.get(2));

	}

}
