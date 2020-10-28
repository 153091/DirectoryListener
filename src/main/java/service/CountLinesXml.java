package service;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CountLinesXml implements FileHandler {

    private String path; // directory denoted by the abstract pathname.

    public CountLinesXml(String path) {
        this.path = path;
    }


    @Override
    public void run() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        LoggerService.LOGGER.info("Начало выполнения " + dateFormat.format(date));

        //measuring elapsed time using System.nanoTime
        long startTime = System.nanoTime();

        //code
        try{

            File myFile =new File(path);
            FileReader fileReader = new FileReader(myFile);
            LineNumberReader lineNumberReader = new LineNumberReader(fileReader);

            int lineNumber = 0;

            while (lineNumberReader.readLine() != null){
                lineNumber++;
            }

            LoggerService.LOGGER.info("Lines in the file " + lineNumber);

            lineNumberReader.close();

        }catch(IOException e){
            e.printStackTrace();
        }




        long elapsedTime = System.nanoTime() - startTime;

        LoggerService.LOGGER.info("Total execution time to read lines of the file in nano seconds: " + elapsedTime);
    }
}
