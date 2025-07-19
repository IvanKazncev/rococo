package guru.qa.rococo.page;

import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.helpers.AssertsScreens;
import guru.qa.rococo.page.component.AddAndEditModal;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.TextField;
import io.qameta.allure.Step;
import org.junit.jupiter.api.TestInfo;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.sleep;
import static org.junit.jupiter.api.Assertions.*;

public class PictureCardPage {

    AddAndEditModal addAndEditModal = new AddAndEditModal();

    protected final Button openEditModal = new Button("Кнопка открытия модального окна создания/редактирования карточки",
            $(".btn.variant-ghost.m-3.mx-auto.block.w-full"));
    protected final SelenideElement pictureInCard = $(".my-4.mx-auto.w-full");
    protected final TextField descriptionText = new TextField("Описание в карточке картины", $$(".m-4"));
    protected final TextField pictureName = new TextField("Название картины",$(".card-header.text-center.font-bold"));

    @Step("Проверяем название картины")
    public PictureCardPage pictureNameCheck(final String name) {
        assertEquals(name, pictureName.getText(),"Название картины не изменилось");
        return this;
    }

    @Step("Проверяем описание в карточке картины")
    public PictureCardPage descriptionTextCheck(final String text) {
        assertEquals(text, descriptionText.getSelector(1).getText(),"Описание картины не изменилось");
        return this;
    }

    @Step("Проверка изменения картины")
    public PictureCardPage changePictureCheck(TestInfo testInfo) throws IOException {
        refresh();
        sleep(5000);
        AssertsScreens.assertsScreen(testInfo,pictureInCard,"");
        return this;
    }

    @Step("Сохраняем изменения в карточке")
    public PictureCardPage saveButtonClick(){
        addAndEditModal.saveChanges();
        return this;
    }

    @Step("Открываем модальное окно редактирования карточки")
    public PictureCardPage openEditModal() {
        openEditModal.click();
        return this;
    }

    @Step("Выбираем место хранения {storagePlace}")
    public PictureCardPage setStoragePlace(final String storagePlace){
        addAndEditModal.setStorageLocation(storagePlace);
        return this;
    }

    @Step("Добавляем описание картины")
    public PictureCardPage setDescription(final String description) {
        addAndEditModal.setPictureDescription(description);
        return this;
    }

    @Step("Выбираем автора {artistName} ")
    public PictureCardPage setArtist(final String artistName) {
        addAndEditModal.setArtistName(artistName);
        return this;
    }

    @Step("Загружаем изображение картины")
    public PictureCardPage upLoadPicture(File file) {
        addAndEditModal.upLoadPictureInEditing(file);
        return this;
    }

    @Step("Вводим название картины")
    public PictureCardPage setPictureName(String pictureName) {
        addAndEditModal.setPictureNameInEditing(pictureName);
        return this;
    }
}
