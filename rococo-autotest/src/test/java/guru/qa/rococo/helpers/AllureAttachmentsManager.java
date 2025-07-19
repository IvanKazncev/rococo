package guru.qa.rococo.helpers;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;

public class AllureAttachmentsManager {
    @Attachment(value = "Screenshot",type = "image/png")
    public static byte[] screenshot(){
        return Selenide.screenshot(OutputType.BYTES);
    }
}
