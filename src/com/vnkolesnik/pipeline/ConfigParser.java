package com.vnkolesnik.pipeline;

import ru.spbstu.pipeline.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

class ConfigParser {

    enum Grammar {
        EXECUTOR,
        CONFIGFILE;
        private static String delimeter = "=";
    }

    private final ArrayList<String[]> parsed;

    public String[] get(int index) {
        String[] str = parsed.get(index);
        return str;
    }

    public int getSize() {
        return parsed.size();
    }

    static public ConfigParser make(String filename, Logger log) {
        if (filename != null)
        {
            try {
                FileInputStream file = new FileInputStream(filename);

                byte[] buf = new byte[file.available()];
                file.read(buf, 0, file.available());

                String config = new String(buf);
                Scanner sc = new Scanner(config);
                ArrayList<String[]> toparsed = new ArrayList<>();

                while (sc.hasNextLine()) {
                    String str1 = sc.nextLine();
                    if (sc.hasNextLine()) {
                        String str2 = sc.nextLine();

                        String[] pair = new String[2];
                        if (str1.startsWith(Grammar.EXECUTOR.toString() + Grammar.delimeter) && str1.startsWith(Grammar.EXECUTOR.toString() + Grammar.delimeter)) {
                            pair[0] = str1.substring(Grammar.EXECUTOR.toString().length() + Grammar.delimeter.length(), str1.length());
                            pair[1] = str2.substring(Grammar.CONFIGFILE.toString().length() + Grammar.delimeter.length(), str2.length());
                            toparsed.add(pair);
                        } else {
                            log.log("Config file is incomplete\n");
                            return null;
                        }
                    } else {
                        log.log("Config file is incomplete\n");
                        return null;
                    }
                }

                sc.close();

                return new ConfigParser(toparsed);

            } catch(IOException e){
                log.log("Config file can't be opened or read\n");
                return null;
            }
        }
        else{
            return null;
        }
    }

    private ConfigParser(ArrayList parsed) {
        this.parsed = parsed;
    };
}