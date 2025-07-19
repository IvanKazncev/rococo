package guru.qa.rococo.page.component;

import guru.qa.rococo.page.elements.Button;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class Header {

    private final Button artist = new Button("Кнопка перехода на страницу Художники",
            $x("//A[@href='/artist'][text()='Художники']"));
    private final Button pictureButton = new Button("Кнопка перехода на страницу Картины",
            $x("//A[@href='/painting'][text()='Картины']"));
    private final Button museumButton = new Button("Кнопка перехода на страницу Картины",
            $x("//A[@href='/museum'][text()='Музеи']"));
    private final Button loginButton = new Button("Кнопка перехода на страницу регистрации",
            $(".btn.variant-filled-primary"));

    public void clickArtist() {
        artist.click();
    }

    public void clickPictureButton() {
        pictureButton.click();
    }

    public void clickMuseumButton() {
        museumButton.click();
    }

    public void clickLoginButton() {
        loginButton.click();
    }
}
