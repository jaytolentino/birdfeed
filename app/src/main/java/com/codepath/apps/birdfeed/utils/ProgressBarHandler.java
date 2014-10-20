package com.codepath.apps.birdfeed.utils;

import android.app.Activity;
import android.view.Window;

/**
 * Created by jay on 10/20/14.
 */
public class ProgressBarHandler {

    public static void create(Activity activity) {
        activity.requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
    }

    public static void showProgressBar(Activity activity) {
        activity.setProgressBarIndeterminateVisibility(true);
    }

    public static void hideProgressBar(Activity activity) {
        activity.setProgressBarIndeterminateVisibility(false);
    }
}
