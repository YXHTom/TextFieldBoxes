package studio.carbonylgroup.textfieldboxes;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

/**
 * Text Field Boxes
 * Created by CarbonylGroup on 2017/08/25
 */
public class Utils {

    /**
     * get the current theme primary color
     */
    public static int fetchPrimaryColor(Context context) {

        TypedValue typedValue = new TypedValue();
        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    /**
     * set EditText cursor color
     */
    public static void setCursorDrawableColor(EditText _editText, int _colorRes) {

        try {
            Field fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(_editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(_editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);
            Drawable[] drawables = new Drawable[2];
            drawables[0] = _editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[1] = _editText.getContext().getResources().getDrawable(mCursorDrawableRes);
            drawables[0].setColorFilter(_colorRes, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(_colorRes, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (Throwable ignored) {
        }
    }

    /**
     * convert dp value into pixels
     */
    public static int dp2px(Context context, float dp) {
        Resources r = context.getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return Math.round(px);
    }

    /**
     * adjust the alpha value of the color
     *
     * @return the color after adjustment
     */
    public static int adjustAlpha(int color, float _alphaFactor) {
        int alpha = Math.round(Color.alpha(color) * _alphaFactor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }
}
