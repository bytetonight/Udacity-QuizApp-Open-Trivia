package com.itternet.utils;

import android.text.Html;
import android.text.Spanned;

/**
 * Created by ByteTonight on 07.05.2017.
 */

public class Utils
{

    /**
     * Yet another deprecation in Android-N to work around
     * @param html is the string that might contain unwanted HTML entities
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html)
    {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }
        else
        {
            result = Html.fromHtml(html);
        }
        return result;
    }
}
