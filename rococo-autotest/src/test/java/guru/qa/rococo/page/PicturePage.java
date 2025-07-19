package guru.qa.rococo.page;

import com.codeborne.selenide.Selenide;
import guru.qa.rococo.page.component.AddAndEditModal;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.TextField;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PicturePage {

    protected final TextField pictureName = new TextField("Название картины",$$(".text-center"));
    protected final Button addPictureModalButton = new Button("Кнопка открытия модального добавления картины",
            $(".btn.variant-filled-primary.ml-4"));
    protected final Button addPictureButton = new Button("Кнопка добавление картины",$$(".btn.variant-filled-primary").get(0));
    AddAndEditModal addAndEditModal = new AddAndEditModal();

    @Step("Переходи в карточку картины")
    public PicturePage moveToPictureCard(){
        pictureName.getSelector(0).click();
        return this;
    }

    @Step("Нажимаем кнопку Добавить")
    public PicturePage addPictureButtonClick() {
        addPictureButton.click();
        return this;
    }

    @Step("Выбираем место хранения {storagePlace}")
    public PicturePage setStoragePlace(final String storagePlace){
        addAndEditModal.setStorageLocation(storagePlace);
        return this;
    }

    @Step("Добавляем описание картины")
    public PicturePage setDescription(final String description) {
        addAndEditModal.setPictureDescription(description);
        return this;
    }

    @Step("Выбираем автора {artistName} ")
    public PicturePage setArtist(final String artistName) {
        addAndEditModal.setArtistName(artistName);
        return this;
    }

    @Step("Загружаем изображение картины")
    public PicturePage upLoadPicture(File file) {
        addAndEditModal.upLoadPicture(file);
        return this;
    }

    @Step("Вводим название картины")
    public PicturePage setPictureName(String pictureName) {
        addAndEditModal.setPictureName(pictureName);
        return this;
    }

    @Step("Открываем модальное окно добавления картины")
    public PicturePage addPictureModalOpen(){
        addPictureModalButton.click();
        return this;
    }

    @Step("Проверяем открытие страницы Картины")
    public PicturePage picturePageOpenCheck(String value){
        assertEquals(new ArtistPage().titleText.getText(), value,"Страница Картины не открылась");
        return this;
    }

    @Step("Вводим значение {value} в строку поиска")
    public PicturePage setSearchInput(String value) {
        new ArtistPage().searchInput.setValue(value);
        Selenide.webdriver().driver().actions().sendKeys(Keys.ENTER).build().perform();
        return this;
    }

    @Step("Проверяем результат поиска художников")
    public PicturePage searchResultCheck(String value) {
        sleep(3000);
        assertAll(
                () ->    assertEquals(1, pictureName.getListValuesFromElements().size(), "Найдено большее одного совпадения"),
                () ->    assertEquals(value, pictureName.getListValuesFromElements().getFirst(), "Название картины найдено некорректно"));
        return this;
    }
}
