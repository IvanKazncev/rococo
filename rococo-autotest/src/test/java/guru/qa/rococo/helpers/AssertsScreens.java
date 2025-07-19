package guru.qa.rococo.helpers;

import com.codeborne.selenide.SelenideElement;
import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.ImageComparisonUtil;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;

import org.apache.commons.io.FileUtils;
import org.assertj.core.api.SoftAssertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.TestInfo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static guru.qa.rococo.utils.Utils.getPropertyOrSystemProperty;


public class AssertsScreens {
    static SaveScreenshots saveScreenshots = new SaveScreenshots();

    public static void assertsScreen(@NotNull TestInfo info, @NotNull SelenideElement selectorForScreenshot, String screenNumber) throws IOException {
        var layoutUpdate = getPropertyOrSystemProperty("UPDATE_SCREENS");
        var layoutTest = Boolean.parseBoolean(layoutUpdate);
        String expectedFileName = info.getTestMethod().get().getName() + screenNumber + ".png";
        String expectedScreensDir = "src/test/resources/screens/";
        File actualScreenshot = selectorForScreenshot.screenshot();
        File expectedScreenshot = new File(expectedScreensDir + expectedFileName);
        if (!expectedScreenshot.exists()) {
            saveScreenshots.addImgToAllure("actual", actualScreenshot);
            FileUtils.copyFile(actualScreenshot, new File(expectedScreensDir + expectedFileName).toPath().toFile());
            throw new IllegalArgumentException("Нет скриншота для сравнения. Создан скиншот в папке screens");
        }
        if (layoutTest) {
            try {
                Files.move(actualScreenshot.toPath(), new File(expectedScreensDir + expectedFileName).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Скриншот проверки" + " " + expectedFileName + " " + "обновлен");
            } catch (IOException e) {
                return;
            }
        }
        BufferedImage expectedImage =
                ImageComparisonUtil.readImageFromResources(expectedScreensDir + expectedFileName);
        BufferedImage actualImage =
                ImageComparisonUtil.readImageFromResources(actualScreenshot.toPath().toString());
        File resultDestination = new File("target/diffs/diff_" + expectedFileName);
        ImageComparison imageComparison = new ImageComparison(expectedImage, actualImage, resultDestination);
        ImageComparisonResult result = imageComparison.compareImages();
        if (!result.getImageComparisonState().equals(ImageComparisonState.MATCH)) {
            saveScreenshots.addImgToAllure("actual", actualScreenshot);
            saveScreenshots.addImgToAllure("expected", expectedScreenshot);
            if (resultDestination.exists()) {
                saveScreenshots.addImgToAllure("diff", resultDestination);
            }
            Assertions.assertEquals(result.getImageComparisonState(), ImageComparisonState.MATCH,"Скриншот "+ expectedFileName+ " некорректный");
        }
    }
}
