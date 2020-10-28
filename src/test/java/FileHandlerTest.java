import org.junit.Test;
import service.CountLinesJson;
import static org.junit.Assert.*;

public class FileHandlerTest {
    String pathJson = "src/test/testdir/testJson.json";
    String pathXml = "src/test/testdir/testXml.xml";

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
}
