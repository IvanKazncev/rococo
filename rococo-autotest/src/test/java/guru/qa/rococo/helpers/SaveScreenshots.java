package guru.qa.rococo.helpers;

import io.qameta.allure.Attachment;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SaveScreenshots {
    @Attachment(value = "{name}",type = "image/png")
    public static byte[] saveScreenshot(String name,byte[] image){
        return image;
    }
    public byte[] addImgToAllure(String name, @NotNull File file){
        try {
            byte[] image = Files.readAllBytes(file.toPath());
          return  saveScreenshot(name,image);
        } catch (IOException e) {
            throw new RuntimeException("Не получилось прочитать байты");
        }
    }
}
