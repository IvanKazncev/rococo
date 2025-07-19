package guru.qa.rococo.test;


import com.shared.proto.artists.AddArtistResponse;
import com.shared.proto.paintings.AddPaintingResponse;
import guru.qa.rococo.entity.user.UserAuthEntity;
import guru.qa.rococo.jupiter.annotation.CreatingArtist;
import guru.qa.rococo.jupiter.annotation.CreatingPicture;
import guru.qa.rococo.jupiter.annotation.CreatingUser;
import guru.qa.rococo.page.*;
import guru.qa.rococo.settings.BaseTest;
import guru.qa.rococo.utils.FakerData;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@DisplayName("WebTests")
public class UiTest extends BaseTest {

    MainPage mainPage = new MainPage();
    ArtistPage artistPage = new ArtistPage();
    PicturePage picturePage = new PicturePage();
    MuseumPage museumPage = new MuseumPage();
    LoginPage loginPage = new LoginPage();
    RegistrationPage registrationPage = new RegistrationPage();
    MainPage.MainPageAfterLogin mainPageAfterLogin = new MainPage.MainPageAfterLogin();
    ProfilePage profilePage = new ProfilePage();
    PictureCardPage pictureCardPage = new PictureCardPage();
    ArtistCardPage artistCardPage = new ArtistCardPage();

    @Test
    @DisplayName("Проверка светлой темы на гостевой странице")
    void lightThemeOnMainPageTest(TestInfo testInfo) throws IOException {
        mainPage.openMainPage()
            .screenCheck(testInfo);
    }

    @Test
    @DisplayName("Проверка переключения темы")
    void darkThemeOnPageTest(TestInfo testInfo) throws IOException {
        mainPage.openMainPage()
                .switchTheme()
                .screenCheck(testInfo);

    }

    @Test
    @DisplayName("Проверка перехода на страницу Художники")
    void artistPageOpenCheck() {
        mainPage.openMainPage()
                .moveToArtistsPage();
        artistPage.artistPageOpenCheck("Художники");
    }

    @Test
    @DisplayName("Проверка перехода на страницу Картины")
    void picturePageOpenCheck() {
        mainPage.openMainPage()
                .moveToPicturePage();
        picturePage.picturePageOpenCheck("Картины");
    }

    @Test
    @DisplayName("Проверка перехода на страницу Музеи")
    void museumPageOpenCheck() {
        mainPage.openMainPage()
                .moveToMuseumPage();
        museumPage.picturePageOpenCheck("Музеи");
    }

    @Test
    @DisplayName("Проверка поиска на странице Художники")
    void artistPageSearchCheck() {
        mainPage.openMainPage()
                .moveToArtistsPage();
        artistPage.setSearchInput("Иван Шишкин")
                .searchResultCheck("Иван Шишкин");
    }

    @Test
    @DisplayName("Проверка поиска на странице Картины")
    void picturePageSearchCheck() {
        mainPage.openMainPage()
                .moveToPicturePage();
        picturePage.setSearchInput("Мона Лиза")
                .searchResultCheck("Мона Лиза");
    }

    @Test
    @DisplayName("Проверка поиска на странице Музеи")
    void museumPageSearchCheck() {
        mainPage.openMainPage()
                .moveToMuseumPage();
        museumPage.setSearchInput("Замечательное место")
                .searchResultCheck("Замечательное место");
    }

    @Test
    @DisplayName("Проверка верстки страницы логина")
    void loginPageCheck(TestInfo testInfo) throws IOException {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.screenCheck(testInfo);

    }

    @Test
    @DisplayName("Проверка верстки страницы регистрации")
    void registrationCheck(TestInfo testInfo) throws IOException {

        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.moveToRegistrationPage();
        registrationPage.screenCheck(testInfo);
    }

    @Test
    @DisplayName("Проверка регистрации нового пользователя")
    void registrationNewAccountCheck() throws SQLException {
        String userName = FakerData.getLogin();
        String password = FakerData.getPassword();
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.moveToRegistrationPage();
        registrationPage.setUserName(userName)
                .setPassword(password)
                .submitPassword(password)
                .clickRegistrationButton()
                .logInToTheSystemButtonClick()
                .checkUserInDb(userName);
    }

    @CreatingUser
    @Test
    @DisplayName("Проверка выхода из аккаунта")
    void exitFromProfileCheck(@NotNull UserAuthEntity user) {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.clickProfileButton();
        profilePage.clickExit();
        mainPage.messageCheck("Сессия завершена");
    }

    @CreatingUser
    @Test
    @DisplayName("Проверка редактирования профиля")
    void editProfileCheck(@NotNull UserAuthEntity user) throws SQLException {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.clickProfileButton();
        profilePage.setFirstName(user.getUsername())
                .setLastName(user.getUsername())
                .refreshProfile();
        mainPage.messageCheck("Профиль обновлен");
        profilePage.profileUpdateCheck(user.getUsername());
    }

    @CreatingUser
    @Test
    @DisplayName("Проверка добавления картины")
    void addPictureCheck(@NotNull UserAuthEntity user) {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.moveToPicturePage();
        picturePage.addPictureModalOpen()
                .setPictureName(user.getUsername())
                .upLoadPicture(new File("src/test/resources/avatar.png"))
                .setArtist("Иван Шишкин")
                .setDescription(FakerData.randomString(20))
                .setStoragePlace("Замечательное место")
                .addPictureButtonClick()
                .setSearchInput(user.getUsername())
                .searchResultCheck(user.getUsername());
    }

    @CreatingUser
    @CreatingPicture
    @Test
    @DisplayName("Проверка редактирование карточки картины")
    void editPictureCheck(@NotNull UserAuthEntity user, @NotNull AddPaintingResponse picture, TestInfo testInfo) throws IOException {
        String description = FakerData.randomString(20);
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.moveToPicturePage();
        picturePage.setSearchInput(picture.getPainting().getTitle())
                .moveToPictureCard();
        pictureCardPage.openEditModal()
                .setPictureName(user.getUsername())
                .upLoadPicture(new File("src/test/resources/золото.jpg"))
                .setArtist("Илья Репин")
                .setDescription(description)
                .setStoragePlace("Уникальное местоположение")
                .saveButtonClick()
                .changePictureCheck(testInfo)
                .descriptionTextCheck(description)
                .pictureNameCheck(user.getUsername());
    }

    @CreatingUser
    @Test
    @DisplayName("Проверка добавление художника")
    void addArtistCheck(@NotNull UserAuthEntity user) {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.moveToArtistsPage();
        artistPage.openAddArtistModal()
                .setArtistName(user.getUsername())
                .upLoadArtistAvatar(new File("src/test/resources/avatar.png"))
                .setBioInfo(FakerData.randomString(20))
                .addButtonClick()
                .setSearchInput(user.getUsername())
                .searchResultCheck(user.getUsername());
    }

    @CreatingUser
    @CreatingArtist
    @Test
    @DisplayName("Проверка редактирование карточки художника")
    void editArtistCheck(@NotNull UserAuthEntity user,@NotNull AddArtistResponse addArtistResponse,TestInfo testInfo) throws IOException {
        mainPage.openMainPage()
                .moveToLoginPage();
        loginPage.setLogin(user.getUsername())
                .setPassword(user.getPassword())
                .loginButtonClick();
        mainPageAfterLogin.moveToArtistsPage();
        artistPage.setSearchInput(addArtistResponse.getArtist().getName())
                .openArtistCardPage();
        artistCardPage.openEditModal()
                .setAvatar(new File("src/test/resources/золото.jpg"))
                .setArtisName(user.getUsername())
                .setBioInfo(FakerData.randomString(20))
                .saveButtonClick()
                .avatarCheck(testInfo)
                .artistNameCheck(user.getUsername());
    }
}
