package service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FormatTime {
    public static String time(String str) {
        // process create event
        Path p1 = Paths.get(str);
        String time = TimeChecker.resolveCreationTimeWithBasicAttributes(p1)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss"));
        LoggerService.LOGGER.info("Create time " + time);

        return time;
    }
}
