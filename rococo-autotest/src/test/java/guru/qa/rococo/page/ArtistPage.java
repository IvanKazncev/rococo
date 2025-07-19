package guru.qa.rococo.page;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.page.component.AddAndEditModal;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.Input;
import guru.qa.rococo.page.elements.TextField;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArtistPage {

    protected final Input searchInput = new Input("Input для поиска художников",
            $(".input"));
    protected final TextField artistNamesText = new TextField("Имена Художников в результате поиска",
            $$(".flex-auto.py-4"));
    protected final TextField titleText = new TextField("Текст названия вкладки", $(".text-3xl.m-4"));
    protected final Button addArtistButton = new Button("Кнопка добавления художника",$(".btn.variant-filled-primary.ml-4"));

    AddAndEditModal addAndEditModal = new AddAndEditModal();

    @Step("Открываем карточку художника")
    public ArtistPage openArtistCardPage(){
        artistNamesText.Click(0);
        return this;
    }

    @Step("Сохраняем изменения")
    public ArtistPage addButtonClick(){
        addAndEditModal.saveChanges();
        return this;
    }

    @Step("Заполняем поле Биография")
    public ArtistPage setBioInfo(final String bioInfo) {
        addAndEditModal.setBiography(bioInfo);
        return this;
    }

    @Step("Загружаем аватарку художника")
    public ArtistPage upLoadArtistAvatar(File file){
        addAndEditModal.upLoadPicture(file);
        return this;
    }

    @Step("Заполняем Имя художника {artistName}")
    public ArtistPage setArtistName(final String artistName) {
        addAndEditModal.setPictureName(artistName);
        return this;
    }

    @Step("Открываем модальное окно добавления художника")
    public ArtistPage openAddArtistModal(){
        addArtistButton.click();
        return this;
    }


    @Step("Вводим значение {value} в строку поиска")
    public ArtistPage setSearchInput(String value) {
        searchInput.setValue(value);
        Selenide.webdriver().driver().actions().sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    @Step("Проверяем открытие страницы Художники")
    public ArtistPage artistPageOpenCheck(String value) {
        assertEquals(value, titleText.getText(), "Страница Художники не открылась");
        return this;
    }

    @Step("Проверяем результат поиска художников")
    public ArtistPage searchResultCheck(String value) {
        sleep(5000);
        assertAll(
                () ->    assertEquals(1, artistNamesText.getListValuesFromElements().size(), "Найдено большее одного совпадения"),
                () ->    assertEquals(value, artistNamesText.getListValuesFromElements().getFirst(), "Имя художника найдено некорректно"));
        return this;
    }
}
