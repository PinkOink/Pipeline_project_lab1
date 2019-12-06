package com.vnkolesnik.executorreader;

import ru.spbstu.pipeline.logging.Logger;

import ru.spbstu.pipeline.Reader;
import ru.spbstu.pipeline.Consumer;
import ru.spbstu.pipeline.Status;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class ExecutorReader implements Reader {
    private String filename_read;
    private int read_size;
    private Consumer[] consumers;
    private static Logger logger;
    private int pos;
    private byte[] buf;


    public ExecutorReader(String filename_config, Logger log) {
        ConfigParser parsed = ConfigParser.make(filename_config, log);
        filename_read = parsed.get(ConfigParser.Grammar.INPUT_FILE);
        read_size = Integer.parseInt(parsed.get(ConfigParser.Grammar.READ_SIZE));
        consumers = null;
        logger = log;
        buf = null;
    }

    /*  Producer            */
    @Override
    public byte[] get() {
        return buf;
    }


    /*  InitializeProducer  */
    @Override
    public void addConsumer(Consumer var1){
        consumers = new Consumer[1];
        consumers[0] = var1;
    }
    @Override
    public void addConsumers(List<Consumer> var1){
        consumers = new Consumer[var1.size()];
        var1.toArray(consumers);
    }


    /*  Runnable             */
    public void run() {
        FileInputStream file;
        try {
            file = new FileInputStream(filename_read);
        } catch (IOException e){
            logger.log("File can't be opened\n");
            file = null;
        }


        if (file != null) {
            try {
                buf = new byte[Math.min(read_size, file.available())];
                while (file.available() != 0) {
                    if (file.available() < read_size) {
                        buf = new byte[file.available()];
                    }
                    file.read(buf);

                    for (Consumer c: consumers) {
                        c.loadDataFrom(this);
                    }
                    for (Consumer c: consumers) {
                        c.run();
                    }
                }
            } catch (IOException e) {
                logger.log("Reading file error\n");
            }
        }
    }


    /*  Fallible             */
    @Override
    public Status status() {
        return null;
    }
}