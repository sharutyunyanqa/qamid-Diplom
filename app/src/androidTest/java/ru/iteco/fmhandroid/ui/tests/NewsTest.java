package ru.iteco.fmhandroid.ui.tests;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomCategory;
import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Data;
import ru.iteco.fmhandroid.ui.screenElement.FilterNews;
import ru.iteco.fmhandroid.ui.screenElement.NewsElement;
import ru.iteco.fmhandroid.ui.screenElement.PanelElement;
import ru.iteco.fmhandroid.ui.steps.AuthStep;
import ru.iteco.fmhandroid.ui.steps.CreateNewsStep;
import ru.iteco.fmhandroid.ui.steps.EditNewsStep;
import ru.iteco.fmhandroid.ui.steps.FilterNewsStep;
import ru.iteco.fmhandroid.ui.steps.GeneralSteps;
import ru.iteco.fmhandroid.ui.steps.MainSteps;
import ru.iteco.fmhandroid.ui.steps.NewsStep;
import ru.iteco.fmhandroid.ui.steps.PanelStep;
import ru.iteco.fmhandroid.ui.steps.SplashStep;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class NewsTest {

    Data data = new Data();
    AuthStep authStep = new AuthStep();
    MainSteps mainSteps = new MainSteps();
    GeneralSteps generalSteps = new GeneralSteps();
    NewsStep newsStep = new NewsStep();
    PanelStep panelStep= new PanelStep();
    FilterNewsStep filterNews = new FilterNewsStep();
    CreateNewsStep createNewsStep = new CreateNewsStep();
    EditNewsStep editNewsStep = new EditNewsStep();
    NewsElement newsElement = new NewsElement();
    SplashStep splashStep = new SplashStep();


    @Rule
    public ActivityScenarioRule<AppActivity> activityRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void logoutCheck(){
        splashStep.appDownload();
        try {
            mainSteps.loadMainScreen();
        } catch (Exception e) {
            authStep.authWithValidLoginAndPass(authInfo());
            authStep.clickSignInButton();
        } finally {
            mainSteps.loadMainScreen();
            mainSteps.clickAllNewsButton();
        }

    }

    @Test
    @DisplayName("Перейти в Control panael")
    public void clickControlPanel(){
        newsStep.clickEditButton();
        panelStep.checkPanelElements();
    }

    @Test
    @DisplayName("Создать новость")
    public void createNews(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        CreateNewsStep.newsScreenElements();
        createNewsStep.createNews(randomCategory(),data.titleCyr, data.dateOfPublic, data.timeOfPublic, data.descriptionOnCyr);
        generalSteps.clickSaveButton();
        mainSteps.clickOnNews();
        newsStep.loadNewsList();
        panelStep.checkCreateNews(0, data.titleCyr, data.descriptionOnCyr);
        NewsElement.allNewsBlock.perform(swipeDown());
        newsStep.checkOpenNews(0);
        String createDescription = newsStep.getCreateNewsDescription(0);
        assertEquals(data.descriptionOnCyr, createDescription);

    }

    @Test
    @DisplayName("Создать новость с пустым полем Description")
    public void createNewsWithEmptyDescription(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        CreateNewsStep.newsScreenElements();
        createNewsStep.createNews(randomCategory(),data.titleCyr, data.dateOfPublic, data.timeOfPublic, data.descriptionIsEmpty);
        generalSteps.clickSaveButton();
        generalSteps.checkEmptyFieldError();
    }

    @Test
    @DisplayName("Создать новость со спец.символами") 
    public void createNewsWithSymbols(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        CreateNewsStep.newsScreenElements();
        createNewsStep.createNews(randomCategory(),data.titleCyr, data.dateOfPublic, data.timeOfPublic,data.descriptionSymbols);
        generalSteps.clickSaveButton();
        generalSteps.checkInvalidData("Wrong format data", true);
    }

    @Test
    @DisplayName("Создать новость с пустыми полями")
    public void createNewsWithEmptyFields(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        CreateNewsStep.newsScreenElements();
        generalSteps.clickSaveButton();
        generalSteps.checkEmptyFieldError();
    }

    @Test
    @DisplayName("Редактировать новость")
    public void editNews(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(),data.titleCyr, data.dateOfPublic, data.timeOfPublic, data.descriptionOnCyr);
        generalSteps.clickSaveButton();
        newsStep.loadNewsList();
        panelStep.clickEditNewsButton(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.editTitle(data.editNewTitle);
        editNewsStep.editDescription(data.editNewDescription);
        generalSteps.clickSaveButton();
        panelStep.clickOnAnyNews(0);
        assertEquals(data.editNewDescription, panelStep.getEditedNewsDescription(0));
    }

    @Test
    @DisplayName("Отмена редактирование новости")
    public void CancelEditNews(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        createNewsStep.createNews(randomCategory(),data.titleCyr, data.dateOfPublic, data.timeOfPublic, data.descriptionOnCyr);
        generalSteps.clickSaveButton();
        newsStep.loadNewsList();
        panelStep.clickEditNewsButton(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.editTitle(data.editNewTitle);
        editNewsStep.editDescription(data.editNewDescription);
        generalSteps.clickCancelButton();
        generalSteps.clickOkButton();
        panelStep.checkPanelElements();
        PanelElement.newsList.perform(swipeDown());
        panelStep.clickOnAnyNews(0);
        assertEquals(data.editNewTitle, panelStep.getEditedNewsTitle(0));
    }

    @Test
    @DisplayName("Отмена создание носовти")
    public void cancelCreateNews(){
        newsStep.clickEditButton();
        panelStep.clickCreateNewsButton();
        createNewsStep.fillTitle("Text");
        createNewsStep.fillTitle(data.titleCyr);
        generalSteps.clickCancelButton();
        generalSteps.clickOkButton();
        panelStep.checkPanelElements();
    }

    @Test
    @DisplayName("Фильтрация новостей по статусу")
    public void checkFilterOfActive() {
        newsStep.clickEditButton();
        panelStep.openExpendedNewsFilter();
        filterNews.clickNotActiveCheckBox();
        filterNews.checkFilterNewsElements();
        filterNews.clickFilterButton();
        newsStep.loadNewsList();
        panelStep.checkStatusIsActive();
        panelStep.clickEditNewsButton(0);
        editNewsStep.checkEditNewsElements();
        editNewsStep.changeStatus();
        generalSteps.clickSaveButton();
        panelStep.clickOnAnyNews(0);
        panelStep.openExpendedNewsFilter();
        filterNews.clickActiveCheckBox();
        filterNews.clickFilterButton();
        newsStep.loadNewsList();
        panelStep.checkStatusIsNotActive();

    }
    @Test
    @DisplayName("Отмена фильтрации новостей")
    public void cancelFilterNews(){
        newsStep.clickEditButton();
        panelStep.openExpendedNewsFilter();
        generalSteps.clickCancelButton();
        panelStep.checkPanelElements();
    }

    @Test
    @DisplayName("Фильтрация по дате нет новостей")
    public void noNewsToShow(){
        newsStep.openFilter();
        filterNews.checkFilterNewsElements();
        filterNews.fillStartDate(data.nonNewsDate);
        filterNews.fillEndDate(data.nonNewsDate);
        filterNews.clickFilterButton();
        generalSteps.checkNewsListImage();
        generalSteps.checkNothingToShow();
    }

    @Test
    @DisplayName("Отмена фильтрации")
    public void cancelFiltering(){
        newsStep.openFilter();
        filterNews.checkFilterNewsElements();
        filterNews.fillStartDate(data.dateOfPublic);
        generalSteps.clickCancelButton();
        newsStep.checkNewsElements();
    }

    @Test
    @DisplayName("чек-боксы расширенного фильтра")
    public void checkBoxesOfFilter(){
        newsStep.clickEditButton();
        panelStep.openExpendedNewsFilter();
        filterNews.clickActiveCheckBox();
        filterNews.checkBoxStatusIsActive(false);
        filterNews.clickNotActiveCheckBox();
        filterNews.checkBoxStatusIsNotActive(false);
    }

    @Test
    @DisplayName("Сортировка новостей во вкладке Новости")
    public void sortingNews(){
        String filterNewsTitle = newsStep.getFirstNewsTitle(0);
        newsStep.clickSortButton();
        NewsElement.allNewsBlock.perform(swipeDown());
        newsStep.clickSortButton();
        NewsElement.allNewsBlock.perform(swipeDown());
        newsStep.loadNewsList();
        String firstNewsTitleAfterAnotherSorting = newsStep.getFirsNewsTitleAfterAnotherSorting(0);
        assertEquals(filterNewsTitle, firstNewsTitleAfterAnotherSorting);
    }

}
