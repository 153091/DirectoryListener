package listener;

import service.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

public class Listener {
    public static void start() {
        try {
            WatchService service = FileSystems.getDefault().newWatchService();
            Map<WatchKey, Path> keyMap = new HashMap<>();
            Path path = Paths.get("TestDirectory");
            keyMap.put(path.register(service,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY),
                    path);

            WatchKey watchKey;
            do {
                watchKey = service.take();
                Path eventDir = keyMap.get(watchKey);

                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path eventPath = (Path) event.context();
                    LoggerService.LOGGER.info(eventDir + ": " + kind + ": " + eventPath);


                    if (kind.equals(StandardWatchEventKinds.ENTRY_CREATE)) {

                        String str = "TestDirectory/" + eventPath;
                        // process create event
                        FormatTime.time(str);


                        if (eventPath.toString().matches("^(.+)\\.(xml|json)$")) {
                            if (eventPath.toString().matches("^(.+)\\.(xml)$")) {
                                new Thread(new CountLinesXml(str)).start();
                            }
                            else new Thread(new CountLinesJson(str)).start();
                        }
                        else {
                            DeleteFile.delete(str);
                        }
                    }
                }
            } while (watchKey.reset());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
