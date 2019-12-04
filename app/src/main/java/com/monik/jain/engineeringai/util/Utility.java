package com.monik.jain.engineeringai.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import androidx.annotation.CheckResult;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import com.google.android.material.snackbar.Snackbar;
import com.monik.jain.engineeringai.BuildConfig;
import com.monik.jain.engineeringai.R;

import static androidx.core.content.ContextCompat.getColor;

public class Utility {

    private static final String TAG = Utility.class.getSimpleName();
    private static AlertDialog progressDialog;
    private static boolean doubleBackToExitPressedOnce;

    private Utility() {//Private constructor
    }

    // check object is null or not.
    public static boolean isEmpty(@Nullable List list) {
        return list == null || list.isEmpty();
    }

    // check object is null or not.
    public static int size(@Nullable List list) {
        return list == null ? 0 : list.size();
    }


    /**
     * checks network availability and creates and shows dialog if not available
     *
     * @param context       current context
     * @param clickListener dialog click listener for positive and negative button
     * @return true if available, false otherwise
     */
    public static boolean isNetworkAvailable(Context context, DialogInterface.OnClickListener clickListener) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        /*if (clickListener != null) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.app_name)
                    .setMessage(R.string.msg_network_error)
                    .setCancelable(false)
                    .setPositiveButton(R.string.retry, clickListener)
                    .setNegativeButton(R.string.ok, clickListener)
                    .show();
        }*/
        return false;
    }

    /**
     * checks network availability and shows toast if not available
     *
     * @param context current context
     * @return true if available, false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        return isNetworkAvailable(context, true);
    }

    /**
     * checks network availability
     *
     * @param context   current context
     * @param showToast whether to show toast if network not available
     * @return true if available, false otherwise
     */
    public static boolean isNetworkAvailable(final Context context, boolean showToast) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                return true;
            }
        }
        return false;
    }

    //convert px to dp
    public static int pxToSp(float px) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                px,
                Resources.getSystem().getDisplayMetrics()
        );
    }

    //convert sp to px
    public static int spToPx(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, Resources.getSystem().getDisplayMetrics());
    }

    //convert dp to px
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(@NonNull Context context, int px) {
        return (int) (px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /**
     * dp to px
     */
    private static int dpTpPx(Context mContext, float measureInDp) {
        Resources r = mContext.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                measureInDp,
                r.getDisplayMetrics()
        );
    }

    /*
     * Distance between 2 LatLong
     * */
    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    // email validation
    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    // 8-20 length validation
    public static boolean isLengthValid(String pwd) {
        return (pwd.length() <= 20 && pwd.length() >= 8);
    }

    // min one cap lettet validation
    public static boolean isMinOneCapLetter(String pwd) {
        return !pwd.equals(pwd.toLowerCase());
    }

    // min one number validation
    public static boolean isMinOneNum(String pwd) {
        return pwd.matches("^(?=.*\\d).+$");
    }

    // min one number validation
    public static boolean isNoSpace(String pwd) {
//        return pwd.matches(".*([ \t]).*");
        return pwd.matches("\\S");
    }

    //make toast
    public static void toast(Context context, String message) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    // hide soft keyboard
    public static void hideSoftKeyboard(View view, Context mContext) {
        if (view != null && mContext != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    //hide Keyboard
    public static void hideKeyboard(Activity activity) {
        if (activity == null)
            return;
        try {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputManager != null && activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
//            Utility.log("KeyBoardUtil >>> " + e);
        }
    }

    public static void hideKeyboard(View v) {
        if (v != null) {
            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    //showKeyboard
    public static void showKeyboard(View view, Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void openAppDetailsInSettings(@NonNull Context context) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        if (intent.resolveActivity(context.getPackageManager()) != null)
            context.startActivity(intent);
    }

    public static void showSnackbar(@NonNull View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
    }

    public static void showSnackbar(@NonNull View view, @StringRes int messageId) {
        Snackbar.make(view, messageId, Snackbar.LENGTH_SHORT).show();
    }
    //Progress Dialog Box ends

    // show alert on un authorize user
    public static AlertDialog showAlertUnAuthorize(String message, @Nullable final Activity activity) {
        return showAlertUnAuthorize(message, activity, false);
    }

    public static void cancelProgress() {
        try {
            if (isProgressOpen()) {
                progressDialog.dismiss();
                progressDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isProgressOpen() {
        return progressDialog != null && progressDialog.isShowing();
    }

    public static void showProgress(@NonNull Context context) {
        showProgress(context, false, null);
    }

    private static void showProgress(@NonNull Context context, boolean cancelable, DialogInterface.OnDismissListener dismissListener) {
        if (!isProgressOpen()) {
            progressDialog = new AlertDialog.Builder(context, R.style.Widget_AppCompat_ProgressBar)
                    .setCancelable(cancelable)
                    .setView(new ProgressBar(context))
                    .setOnDismissListener(dismissListener)
                    .create();
        }
        progressDialog.show();
    }

    @Nullable
    private static AlertDialog showAlertUnAuthorize(String message, @Nullable final Activity activity, boolean cancelable) {
        if (activity != null && !activity.isDestroyed() && !activity.isFinishing()) {
            return new AlertDialog.Builder(activity)
                    .setTitle(R.string.app_name)
                    .setMessage(message)
                    .setCancelable(cancelable)
                    .setPositiveButton(R.string.ok,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.dismiss();
                                }
                            })
                    .show();
        } else return null;
    }

    public static String getCurrentDateTime(String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat(dateFormat, Locale.getDefault());
        return mdformat.format(calendar.getTime());
    }

/*
    public static String getStringFromDate(@NonNull Date date) {
        SimpleDateFormat format = new SimpleDateFormat(KeyClass.DATE_TIME_MM_dd_yyyy_hh_mm_ss, Locale.getDefault());
        return format.format(date);
    }
*/

    public static String getStringFromDate(@NonNull Date date, @NonNull String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.getDefault());
        return format.format(date);
    }

    public static String getStringFromDateEng(@NonNull Date date, @NonNull String formatString) {
        SimpleDateFormat format = new SimpleDateFormat(formatString, Locale.ENGLISH);
        return format.format(date);
    }

    @Nullable
    public static Date getDateFromString(@Nullable String dateString, String dateFormate) {
        if (dateString == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormate, Locale.US);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Utility.log("ParseException", e);
        }
        return date;
    }

    @Nullable
    public static Date getDateFromStringUTC(@Nullable String dateString, String dateFormate) {
        if (dateString == null)
            return null;
        SimpleDateFormat format = new SimpleDateFormat(dateFormate, Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (ParseException e) {
            Utility.log("ParseException", e);
        }
        return date;
    }

 /*   public static String changeDateFormate(String s, String original, String target) {
        if (Utility.isNotEmpty(s)) {
            DateFormat originalFormat = new SimpleDateFormat(original, Locale.getDefault());
            DateFormat targetFormat = new SimpleDateFormat(target, Locale.getDefault());
            Date date = null;
            try {
                date = originalFormat.parse(s);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return targetFormat.format(date);
        } else {
            return null;
        }

    }*/


    public static String getTimeZoneOffsetMinutes() {
        long offset = TimeZone.getDefault().getRawOffset();
        return String.format(Locale.getDefault(), "%s%d", offset < 0 ? "-" : "+", offset / 60000L);
    }


    //-----from apache commons lang-----------------------------------------

    /**
     * <p>Checks if two date objects are on the same day ignoring time.</p>
     * <p>
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param date1 the first date, not altered, not null
     * @param date2 the second date, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either date is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(@NonNull final Date date1, @NonNull final Date date2) {
        final Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);
        final Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        return isSameDay(cal1, cal2);
    }

    /**
     * Returns a calendar instance at the start of this day
     *
     * @return the calendar instance
     */
    public static Calendar today() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }

    /**
     * <p>Checks if two calendar objects are on the same day ignoring time.</p>
     * <p>
     * <p>28 Mar 2002 13:45 and 28 Mar 2002 06:01 would return true.
     * 28 Mar 2002 13:45 and 12 Mar 2002 13:45 would return false.
     * </p>
     *
     * @param cal1 the first calendar, not altered, not null
     * @param cal2 the second calendar, not altered, not null
     * @return true if they represent the same day
     * @throws IllegalArgumentException if either calendar is <code>null</code>
     * @since 2.1
     */
    public static boolean isSameDay(@NonNull final Calendar cal1, @NonNull final Calendar cal2) {
        return cal1.get(Calendar.ERA) == cal2.get(Calendar.ERA) &&
                cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }

    //log with exception value
    public static void log(String value, Throwable e) {
        if (BuildConfig.DEBUG)
            Log.e(TAG, value, e);
    }

    //log with exception value
    public static void log(String tag, String value, Throwable e) {
        if (BuildConfig.DEBUG)
            Log.e(tag, value, e);
    }

    /**
     * convert date to given format
     *
     * @param inputFormat          available in which format
     * @param outputFormat         format that want
     * @param convertLocalTimezone want to convert with loacal time zone
     * @return converted string in output format
     */
    public static String convertDateToFormat(String date, String inputFormat, String outputFormat, boolean convertLocalTimezone) {
        if (TextUtils.isEmpty(date))
            return "";

        String dateOutput;
        Date objDate = null;

        SimpleDateFormat dateFormat = new SimpleDateFormat(inputFormat, Locale.getDefault());
        try {
            objDate = dateFormat.parse(date);
        } catch (ParseException e) {
            Utility.log("ParseException", e);
        }

        if (objDate == null)
            return "";

        if (convertLocalTimezone) {
            Calendar calendar = Calendar.getInstance();
            TimeZone zone = calendar.getTimeZone();
            int offset = zone.getRawOffset();
            objDate.setTime(objDate.getTime() + offset);
        }

        SimpleDateFormat format = new SimpleDateFormat(outputFormat, Locale.getDefault());
        dateOutput = format.format(objDate);
        return dateOutput;
    }
    /*
     *//*
     * To display response error by toast
     *//*
    public static String displayResponseError(@NonNull ResponseBody responseErrorBody, final Activity activity) {
        String userMessage = null;
        try {
            JSONObject jsonObject = new JSONObject(responseErrorBody.string());
            userMessage = jsonObject.getString("Message");
//            Utility.log("Message : " + userMessage);
            Utility.showAlertUnAuthorize(userMessage, activity);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
//            Utility.displayNetworkErrorMsg(activity);
        }
        return userMessage;
    }*/

    /*
     */
    /*
     * To get response error from ErrorBody
     *//*

    public static String getResponseError(@NonNull ResponseBody responseErrorBody) {
        String userMessage = null;
        try {
            JSONObject jsonObject = new JSONObject(responseErrorBody.string());
            userMessage = jsonObject.getString("Message");
//            Utility.log("Message : " + userMessage);
        } catch (JSONException | IOException e) {
            e.printStackTrace();
//            Utility.displayNetworkErrorMsg(activity);
            userMessage = null;
        }

        if (userMessage == null) {
            try {
                JSONObject jsonObject = new JSONObject(responseErrorBody.string());
                JSONObject resultState = jsonObject.getJSONObject("resultState");
                if (resultState != null) {
                    userMessage = resultState.getString("Message");
                }
//            Utility.log("Message : " + userMessage);
            } catch (JSONException | IOException e) {
                e.printStackTrace();
//            Utility.displayNetworkErrorMsg(activity);
                userMessage = null;
            }
        }
        return userMessage;
    }
*/

    public static String getMapImage(@Nullable String latitude, @Nullable String longitude) {
//        return "http://maps.google.com/maps/api/staticmap?markers=color:red|" + latitude + "," + longitude + "&size=400x400&zoom=13&sensor=true";
        return "https://static-maps.yandex.ru/1.x/?lang=en-US&ll=" + longitude + "," + latitude + "&z=12&l=map&size=600,300&pt=" + longitude + "," + latitude + ",vkbkm";
    }

    public static void showMap(@NonNull Context context, @NonNull String latitude, @NonNull String longitude) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("geo:" + latitude + "," + longitude + "?z=15");
        intent.setData(data);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

    /**
     * copied from https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/ArrayUtils.java
     * <p>
     * <p>Copies the given array and adds the given element at the end of the new array.
     * <p>
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     * <p>
     * <p>If the input array is {@code null}, a new one element array is returned
     * whose component type is the same as the element, unless the element itself is null,
     * in which case the return type is Object[]
     * <p>
     * <pre>
     * ArrayUtils.add(null, null)      = IllegalArgumentException
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T>     the component type of the array
     * @param array   the array to "add" the element to, may be {@code null}
     * @param element the object to add, may be {@code null}
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * If both are null, an IllegalArgumentException is thrown
     * @throws IllegalArgumentException if both arguments are null
     * @since 2.1
     */
    @CheckResult
    public static <T> T[] addElementToArray(final T[] array, final T element) {
        Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        @SuppressWarnings("unchecked") // type must be T
        final T[] newArray = (T[]) copyArrayGrow1(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * copied from https://github.com/apache/commons-lang/blob/master/src/main/java/org/apache/commons/lang3/ArrayUtils.java
     * <p>
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array                 The array to copy, must not be {@code null}.
     * @param newArrayComponentType If {@code array} is {@code null}, create a
     *                              size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */
    @CheckResult
    private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
        if (array != null) {
            final int arrayLength = Array.getLength(array);
            final Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

/*
    public static Toolbar setupToolBar(@NonNull AppCompatActivity activity, @NonNull View rootView,
                                       boolean backButton, @StringRes int titleText) {

        Toolbar toolbar = setupAppbar(activity, rootView, backButton);
        setToolbarTitleText(rootView, titleText);
        return toolbar;
    }
*/

 /*   public static Toolbar setupToolBar(@NonNull AppCompatActivity activity, @NonNull View rootView,
                                       boolean backButton, @NonNull String titleText) {

        Toolbar toolbar = setupAppbar(activity, rootView, backButton);
        setToolbarTitleText(rootView, titleText);
        return toolbar;
    }

    public static void setToolbarTitleText(@NonNull View rootView, @NonNull String titleText) {
        TextView txtToolbarTitle = rootView.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(titleText);
    }

    public static void setToolbarTitleText(@NonNull View rootView, @StringRes int titleText) {
        TextView txtToolbarTitle = rootView.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(titleText);
    }

    @CheckResult
    private static Toolbar setupAppbar(@NonNull AppCompatActivity activity, @NonNull View rootView, boolean backButton) {
        Toolbar toolbarMain = rootView.findViewById(R.id.toolbarMain);
        resetAppbar(activity, toolbarMain, backButton);
        return toolbarMain;
    }*/

    public static void resetAppbar(@NonNull AppCompatActivity activity, @NonNull Toolbar toolbarMain, boolean backButton) {
        activity.setSupportActionBar(toolbarMain);
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(backButton);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

 /*   public static Toolbar setupEventToolBar(@NonNull AppCompatActivity activity, @NonNull View rootView,
                                            boolean backButton, @NonNull String titleText, @NonNull String subTitleText) {

        Toolbar toolbar = setupEventAppbar(activity, rootView, backButton);
        setToolbarSubTitleText(rootView, titleText, subTitleText);
        return toolbar;
    }

    public static Toolbar setupEventToolBarWithSubTitleGone(@NonNull AppCompatActivity activity, @NonNull View rootView,
                                                            boolean backButton, @NonNull String titleText) {

        Toolbar toolbar = setupEventAppbar(activity, rootView, backButton);
        setToolbarSubTitleTextGone(rootView, titleText);
        return toolbar;
    }*/

/*
    public static Toolbar setupEventToolBar(@NonNull AppCompatActivity activity, @NonNull View rootView,
                                            boolean backButton, @StringRes int titleText, @StringRes int subTitleText) {

        Toolbar toolbar = setupEventAppbar(activity, rootView, backButton);
        setToolbarSubTitleText(rootView, titleText, subTitleText);
        return toolbar;
    }
*/


/*
    private static void setToolbarSubTitleText(@NonNull View rootView, @StringRes int titleText, @StringRes int subTitleText) {
        TextView txtToolbarTitle = rootView.findViewById(R.id.txtToolbarEventTitle);
        txtToolbarTitle.setText(titleText);
        TextView txtToolbarSubTitle = rootView.findViewById(R.id.txtToolbarEventSubTitle);
        txtToolbarSubTitle.setText(subTitleText);
    }

    private static Toolbar setupEventAppbar(@NonNull AppCompatActivity activity, @NonNull View rootView, boolean backButton) {
        Toolbar toolbarMain = rootView.findViewById(R.id.toolbarEvent);
        resetAppbar(activity, toolbarMain, backButton);
        return toolbarMain;
    }

    private static void setToolbarSubTitleText(@NonNull View rootView, @NonNull String titleText, @NonNull String subTitleText) {
        TextView txtToolbarTitle = rootView.findViewById(R.id.txtToolbarEventTitle);
        txtToolbarTitle.setText(titleText);
        TextView txtToolbarSubTitle = rootView.findViewById(R.id.txtToolbarEventSubTitle);
        txtToolbarSubTitle.setText(subTitleText);
    }

    private static void setToolbarSubTitleTextGone(@NonNull View rootView, @NonNull String titleText) {
        TextView txtToolbarTitle = rootView.findViewById(R.id.txtToolbarEventTitle);
        txtToolbarTitle.setText(titleText);
        TextView txtToolbarSubTitle = rootView.findViewById(R.id.txtToolbarEventSubTitle);
        txtToolbarSubTitle.setVisibility(View.GONE);
    }
*/

    /**
     * @param activity           Activity      context
     * @param statusBarIconTheme Boolean   False for Black Icon and True for white (default) icon // above API 23
     * @param color              int
     * @since note : API 21 doesn't support statusBarIconTheme so default status bar color is #bbbbbb
     */
/*
    @TargetApi(Build.VERSION_CODES.M)
    public static void setTransparentStatusColor(Activity activity, final boolean statusBarIconTheme, int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP_MR1 ||
                    Build.VERSION.SDK_INT == Build.VERSION_CODES.LOLLIPOP) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getColor(activity, statusBarIconTheme ? android.R.color.transparent : R.color.gray_BBBBBB));
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getColor(activity, color));
            } else {
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(getColor(activity, android.R.color.transparent));
            }
            setStatusBarIconTheme(activity, statusBarIconTheme);
        }
    }

*/
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static final void setStatusBarIconTheme(final Activity activity, final boolean statuBarIconTheme) {
        final int flag = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView().setSystemUiVisibility(statuBarIconTheme ? (flag & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)
                : (flag | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));
    }


    // Copy an InputStream to a File.
//
    @Nullable
    public static File copyUriToFile(@NonNull Context context, @NonNull Uri uri) {

        InputStream in = null;
        OutputStream out = null;

        File outFile = null;

        try {

            if (context.getContentResolver() != null) {
                in = context.getContentResolver().openInputStream(uri);
//                outFile = createImageFile(context, KeyClass.TEMP_IMAGE_FILE_NAME_2);
                if (outFile != null && in != null) {
                    out = new FileOutputStream(outFile);
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Ensure that the InputStreams are closed even if there's an exception.
            try {
                if (out != null) {
                    out.close();
                }

                // If you want to close the "in" InputStream yourself then remove this
                // from here but ensure that you close it yourself eventually.
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outFile;
    }


    /**
     * Creates empty file with name {@link KeyClass#TEMP_IMAGE_FILE_NAME}
     * <p>
     * directory used:
     * internal data pictures
     * or external data pictures
     * or internal cache pictures
     * <p>
     * deletes file if already exist
     * creates new empty file
     *
     * @return created empty file or null if any operation fails
     * @throws IOException If an I/O error occurred
     */
    @Nullable
    public static File createImageFile(@NonNull Context context, @NonNull String imageFileName) throws IOException {
        File storageDir = context.getFilesDir();
        boolean dirCreated;
        if (storageDir == null) {
            File externalStorage = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            if (externalStorage == null) {
                storageDir = new File(context.getCacheDir(), Environment.DIRECTORY_PICTURES);
                dirCreated = storageDir.exists() || storageDir.mkdirs();
            } else {
                dirCreated = true;
            }
        } else {
            storageDir = new File(context.getFilesDir(), Environment.DIRECTORY_PICTURES);
            dirCreated = storageDir.exists() || storageDir.mkdirs();
        }

        if (dirCreated) {
            File imageFile = new File(storageDir, imageFileName);
            boolean isDeleted = true;
            if (imageFile.exists()) {
                isDeleted = imageFile.delete();
            }
            if (isDeleted) {
                boolean fileCreated = imageFile.createNewFile();
                if (fileCreated) return imageFile;
                else return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

/*    public static void openAlertDialog(final Context context) {
        final AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name)
                .setMessage(R.string.msg_alert_dialog)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.putExtra(KeyClass.EXTRA_UNREGISTERED_USER, true);
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

    public static String getLocalDateFromUtc(String time) {
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(KeyClass.DATE_TIME_DD_MM_YYYY_HH_MM_SS_CAPITAL_DASH,
                Locale.ENGLISH);
        return LocalDateTime.parse(time, formatter2)
                .atOffset(ZoneOffset.UTC)
                .atZoneSameInstant(ZoneId.systemDefault())
                .format(formatter2);
    }

    public static File storeImage(Context context, Bitmap image) {
        File pictureFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "IMG_" + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();

        } catch (FileNotFoundException e) {
            Log.d(">>", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(">>", "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }*/

    public static String stringDateFromstringDate(String date, String currentFormat, String newFormat) {
        SimpleDateFormat format1 = new SimpleDateFormat(currentFormat, Locale.ENGLISH);
        SimpleDateFormat format2 = new SimpleDateFormat(newFormat, Locale.ENGLISH);
        Date date1 = null;
        try {
            date1 = format1.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date1);
    }

    public static void openDialer(Context context, String contactNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel: " + contactNumber));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean doubleTapToExit(Activity activity, View view) {
        if (doubleBackToExitPressedOnce) {
            return true;
        }
        doubleBackToExitPressedOnce = true;
//        toast(activity,  activity.getString(R.string.back_again));
        showSnackbar(view, activity.getString(R.string.back_again));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
        return false;
    }
}

