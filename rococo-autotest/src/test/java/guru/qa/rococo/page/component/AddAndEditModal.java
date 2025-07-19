package guru.qa.rococo.page.component;

import guru.qa.rococo.page.elements.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.openqa.selenium.By;

import java.io.File;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AddAndEditModal {
    private final Input pictureModalInputs = new Input("Поля ввода в модальном окне добавления картины",$$(".input"));
    private final UpLoad upLoadInput = new UpLoad("Поле ввода для загрузки файла",pictureModalInputs.getElement(1));
    private final UpLoad upLoadInputInEditing = new UpLoad("Поле ввода для загрузки файла",pictureModalInputs.getElement(0));
    private final OptionsList optionsList = new OptionsList("Список вариантов авторов картины",
            $$(".select"));
    private final Input pictureDescription = new Input("Инпут добавление описания картины",$(By.name("description")));
    private final Button saveButton = new Button("Кнопка перехода на страницу регистрации",
            $(".btn.variant-filled-primary"));
    private final Input biographyInput = new Input("Поле ввода Биографии",$(By.name("biography")));

    public AddAndEditModal setBiography(final String biography) {
        biographyInput.setValue(biography);
        return this;
    }

    public AddAndEditModal saveChanges() {
        saveButton.click();
        return this;
    }

    public AddAndEditModal setStorageLocation(String storageLocation) {
        optionsList.setOption(storageLocation,optionsList.getElement(1));
        return this;
    }

    public AddAndEditModal setPictureDescription(String description) {
        pictureDescription.setValue(description);
        return this;
    }

    public AddAndEditModal setArtistName(String artistName) {
        optionsList.setOption(artistName,optionsList.getElement(0));
        return this;
    }

    public AddAndEditModal setPictureName(final String pictureName) {
        pictureModalInputs.setValue(pictureName,pictureModalInputs.getElement(0));
        return this;
    }

    public AddAndEditModal setPictureNameInEditing(final String pictureName) {
        pictureModalInputs.setValue(pictureName,pictureModalInputs.getElement(1));
        return this;
    }

    public AddAndEditModal upLoadPicture(File file) {
        upLoadInput.upLoadPicture(file);
        return this;
    }

    public AddAndEditModal upLoadPictureInEditing(File file) {
        upLoadInputInEditing.upLoadPicture(file);
        return this;
    }

    static public class editArtistModal{
        private final UpLoad upLoadAvatarInput = new UpLoad("Поле загрузки аватара",$(By.name("photo")));
        private final Input artistNameInput = new Input("Поле ввода имени артиста",$(By.name("name")));
        private final Input biographyInput = new Input("Поле ввода Биографии",$(By.name("biography")));

        public void saveButtonClick(){
            new AddAndEditModal().saveChanges();
        }
        public void setBiography(final String biography) {
            biographyInput.setValue(biography);
        }
        public void setArtistName(final String artistName) {
            artistNameInput.setValue(artistName);
        }
        public void upLoadAvatar(File file) {
            upLoadAvatarInput.upLoadPicture(file);
        }
    }
}
