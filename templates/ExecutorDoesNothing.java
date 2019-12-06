package com.vnkolesnik.templates;

import ru.spbstu.pipeline.*;

import java.util.List;

public class ExecutorDoesNothing implements Executor {
    /*  Producer            */
    @Override
    public Object get() {
        return null;
    }


    /*  InitializeProducer  */
    @Override
    public void addConsumer(Consumer var1){

    }
    @Override
    public void addConsumers(List<Consumer> var1){

    }


    /*  Consumer            */
    @Override
    public void loadDataFrom(Producer producer){

    }


    /*  InitializeConsumer   */
    public void addProducer(Producer var1){

    }

    public void addProducers(List<Producer> var1){

    }


    /*  Runnable             */
    public void run() {

    }


    /*  Fallible             */
    @Override
    public Status status() {
        return null;
    }
}
