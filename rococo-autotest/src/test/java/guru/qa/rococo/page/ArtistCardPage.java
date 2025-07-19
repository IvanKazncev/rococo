package guru.qa.rococo.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.helpers.AssertsScreens;
import guru.qa.rococo.page.component.AddAndEditModal;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.TextField;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;

public class ArtistCardPage {

    protected final Button openEditModalButton = new Button("Кнопка открытия модального окна редактирования художника",
            $(".btn.variant-ghost.m-3.mx-auto.block.w-full"));
    protected final SelenideElement artistAvatar = $(".avatar-image.w-full.h-full.object-cover");
    protected final TextField artistNameTextField = new TextField("Текстовое поле с именем художника",$(".card-header.text-center.font-bold.text-2xl"));
    AddAndEditModal.editArtistModal editArtistModal = new AddAndEditModal.editArtistModal();



    @Step("Проверка имени художника")
    public ArtistCardPage artistNameCheck(final String artistName) {
        Assertions.assertEquals(artistName,artistNameTextField.getText(),"Имя художника не изменилось");
        return this;
    }

    @Step("Проверка аватарки художника")
    public ArtistCardPage avatarCheck(TestInfo info) throws IOException {
        Selenide.sleep(5000);
        AssertsScreens.assertsScreen(info,artistAvatar,"");
        return this;
    }

    @Step("Нажимаем кнопку Сохранить")
    public ArtistCardPage saveButtonClick(){
        editArtistModal.saveButtonClick();
        return this;
    }

    @Step("Вводим описание биографии")
    public ArtistCardPage setBioInfo(final String bioInfo) {
        editArtistModal.setBiography(bioInfo);
        return this;
    }

    @Step("Вводим имя художника {name}")
    public ArtistCardPage setArtisName(final String name) {
        editArtistModal.setArtistName(name);
        return this;
    }

    @Step("Загружаем аватар художника")
    public ArtistCardPage setAvatar(File file) {
        editArtistModal.upLoadAvatar(file);
        return this;
    }

    @Step("Открываем модальное окно редактирование карточки художника")
    public ArtistCardPage openEditModal() {
        openEditModalButton.click();
        return this;
    }
}
