package bloomfilter;
import com.google.api.client.util.Charsets;
import com.google.common.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DictionnaryService {

    private static final Logger LOG = LoggerFactory.getLogger(DictionnaryService.class);
    private static final String DICO = "wordlist.txt";

    public List<String> getAll() {
        Path path = Paths.get(Resources.getResource(DICO).getPath());

        try (Stream<String> lines = Files.lines(path, Charsets.ISO_8859_1)) {
            return lines.collect(toList());
        } catch (IOException ignored) {
            LOG.warn("Cannot read commune file : {}", ignored);
        }

        return Collections.emptyList();
    }
}
