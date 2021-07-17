package org.home.env;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsManager {
    final private String filePathSettings = "Settings.json";

    public Settings load() {
        Settings settings;
        try (Stream<String> lines = Files.lines(Paths.get(filePathSettings))) {
            String content = lines.collect(Collectors.joining());
            settings = JSON.parseObject(content, Settings.class);
        } catch (IOException e) {
            //java.nio.file.NoSuchFileException
            settings = new Settings();
            save(settings);
        }
        return settings;
    }

    public void save(Settings settings) {
        String json = JSON.toJSONString(settings);
        try {
            Files.write(Paths.get(filePathSettings), json.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
