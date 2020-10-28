package service;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DeleteFile {

    // directory denoted by the abstract pathname.
    public static void delete(String path) {

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        LoggerService.LOGGER.info("Начало выполнения " + dateFormat.format(date));

        //measuring elapsed time using System.nanoTime
        long startTime = System.nanoTime();

        File f = new File(path);         // file to be delete
        f.delete();

        long elapsedTime = System.nanoTime() - startTime;

        LoggerService.LOGGER.info("Total execution time to delete file in nano seconds: " + elapsedTime);
    }
}
