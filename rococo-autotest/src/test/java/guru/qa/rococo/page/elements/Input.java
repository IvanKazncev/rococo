package guru.qa.rococo.page.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

@Data
@AllArgsConstructor
public class Input {

    public Input(String name, ElementsCollection elements) {
        this.name = name;
        this.elements = elements;
    }

    String name;

    public Input(String name, SelenideElement selector) {
        this.name = name;
        this.selector = selector;
    }

    SelenideElement selector;
    ElementsCollection elements;

    public void setValue(final String value) {
        selector.shouldBe(Condition.visible, Duration.ofSeconds(30)).setValue(value);
    }

    public void setValue(final String value, @NotNull SelenideElement selector) {
        selector.shouldBe(Condition.visible, Duration.ofSeconds(30)).setValue(value);
    }

    public SelenideElement getElement(int index) {
        return elements.get(index);
    }
}
