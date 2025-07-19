package guru.qa.rococo.settings;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.codeborne.selenide.Configuration.*;

public class BaseTest {

    private static void getRemoteDriver() {
        proxyEnabled = true;
        browser = "chrome";
        headless = false;
        timeout = 10000;
        browserSize = "1920x1080";
        remote = "http://localhost:4444";
    }

    @BeforeEach
    public void setUp() {
        getRemoteDriver();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
