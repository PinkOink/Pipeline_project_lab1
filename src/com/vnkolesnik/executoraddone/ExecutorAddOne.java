package com.vnkolesnik.executoraddone;

import ru.spbstu.pipeline.logging.Logger;

import ru.spbstu.pipeline.Consumer;
import ru.spbstu.pipeline.Executor;
import ru.spbstu.pipeline.Producer;
import ru.spbstu.pipeline.Status;

import java.util.List;

public class ExecutorAddOne implements Executor {
    private Logger logger;
    private byte[] buf;
    private Producer[] producers;
    private Consumer[] consumers;

    public ExecutorAddOne(String filename, Logger log) {
        logger = log;
    }

    /*  Producer            */
    @Override
    public Object get() {
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
        byte [] buf_out = new byte[buf.length + 1];
        System.arraycopy(buf, 0, buf_out, 0, buf.length);
        buf_out[buf_out.length - 1] = '1';
        buf = buf_out;
        buf_out = null;

        for (Consumer c: consumers) {
            c.loadDataFrom(this);
        }
        for (Consumer c: consumers) {
            c.run();
        }
    }


    /*  Fallible             */
    @Override
    public Status status() {
        return null;
    }
}
