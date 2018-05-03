package Cumulus.Util.Configuration;

import Cumulus.Util.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigWrapper {
    private String CONFIG_PATH;
    private String DEFAULT_CONFIG_PATH;

    private String RELATIVE_DEFAULT_CONFIG_PATH = "\\resources\\config\\defaultconfig.json";

    public ConfigWrapper(String path) {
        this.CONFIG_PATH = path;
        this.DEFAULT_CONFIG_PATH = System.getProperty("user.dir") + RELATIVE_DEFAULT_CONFIG_PATH;
    }

    public void setConfigPath(String path) {
        this.CONFIG_PATH = path;
    }

    public void saveConfig(Configuration config) {
        JSONObject obj = new JSONObject();
        obj.put("bot_token", config.getBotToken());
        ObjectMapper mapper = new ObjectMapper();

        try(FileWriter file = new FileWriter(CONFIG_PATH)) {
            file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public Configuration readConfig() {
        JSONParser parser = new JSONParser();
        Configuration config = new Configuration();

        try{
            JSONObject obj = (JSONObject) parser.parse(new FileReader(CONFIG_PATH));

            config.setBotToken((String) obj.get("bot_token"));

        }catch (FileNotFoundException e){
            Logger.errorContext(this.getClass(), "Config File Not Found. Generating new one...");
            setConfigToDefault();
            config.flagError();
        }catch (IOException e){
            config.flagError();
        }catch (ParseException e){
            config.flagError();
        }

        return config;
    }

    public void setConfigToDefault() {
        JSONParser parser = new JSONParser();
        JSONObject obj;
        ObjectMapper mapper = new ObjectMapper();

        try{
            obj = (JSONObject) parser.parse(new FileReader(DEFAULT_CONFIG_PATH));
        }catch (FileNotFoundException e){
            Logger.errorContext(this.getClass(), "Default Config File Not Found. Unable to set config to default.");
            return;
        }catch (IOException e){
            Logger.errorContext(this.getClass(), "An error occurred while reading the default config. Unable to set config to default.");
            return;
        }catch (ParseException e){
            Logger.errorContext(this.getClass(), "An error occurred while reading the default config. Unable to set config to default.");
            return;
        }

        try(FileWriter file = new FileWriter(CONFIG_PATH)) {
            file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj));
            file.flush();
        }catch(IOException e){
            Logger.errorContext(this.getClass(), "Unable to set config to default, an IOException occurred while writing to config file.");
        }
    }

}
