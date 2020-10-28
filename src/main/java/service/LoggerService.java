package service;

import java.io.FileInputStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LoggerService {
    // LOGGER
    public static final Logger LOGGER = Logger.getLogger(LoggerService.class.getName());;
    static {
        try(FileInputStream ins = new FileInputStream("log.config")){ //полный путь до файла с конфигами
            LogManager.getLogManager().readConfiguration(ins);

        }catch (Exception ignore){
            ignore.printStackTrace();
        }
    }
}
