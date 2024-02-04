package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import io.qameta.allure.selenide.AllureSelenide;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static org.openqa.selenium.By.linkText;

public class IssueTest {
    private final static String REPOSITORY = "eroshenkoam/allure-example";
    private final static String ISSUE_NAME = "e.sh";

    @Test
    @Feature("Issue в репозитории")
    @Story("Поиск Issue")
    @Owner("rgrishaev")
    @Severity(SeverityLevel.MINOR)
    @Link(value = "Testing", url = "https://testing.github.com")
    @DisplayName("Вариант теста с чистым Selenide + Listener")
    public void cleanSelenideTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com/");
        $("[data-target='qbsearch-input.inputButtonText']").click();
        $("#query-builder-test").sendKeys(REPOSITORY);
        $("#query-builder-test").submit();

        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $(withText(ISSUE_NAME)).should(Condition.exist);
    }

    @Test
    @Feature("Issue в репозитории")
    @Story("Поиск Issue")
    @Owner("rgrishaev")
    @Severity(SeverityLevel.BLOCKER)
    @Link(value = "Testing", url = "https://testing.github.com")
    @DisplayName("Вариант теста с Лямбда шагами")
    public void lambdaStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Откырть главную страницу github", () -> {
            open("https://github.com/");
        });
        step("Найти репозиторий по средствам стандартного поиска " + REPOSITORY, () -> {
            $("[data-target='qbsearch-input.inputButtonText']").click();
            $("#query-builder-test").sendKeys(REPOSITORY);
            $("#query-builder-test").submit();
        });
        step("Перейти в искомый репозиторий", () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Click on Issues tab", () -> {
            $("#issues-tab").click();
        });
        step("Проверить существование задачи с именем " + ISSUE_NAME, () -> {
            $(withText(ISSUE_NAME)).should(Condition.exist);
        });
    }
    @Test
    @Feature("Issue в репозитории #2")
    @Story("Поиск Issue")
    @Owner("rgrishaev")
    @Severity(SeverityLevel.NORMAL)
    @Link(value = "Testing", url = "https://testing.github.com")
    @DisplayName("Вариант теста с аннотацией @Step")
    public void annotatedStepsTest() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithName(ISSUE_NAME);
    }
}
