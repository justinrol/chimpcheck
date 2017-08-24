package edu.colorado.plv.chimp.performers;

import android.support.test.espresso.AmbiguousViewMatcherException;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.core.deps.guava.collect.Iterables;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiSelector;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import chimp.protobuf.AppEventOuterClass;
import edu.colorado.plv.chimp.components.ActivityManager;
import edu.colorado.plv.chimp.driver.ChimpDriver;
import edu.colorado.plv.chimp.managers.ViewManager;
import edu.colorado.plv.chimp.managers.WildCardManager;
import edu.colorado.plv.chimp.viewactions.ChimpActionFactory;
import edu.colorado.plv.chimp.viewactions.ChimpStagingAction;

import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static edu.colorado.plv.chimp.components.ActivityManager.getResIdFromResName;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by Pezh on 8/24/17.
 */

public class TypePerformer extends Performer<AppEventOuterClass.Type> {

    public TypePerformer(ChimpDriver chimpDriver, ViewManager viewManager, WildCardManager wildCardManager, UiSelector wildCardTopSelector, UiSelector wildCardChildSelector) {
        super("Click", chimpDriver, viewManager, wildCardManager, wildCardTopSelector, wildCardChildSelector);
    }

    @Override
    public ArrayList<ViewManager.ViewTarget> getTargets(AppEventOuterClass.Type type) {
        return viewManager.retrieveTargets(type.getUiid());
    }

    @Override
    public AppEventOuterClass.Type performMatcherAction(AppEventOuterClass.Type origin, Matcher<View> matcher) {
        Espresso.onView(matcher).perform(typeText(origin.getInput()));
        return origin;
    }

    @Override
    public AppEventOuterClass.Type performXYAction(AppEventOuterClass.Type origin, int x, int y) {
        return origin;
    }

    public UiObject2 popOne(ArrayList<UiObject2> uiObjects) {
        Random seed = new Random();
        if (uiObjects.size() > 0) {
            int randIdx = seed.nextInt(uiObjects.size());
            UiObject2 cand = uiObjects.get(randIdx);
            uiObjects.remove(randIdx);
            return cand;
        }
        return null;
    }
    @Override
    public AppEventOuterClass.Type performWildCardAction(AppEventOuterClass.Type origin) {

        Espresso.onView(isRoot()).perform( new ChimpStagingAction() );

        chimpDriver.preemptiveTraceReport();

        try {
            ArrayList<UiObject2> uiObjects = wildCardManager.retrieveTopLevelUIObjects2(By.clickable(true));

            while (uiObjects.size() > 0) {
                Log.i(tag("wildcard counting"), Integer.toString(uiObjects.size()));
                UiObject2 uiObject = popOne(uiObjects);
                try {
                    Log.i(tag("wildcard"), "Attempting to perform action on UiObject");
                    return performUiObjectAction(origin, uiObject);
                } catch (UiObjectNotFoundException e) {
                    Log.i(tag("wildcard"), "Failed clicking on UIObject: " + " " + e.toString());
                }
            }
        } catch (Exception other){
            other.printStackTrace();
        }


        Log.e(tag("wildcard"), "Exhausted all wild card options.");
        return null;

    }
    public AppEventOuterClass.Type performUiObjectAction(AppEventOuterClass.Type origin, UiObject uiObject) throws UiObjectNotFoundException {
        return origin;
    }

    public Iterable<Matcher<? super View>> getViewMatchers(int resid, String text, String content){
        List<Matcher<? super View>> list = new ArrayList<>();

        if(resid != 0){
            list.add(withId(resid));
        }
        if(text != null){
            list.add(withText(text));
        }
        if(content != null){
            list.add(withContentDescription(content));
        }
        list.add(isDisplayed());
        list.add(supportsInputMethods());
        return list;
    }

    public  Iterable<Matcher<? super View>> getViewMatchersFromUIObject2(UiObject2 obj){
        return getViewMatchers(getResIdFromResName(obj.getResourceName()), obj.getText(), obj.getContentDescription());
    }

    public AppEventOuterClass.Type performUiObjectAction(AppEventOuterClass.Type origin, UiObject2 uiObject) throws UiObjectNotFoundException {
        Log.d("Wildcard TypePerformer", uiObject.getResourceName());
        Log.d("Wildcard TypePerformer", uiObject.getClassName());


        Iterable<Matcher<? super View>> its = null;
        ViewInteraction vi;

        try {
            // clear text first
            its = getViewMatchersFromUIObject2(uiObject);
            vi = Espresso.onView(allOf(its));
            vi.perform(clearText());

            // fill with the target text
            its = getViewMatchersFromUIObject2(uiObject);
            vi = Espresso.onView(allOf(its));
            vi.perform(typeText(origin.getInput()));

            // close the soft keyboard
            vi = Espresso.onView(isRoot());
            vi.perform(closeSoftKeyboard());
        } catch (AmbiguousViewMatcherException avme){
            // This should not happen tho
            avme.printStackTrace();
        }
        String display = ActivityManager.getResEntryName(uiObject.getResourceName());
        if(display == null) "Content Desc: ".concat(uiObject.getContentDescription());
        if(display == null) display = "Text: ".concat(uiObject.getText());

        AppEventOuterClass.Type.Builder builder = AppEventOuterClass.Type.newBuilder();
        builder.setUiid(AppEventOuterClass.UIID.newBuilder().setIdType(AppEventOuterClass.UIID.UIIDType.NAME_ID).setNameid(display)).setInput(origin.getInput());

        Log.i(tag("UiObjectAction"), "Completed retrieval and execute.");
        return builder.build();
    }

}

