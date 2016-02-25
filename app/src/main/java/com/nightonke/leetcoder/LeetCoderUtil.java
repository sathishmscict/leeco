package com.nightonke.leetcoder;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.github.johnpersano.supertoasts.SuperToast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

/**
 * Created by Weiping on 2016/2/24.
 */
public class LeetCoderUtil {

    public static int textCounter(String s) {
        int counter = 0;
        for (char c : s.toCharArray()) {
            if (c < 128) {
                counter++;
            } else {
                counter += 2;
            }
        }
        return counter;
    }

    public static Spannable getDialogContent(Context mContext, String pre, String post, boolean countValid) {
        int i0 = 0, i1 = pre.length(), i2 = i1 + post.length();
        Spannable spannable = new SpannableString(pre + post);
        spannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_NORMAL), i0, i1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_OPPOSITE), i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (!countValid) {
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.error_red)), i0, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        } else {
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.error_red)), i0, i1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorPrimary)), i1, i2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

    public static void setStatusBarColor(Context mContext) {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = ((AppCompatActivity)mContext).getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.colorPrimary));
        }
    }

    public static TextView getActionBarTextView(Toolbar mToolBar) {
        TextView titleTextView = null;
        try {
            Field f = mToolBar.getClass().getDeclaredField("mTitleTextView");
            f.setAccessible(true);
            titleTextView = (TextView) f.get(mToolBar);
        } catch (NoSuchFieldException e) {
        } catch (IllegalAccessException e) {
        }
        return titleTextView;
    }

    public static String getTextDrawableString(String string) {
        string = deleteI(string);
        int blankPosition = string.indexOf(" ");
        if (blankPosition == -1 || blankPosition == string.length() - 1) {
            if (string.length() == 1) return string.toUpperCase();
            else {
                char first = getFirstLetter(string);
                char second = getSecondLetter(string);
                if (second == ' ') return String.valueOf(first).toUpperCase();
                else return String.valueOf(first).toUpperCase() + String.valueOf(second).toUpperCase();
            }
        } else {
            char first = getFirstLetter(string);
            char second = getFirstLetter(string.substring(blankPosition + 1));
            if (second == ' ') return String.valueOf(first).toUpperCase();
            else return String.valueOf(first).toUpperCase() + String.valueOf(second).toUpperCase();
        }
    }

    public static String deleteI(String string) {
        boolean isAllI = true;
        int blankPosition = string.lastIndexOf(" ");
        for (int i = blankPosition + 1; i < string.length(); i++) {
            if (string.charAt(i) != 'I') {
                isAllI = false;
                break;
            }
        }
        if (isAllI) return string.substring(0, blankPosition);
        else return string;

    }

    public static char getFirstLetter(String string) {
        for (char c : string.toCharArray()) {
            if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) return c;
        }
        return ' ';
    }

    public static char getSecondLetter(String string) {
        boolean isFirst = true;
        for (char c : string.toCharArray()) {
            if (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z')) {
                if (isFirst) isFirst = false;
                else return c;
            }
        }
        return ' ';
    }

    public static int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(Colors.length);
        while (Colors[p].equals(lastColor0)
                || Colors[p].equals(lastColor1)
                || Colors[p].equals(lastColor2)) {
            p = random.nextInt(Colors.length);
        }
        lastColor0 = lastColor1;
        lastColor1 = lastColor2;
        lastColor2 = Colors[p];
        return Color.parseColor(Colors[p]);
    }

    public static int getScreenWidth(Context context) {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    public static int dpToPx(int dp) {
        DisplayMetrics displayMetrics = LeetCoderApplication.getAppContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static void sortProblemSearchResult(ArrayList<Problem_Index> problemIndices) {
        Collections.sort(problemIndices, new Comparator<Problem_Index>() {
            @Override
            public int compare(Problem_Index lhs, Problem_Index rhs) {
                return lhs.getTitle().compareTo(rhs.getTitle());
            }
        });
    }

    public static void showToast(Context context, String text, int color) {
        SuperToast.cancelAllSuperToasts();
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(color);
        superToast.show();
    }

    private static String lastToast = "";
    public static void showToast(Context context, String text) {
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.VERY_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.show();
    }

    public static void showToast(Context context, int textId) {
        String text = context.getResources().getString(textId);
        if (context == null) return;
        if (lastToast.equals(text)) {
            SuperToast.cancelAllSuperToasts();
        } else {
            lastToast = text;
        }
        SuperToast superToast = new SuperToast(context);
        superToast.setAnimations(SuperToast.Animations.FLYIN);
        superToast.setDuration(SuperToast.Duration.VERY_SHORT);
        superToast.setTextColor(Color.parseColor("#ffffff"));
        superToast.setTextSize(SuperToast.TextSize.SMALL);
        superToast.setText(text);
        superToast.setBackground(SuperToast.Background.BLUE);
        superToast.show();
    }






    private static String lastColor0, lastColor1, lastColor2;

    private static Random random;

    private static String[] Colors = {"#F44336",
            "#E91E63",
            "#9C27B0",
            "#673AB7",
            "#3F51B5",
            "#2196F3",
            "#03A9F4",
            "#00BCD4",
            "#009688",
            "#4CAF50",
            "#8BC34A",
            "#CDDC39",
            "#FFEB3B",
            "#FFC107",
            "#FF9800",
            "#FF5722",
            "#795548",
            "#9E9E9E",
            "#607D8B"};

    private static LeetCoderUtil ourInstance = new LeetCoderUtil();

    public static LeetCoderUtil getInstance() {
        return ourInstance;
    }

    private LeetCoderUtil() {
    }
}
