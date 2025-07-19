package guru.qa.rococo.page;

import guru.qa.rococo.helpers.AssertsScreens;
import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.Input;
import guru.qa.rococo.service.DataService;
import io.qameta.allure.Step;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.TestInfo;

import java.io.IOException;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

public class RegistrationPage {

    protected final Input userNameInput = new Input("Поле ввода имени  пользователя",
            $("#username"));
    protected final Input passwordInput = new Input("Поле ввода пароля при создании пользователя",
            $("#password"));
    protected final Input submitPasswordInput = new Input("Поле подтверждения пароля при создании пользователя",
            $("#passwordSubmit"));
    protected final Button registrationButton = new Button("Кнопка Зарегестрироваться",
            $(".form__submit"));


    @Step("Проверяем проверяем верстку страницы регистрации")
    public RegistrationPage screenCheck(TestInfo testInfo) throws IOException {
        AssertsScreens.assertsScreen(testInfo, new MainPage().mainPage, "");
        return this;
    }

    @Step("Вводим имя пользователя")
    public RegistrationPage setUserName(String userName) {
        userNameInput.setValue(userName);
        return this;
    }

    @Step("Вводим пароль пользователя")
    public RegistrationPage setPassword(String password) {
        passwordInput.setValue(password);
        return this;
    }

    @Step("Подтверждаем пароль пользователя")
    public RegistrationPage submitPassword(String password) {
        submitPasswordInput.setValue(password);
        return this;
    }

    @Step("Подтверждаем регистрацию нового пользователя")
    public RegistrationPage clickRegistrationButton() {
        registrationButton.click();
        return this;
    }

    @Step("Нажимаем кнопку Войти в систему")
    public RegistrationPage logInToTheSystemButtonClick() {
        registrationButton.click();
        return this;
    }

    @Step("Проверяем создание пользователя в базе данных")
    public RegistrationPage checkUserInDb(String userName) throws SQLException {
        assertEquals(userName, new DataService().findUserByName(userName),"Пользователь в БД не найден или некорректен");
        return this;
    }

}
