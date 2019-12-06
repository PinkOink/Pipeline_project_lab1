package com.vnkolesnik;

import com.vnkolesnik.pipeline.PipelineManager;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1) {
            PipelineManager pipe = new PipelineManager(args[0]);
            pipe.startPipeline();
        }
    }
}
