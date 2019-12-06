package com.vnkolesnik.pipeline;

import com.vnkolesnik.log.Log;

import ru.spbstu.pipeline.Reader;
import ru.spbstu.pipeline.Writer;
import ru.spbstu.pipeline.Executor;

import ru.spbstu.pipeline.logging.Logger;

public class PipelineManager {
    Log logger;
    Reader readerExec;
    Writer writerExec;
    Executor[] execs;

    public PipelineManager(String filename) {
        logger = Log.getLog();
        ConfigParser parsed = ConfigParser.make(filename, logger);

        if (parsed != null) {
            execs = new Executor[parsed.getSize() - 2];

            Class[] execsParams = {String.class, Logger.class};
            Class classname;

            for (int i = 1; i <= execs.length; i++) {
                try {
                    classname = Class.forName(parsed.get(i)[0]);
                    execs[i - 1] = (Executor) classname.getConstructor(execsParams).newInstance(parsed.get(i)[1], logger);
                } catch (Exception e) {
                    logger.log("Wrong executor name\n");
                }
            }

            try {
                classname = Class.forName(parsed.get(0)[0]);
                readerExec = (Reader) classname.getConstructor(execsParams).newInstance(parsed.get(0)[1], logger);

                classname = Class.forName(parsed.get(parsed.getSize() - 1)[0]);
                writerExec = (Writer) classname.getConstructor(execsParams).newInstance(parsed.get(parsed.getSize() - 1)[1], logger);
            } catch (Exception e) {
                logger.log("Wrong executor name\n");
            }

            if (execs.length != 0) {
                readerExec.addConsumer(execs[0]);
                execs[execs.length - 1].addConsumer(writerExec);
                for (int i = 1; i < execs.length; i++) {
                    execs[i - 1].addConsumer(execs[i]);
                }
            } else {
                readerExec.addConsumer(writerExec);
            }
        } else {
            logger.log("Config file error\n");
        }
    }

    public void startPipeline() {
        readerExec.run();
    }
}
