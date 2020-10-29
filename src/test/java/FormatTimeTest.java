import org.junit.Test;
import service.FormatTime;
import service.TimeChecker;

import java.io.File;
import java.nio.file.Path;
import java.time.Instant;

import static org.junit.Assert.assertTrue;

public class FormatTimeTest {
    private final TimeChecker timeChecker = new TimeChecker();

    @Test
    public void PathToStringTime() throws Exception {

        final File file = File.createTempFile("createdFile", ".txt");
        final Path path = file.toPath();
        assertTrue(FormatTime.time(path.toString()).getClass() == String.class);
        assertTrue(FormatTime.time(path.toString()).matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}:\\d{2}"));

    }
}
