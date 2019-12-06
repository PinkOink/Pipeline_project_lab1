package com.vnkolesnik.executorwriter;

import ru.spbstu.pipeline.logging.Logger;

import ru.spbstu.pipeline.Producer;
import ru.spbstu.pipeline.Status;
import ru.spbstu.pipeline.Writer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExecutorWriter implements Writer {
    private String filename_write;
    private FileOutputStream out;
    private Producer[] producers;
    private static Logger logger;
    private byte[] buf;


    public ExecutorWriter(String filename_config, Logger log) {
        ConfigParser parsed = ConfigParser.make(filename_config, log);
        filename_write = parsed.get(ConfigParser.Grammar.OUTPUT_FILE);
        producers = null;
        logger = log;
        buf = null;

        try {
            out = new FileOutputStream(filename_write);
        } catch (IOException e) {
            logger.log("File can't be opened\n");
        }
    }

    /*  Consumer            */
    @Override
    public void loadDataFrom(Producer producer){
        buf = ((byte [])producer.get()).clone();
    }


    /*  InitializeConsumer   */
    public void addProducer(Producer var1){
        producers = new Producer[1];
        producers[0] = var1;
    }

    public void addProducers(List<Producer> var1){
        producers = new Producer[var1.size()];
        var1.toArray(producers);
    }


    /*  Runnable             */
    public void run() {
        try {
            out.write(buf);
        } catch (IOException e) {
            logger.log("Writing file error\n");
        }
    }


    /*  Fallible             */
    @Override
    public Status status() {
        return null;
    }
}
