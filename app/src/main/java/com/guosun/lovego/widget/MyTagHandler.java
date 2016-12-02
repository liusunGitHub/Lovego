package com.guosun.lovego.widget;

import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

import org.xml.sax.XMLReader;

import java.lang.reflect.Field;

/**
 * Created by liuguosheng on 2016/11/18.
 */
public class MyTagHandler implements Html.TagHandler {
    private int startIndex = 0;
    private int stopIndex = 0;

    @Override
    public void handleTag(boolean opening, String tag, Editable output, XMLReader xmlReader) {
        if (tag.toLowerCase().equals("span")) {
            if (opening) {
                startGame(tag, output, xmlReader);
            } else {
                endGame(tag, output, xmlReader);
            }
        }
    }

    public void startGame(String tag, Editable output, XMLReader xmlReader) {
        startIndex = output.length();
        try {
            Field elementField = xmlReader.getClass().getDeclaredField("theNewElement");
            elementField.setAccessible(true);
            Object element = elementField.get(xmlReader);
            Field attsField = element.getClass().getDeclaredField("theAtts");
            attsField.setAccessible(true);
            Object atts = attsField.get(element);
            Field dataField = atts.getClass().getDeclaredField("data");
            dataField.setAccessible(true);
            String[] data = (String[]) dataField.get(atts);
            Field lengthField = atts.getClass().getDeclaredField("length");
            lengthField.setAccessible(true);
            Field style = atts.getClass().getDeclaredField("style");
            lengthField.setAccessible(true);
            int len = (Integer) style.get(atts);
            String myAttributeA = null;
            String myAttributeB = null;
            for (int i = 0; i < len; i++) {
                // 这边的src和type换成你自己的属性名就可以了
                if ("href".equals(data[i * 5 + 1])) {
                    myAttributeA = data[i * 5 + 4];
                }
            }
            Log.i("log2344", "src: " + myAttributeA + " type: " + myAttributeB);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void endGame(String tag, Editable output, XMLReader xmlReader) {
        stopIndex = output.length();
        output.setSpan(new GameSpan(), startIndex, stopIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    private class GameSpan extends ClickableSpan {
        @Override
        public void onClick(View v) {
            Log.e("my", "click");
        }
    }
}
