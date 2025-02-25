package listener;

import service.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Listener {
    private final ExecutorService executorService = Executors.newFixedThreadPool(16);

    public void start() {
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            Path path = Paths.get("TestDirectory");
            path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            WatchKey watchKey;
            do {
                watchKey = service.take();

                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();
                    LoggerService.LOGGER.info(path + ": " + kind + ": " + eventPath);


                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {

                        String str = "TestDirectory/" + eventPath;
                        // process create event
                        FormatTime.time(str);


                        if (eventPath.toString().matches("^(.+)\\.(xml|json)$")) {
                            if (eventPath.toString().matches("^(.+)\\.(xml)$")) {
                                executorService.execute(new CountLinesXml(str));
                            } else
                            executorService.execute(new CountLinesJson(str));
                        }
                        else {
                            executorService.execute(new DeleteFile(str));
                        }
                    }
                }
            } while (watchKey.reset());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
