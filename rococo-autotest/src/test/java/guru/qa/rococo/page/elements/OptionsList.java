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
public class OptionsList {

    public OptionsList(String name,ElementsCollection elementsCollection) {
        this.elementsCollection = elementsCollection;
        this.name = name;
    }

    String name;

    public OptionsList(String name, SelenideElement selenideElement) {
        this.name = name;
        this.selenideElement = selenideElement;
    }

    SelenideElement selenideElement;
    ElementsCollection elementsCollection;

    public void setOption(final String option) {
        selenideElement.shouldBe(Condition.visible, Duration.ofSeconds(30))
                .selectOptionContainingText(option);
    }
    public void setOption(final String option, @NotNull SelenideElement selenideElement) {
        selenideElement.shouldBe(Condition.visible, Duration.ofSeconds(30))
                .selectOptionContainingText(option);
    }

    public SelenideElement getElement(int index) {
        return elementsCollection.get(index);
    }
}
