package guru.qa.rococo.page;

import guru.qa.rococo.page.elements.Button;
import guru.qa.rococo.page.elements.Input;
import guru.qa.rococo.service.DataService;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.*;

public class ProfilePage {

    protected final Button exitButton = new Button("Кнопка Выйти", $(".btn.variant-ghost"));
    protected final Input firstNameInput = new Input("Поле ввода Имени", $(By.name("firstname")));
    protected final Input lastNameInput = new Input("Поле ввода фамилии", $(By.name("surname")));
    protected final Button refreshProfileButton = new Button("Кнопка обновить профиль", $(".btn.variant-filled-primary"));

    @Step("Нажимаем кнопку Обновить профиль")
    public ProfilePage refreshProfile() {
        refreshProfileButton.click();
        return this;
    }

    @Step("Вводим Фамилию")
    public ProfilePage setLastName(final String lastName) {
        lastNameInput.setValue(lastName);
        return this;
    }

    @Step("Вводим Имя")
    public ProfilePage setFirstName(final String firstName) {
        firstNameInput.setValue(firstName);
        return this;
    }

    @Step("Нажимаем кнопку Выйти")
    public ProfilePage clickExit() {
        exitButton.click();
        return this;
    }

    @Step("Проверяем добавление фамилии и имени в бд")
    public ProfilePage profileUpdateCheck(final String username) throws SQLException {
        var user = new DataService().getUserDataByUserName(username);
        assertAll(
                () -> assertEquals(username,user.getFirstname(),"Данные по Имени не обновились"),
                () -> assertEquals(username,user.getLastname(),"Данные по Фамилии не обновились")
        );
        return this;
    }
}
