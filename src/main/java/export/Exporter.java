package export;

import java.nio.file.Path;
import java.util.List;

import model.Song;

public interface Exporter {

	void export(List<Song> allSongs, Path path);

}
