package ru.iteco.fmhandroid.ui.screenElement;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ui.data.Helper.withIndex;

import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.Matcher;

import ru.iteco.fmhandroid.R;

public class PanelElement {

    public static ViewInteraction panelName = onView(withText("Control panel"));
    public static ViewInteraction filterNewsButton = onView(withId(R.id.filter_news_material_button));
    public static ViewInteraction sortNewsButton = onView(withId(R.id.sort_news_material_button));
    public static ViewInteraction newsList = onView(withId(R.id.news_list_recycler_view));

    public static ViewInteraction newsTitleText = onView(withIndex(withId(R.id.news_item_title_text_view), 0));

    public static ViewInteraction newsDescriptionText =
            onView(withIndex(withId(R.id.news_item_description_text_view), 0));
    public static ViewInteraction addNewsButton = onView(withId(R.id.add_news_image_view));
    public static ViewInteraction statusActive =
            onView(withIndex(withId(R.id.news_item_published_text_view), 0));
    public static ViewInteraction statusNotActive =
            onView(withIndex(withId(R.id.news_item_published_text_view), 0));
    public static ViewInteraction deleteNewsButton(String newsTitle) {
        return onView(allOf(withId(R.id.delete_news_item_image_view),
                withParent(withParent(allOf(withId(R.id.news_item_material_card_view),
                        withChild(withChild(withText(newsTitle))))))));
    }

    public static Matcher<View> getNewsPublicationDate() {
        return ViewMatchers.withId(R.id.news_item_publication_date_text_view);
    }

    public static Matcher<View> getButtonEditNews() {
        return ViewMatchers.withId(R.id.edit_news_item_image_view);
    }


}
