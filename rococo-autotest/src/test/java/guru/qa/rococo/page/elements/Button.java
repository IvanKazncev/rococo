package guru.qa.rococo.page.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Duration;
@Data
@AllArgsConstructor
public class Button {

    String name;
    SelenideElement selector;

    public void click() {
        selector.shouldBe(Condition.visible, Duration.ofSeconds(30));
        selector.click();
    }
}
