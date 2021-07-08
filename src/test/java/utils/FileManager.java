package utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileManager {

    public static String loadJsonFile(String fileName){
        String fileContent = "Unable  to read file";
        try(Stream<String> stream = Files.lines(Paths.get("src\\test\\resources\\files\\" + fileName))){
            fileContent = stream.collect(Collectors.joining());
        }catch (IOException e){
            e.printStackTrace();
        }
        return fileContent;
    }
}
