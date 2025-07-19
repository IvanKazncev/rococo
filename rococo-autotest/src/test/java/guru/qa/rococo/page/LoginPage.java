package guru.qa.rococo.page;

import guru.qa.rococo.helpers.AssertsScreens;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.Input;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.By;

import java.io.IOException;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    protected final Button moveToRegistrationPageButton = new Button("Кнопка перехода на страницу регистрации",
            $(".form__link"));
    protected final Input loginInput = new Input("Поле ввода Имя пользователя",$(By.name("username")));
    protected final Input passwordInput = new Input("Поле ввода пароля",$(By.name("password")));
    protected final Button submitButton = new Button("Кнопка Войти",$(".form__submit"));


    @Step("Нажимаем кнопку войти")
    public LoginPage loginButtonClick(){
        submitButton.click();
        return this;
    }

    @Step("Вводим пароль")
    public LoginPage setPassword(final String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Вводим логин")
    public LoginPage setLogin(final String login){
        loginInput.setValue(login);
        return this;
    }

    @Step("Проверяем проверяем верстку страницы")
    public LoginPage screenCheck(TestInfo testInfo) throws IOException {
        AssertsScreens.assertsScreen(testInfo, new MainPage().mainPage, "");
        return this;
    }

    @Step("Переходим на страницу регистрации")
    public LoginPage moveToRegistrationPage(){
        moveToRegistrationPageButton.click();
        return this;
    }
}
