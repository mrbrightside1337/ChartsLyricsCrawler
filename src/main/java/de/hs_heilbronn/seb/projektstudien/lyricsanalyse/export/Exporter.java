package de.hs_heilbronn.seb.projektstudien.lyricsanalyse.export;

import java.nio.file.Path;
import java.util.List;

import de.hs_heilbronn.seb.projektstudien.lyricsanalyse.model.Song;

public interface Exporter {

	void export(List<Song> allSongs, Path path);

}
