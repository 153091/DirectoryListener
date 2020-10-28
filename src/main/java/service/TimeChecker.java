package service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

public class TimeChecker {
    public static Instant resolveCreationTimeWithBasicAttributes(Path path) {
        try {
            final BasicFileAttributes attr = Files.readAttributes(path, BasicFileAttributes.class);
            final FileTime fileTime = attr.creationTime();

            Instant localDateTime = fileTime
                    .toInstant();

            return localDateTime;
        } catch (IOException ex) {
            throw new RuntimeException("An issue occured went wrong when resolving creation time", ex);
        }
    }
}
