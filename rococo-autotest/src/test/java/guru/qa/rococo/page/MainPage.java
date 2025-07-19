package guru.qa.rococo.page;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import guru.qa.rococo.helpers.AssertsScreens;
import guru.qa.rococo.page.component.Header;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.TextField;
import guru.qa.rococo.page.elements.ToggleSwitch;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainPage {

    protected final SelenideElement mainPage = $(By.tagName("body"));
    protected final ToggleSwitch switchThemeToggle = new ToggleSwitch("Тогл для переключение темы",
            $x("//DIV[@class='lightswitch-track cursor-pointer transition-all duration-[200ms] w-12 h-6 ring-[1px]" +
                    " ring-surface-500/30 rounded-full bg-surface-50 ']"));
    protected final TextField sessionEndMessage = new TextField("Сообщение о завершении сессии",$(".text-base"));

    Header header = new Header();

    @Step("Проверяем отображение сообщение {message}")
    public MainPage messageCheck(final String message) {
        assertEquals(message, sessionEndMessage.getText(),"Сообщение не отобразилось или не корректно");
        return this;
    }

    @Step("Открываем главную страницу")
    public MainPage openMainPage() {
        Selenide.open("http://localhost:3000/");
        return this;
    }

    @Step("Проверяем скрин цвета темы главной страницы")
    public MainPage screenCheck(TestInfo testInfo) throws IOException {
        AssertsScreens.assertsScreen(testInfo, mainPage, "");
        return this;
    }

    @Step("Переключаем тему на главной странице")
    public MainPage switchTheme() {
        switchThemeToggle.click();
        return this;
    }

    @Step("Переходим в раздел Художники")
    public MainPage moveToArtistsPage() {
        header.clickArtist();
        return this;
    }

    @Step("Переходим в раздел Картины")
    public MainPage moveToPicturePage(){
        header.clickPictureButton();
        return this;
    }

    @Step("Переходим в раздел Музеи")
    public MainPage moveToMuseumPage(){
        header.clickMuseumButton();
        return this;
    }

    @Step("Открываем страницу авторизации/регистрации")
    public MainPage moveToLoginPage(){
        header.clickLoginButton();
        return this;
    }

    public static class MainPageAfterLogin {

        protected final Button openProfileButton = new Button("Кнопка открытия профиля",$(".avatar-initials.w-full.h-full"));

        Header header = new Header();

        @Step("Переходи в раздел картины")
        public MainPageAfterLogin moveToArtistsPage() {
            header.clickArtist();
            return this;
        }

        @Step("Открывает окно профиль")
        public MainPageAfterLogin clickProfileButton() {
            openProfileButton.click();
            return this;
        }

        @Step("Переходим в раздел Картины")
        public MainPageAfterLogin moveToPicturePage(){
            header.clickPictureButton();
            return this;
        }

    }

}
