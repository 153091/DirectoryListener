import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.DeleteFile;

import java.io.File;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;


public class WatchDirectoryTest {
    private WatchService watchService;
    private WatchKey basePathWatchKey;
    Path basePath = Paths.get("src/test/testdir");
    String path = "src/test/testdir/test.txt";

    @Before
    public void setUp() throws Exception {
        watchService = FileSystems.getDefault().newWatchService();
        basePathWatchKey = basePath.register(watchService, ENTRY_CREATE);
    }

    @Test
    public void testEventForDirectory() throws Exception {

        // Use relative path for Unix systems
        File f = new File(path);

        f.getParentFile().mkdirs();
        f.createNewFile();
        WatchKey watchKey = watchService.poll(20, TimeUnit.SECONDS);

        assertThat(watchKey,is(basePathWatchKey));
        List<WatchEvent<?>> eventList = watchKey.pollEvents();
        assertThat(eventList.size(), is(1));
        for (WatchEvent event : eventList) {
            assertThat(event.kind() == StandardWatchEventKinds.ENTRY_CREATE, is(true));
            assertThat(event.count(),is(1));
        }
        Path eventPath = (Path) eventList.get(0).context();
        assertThat(Files.isSameFile(eventPath, Paths.get("test.txt")), is(true));
        Path watchedPath = (Path) watchKey.watchable();
        assertThat(Files.isSameFile(watchedPath, basePath), is(true));
    }

    @After
    public void deleteTestFile(){
        DeleteFile delete = new DeleteFile(path);
        delete.run();
    }
}