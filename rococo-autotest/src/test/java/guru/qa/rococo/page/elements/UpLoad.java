package guru.qa.rococo.page.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;
import java.time.Duration;

@Data
@AllArgsConstructor
public class UpLoad {

    String name;
    SelenideElement selector;

    public void upLoadPicture(File file) {
        selector.shouldBe(Condition.visible, Duration.ofSeconds(30)).uploadFile(file);
    }
}
