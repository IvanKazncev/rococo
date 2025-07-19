package guru.qa.rococo.page;

import com.codeborne.selenide.Selenide;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MuseumPage {

    @Step("Проверяем открытие страницы Музеи")
    public MuseumPage picturePageOpenCheck(String value){
        assertEquals(new ArtistPage().titleText.getText(), value,"Страница Музеи не открылась");
        return this;
    }

    @Step("Вводим значение {value} в строку поиска")
    public MuseumPage setSearchInput(String value) {
        new ArtistPage().searchInput.setValue(value);
        Selenide.webdriver().driver().actions().sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    @Step("Проверяем результат поиска музея")
    public MuseumPage searchResultCheck(String value) {
        Selenide.sleep(3000);
        assertAll(
                () ->    assertEquals(2, new PicturePage().pictureName.getListValuesFromElements().size(), "Найдено больше совпадений чем ожидалалось"),
                () ->    assertEquals(value, new PicturePage().pictureName.getListValuesFromElements().getFirst(), "Название музея найдено некорректно"));
        return this;
    }
}
