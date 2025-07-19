package guru.qa.rococo.page.elements;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.Data;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Data
public class TextField {

    public TextField(String name, SelenideElement selector) {
        this.name = name;
        this.selector = selector;
    }

    String name;
    SelenideElement selector;

    public TextField(String name, ElementsCollection elements) {
        this.name = name;
        this.elements = elements;
    }

    ElementsCollection elements;

    public String getTextFromElements(int index) {
        return elements.get(index).getText();
    }

    public int getElementsCount() {
        return elements.size();
    }

    public List<String> getListValuesFromElements() {
        elements.forEach(element -> {
            element.shouldBe(Condition.visible,Duration.ofSeconds(30));
        });
        List<String> list = new ArrayList<>();
        for (SelenideElement element : elements) {
            list.add(element.getText());
        }
        return list;
    }

    public String getText() {
        return selector.getText();
    }

    public SelenideElement getSelector(int index) {
        return elements.get(index);
    }

    public void Click(int index) {
        getSelector(index).click();
    }
}
