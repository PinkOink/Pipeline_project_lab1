package com.vnkolesnik.executorreader;

import ru.spbstu.pipeline.logging.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

class ConfigParser {

    enum Grammar {
        INPUT_FILE,
        READ_SIZE;
        private static String delimeter = "=";
    }

    private final HashMap<Grammar, String> parsed;

    public String get(Grammar word) {
        String str = parsed.get(word.ordinal());
        return str;
    }

    static public ConfigParser make(String filename, Logger log) {
        if (filename != null)
        {
            try {
                FileInputStream file = new FileInputStream(filename);

                byte[] buf = new byte[file.available()];
                file.read(buf, 0, file.available());

                String config = new String(buf);
                HashMap toparsed = new HashMap<Grammar, String>();
                Scanner sc = new Scanner(config);

                for (Grammar param : Grammar.values()){
                    if (sc.hasNextLine()) {
                        String str = sc.nextLine();
                        if (str.startsWith(param.toString() + Grammar.delimeter)) {
                            toparsed.put(param.ordinal(), str.substring(param.toString().length() + Grammar.delimeter.length(), str.length()));
                        } else {
                            log.log("Config-file error\n");
                            sc.close();
                            return null;
                        }
                    } else {
                        log.log("Config-file error\n");
                        sc.close();
                        return null;
                    }
                }

                sc.close();

                return new ConfigParser(toparsed);

            } catch(IOException e){
                log.log("Config can't be opened or read\n");
                return null;
            }
        }
        else{
            return null;
        }
    }

    private ConfigParser(HashMap parsed) {
        this.parsed = parsed;
    };
}