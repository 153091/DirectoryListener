import org.junit.Test;
import service.TimeChecker;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;


import static org.junit.Assert.assertTrue;

public class TimeCheckerTest {

    private final TimeChecker timeChecker = new TimeChecker();

    @Test
    public void instatntDateIsAfterThenReturnDate() throws Exception {

        final File file = File.createTempFile("createdFile", ".txt");
        final Path path = file.toPath();

        final Instant response = timeChecker.resolveCreationTimeWithBasicAttributes(path);

        assertTrue(Instant
                .now()
                .isAfter(response));

    }
}