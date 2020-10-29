import org.junit.Test;
import service.CountLinesJson;
import service.DeleteFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FileHandlerTest {
    String pathJson = "src/test/testdir/testJson.json";
    String pathXml = "src/test/testdir/testXml.xml";
    private WatchService watchService;
    Path basePath = Paths.get("src/test/testdir");
    String path = "src/test/testdir/testDelete.txt";

    @Test
    public void countLinesJsonTest() {
        CountLinesJson counter = new CountLinesJson(pathJson);
        counter.run();
        assertEquals(counter.getLineNumber(), 11);
    }

    @Test
    public void countLinesXmlTest() {
        CountLinesJson counter = new CountLinesJson(pathXml);
        counter.run();
        assertEquals(counter.getLineNumber(), 8);
    }

    @Test
    public void deleteFileTest() throws Exception {

        // Use path for create file
        File f = new File(path);

//        f.getParentFile().mkdirs();
        f.createNewFile();

        watchService = FileSystems.getDefault().newWatchService();
        WatchKey basePathWatchKey = basePath.register(watchService, ENTRY_DELETE);

        DeleteFile delete = new DeleteFile(path);
        delete.run();

        WatchKey watchKey = watchService.poll(20, TimeUnit.SECONDS);

        List<WatchEvent<?>> eventList = watchKey.pollEvents();

        assertThat(eventList.size(), is(1));
        for (WatchEvent event : eventList) {
            assertThat(event.kind() == StandardWatchEventKinds.ENTRY_DELETE, is(true));
            assertThat(event.count(),is(1));
        }
        Path eventPath = (Path) eventList.get(0).context();
        assertThat(Files.isSameFile(eventPath, Paths.get("testDelete.txt")), is(true));
        Path watchedPath = (Path) watchKey.watchable();
        assertThat(Files.isSameFile(watchedPath, basePath), is(true));
    }
}
