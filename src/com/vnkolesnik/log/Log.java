package com.vnkolesnik.log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

import ru.spbstu.pipeline.logging.Logger;

public class Log implements Logger {

    private FileOutputStream log = null;
    private static Log singleton = null;

    public static Log getLog() {
        if (singleton == null) {
            FileOutputStream out = null;
            try {
                out = new FileOutputStream(System.getProperty("user.dir") + "\\log.txt");
            } catch(IOException err) {
                System.out.println(err.getMessage());
                return null;
            }
            singleton = new Log(out);
        }
        return singleton;
    }

    public void log (String message) {
        try {
            log.write(message.getBytes());
        } catch (IOException err) {
            System.out.println(err.getMessage());
        }
    }

    public void log(String var1, Throwable var2) {
        log(var1);
    }

    public void log(Level var1, String var2) {
        log(var2);
    }

    public void log(Level var1, String var2, Throwable var3) {
        log(var2);
    }

    private Log(FileOutputStream out) {
        log = out;
    }
}