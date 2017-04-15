package com.visual.android.slugroute;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by RamiK on 4/14/2017.
 */

public class KeyScrollView extends ScrollView {

    public KeyScrollView(Context context, )
    {
        super(context);
    }
    public KeyScrollView(Context context, AttributeSet attributeSet)
    {
        super(context,attributeSet);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        System.out.println("l: " + l);
        System.out.println("t: " + t);
        System.out.println("oldl: " + oldl);
        System.out.println("oldt: " + oldt);

        // Grab the last child placed in the ScrollView, we need it to determinate the bottom position.
        View view = (View) getChildAt(getChildCount()-1);

        if(view.getTop()==t){
            // reaches the top end
            System.out.println("TOP");
        }

        // Calculate the scrolldiff
        int diff = (view.getBottom()-(getHeight()+getScrollY()));

        // if diff is zero, then the bottom has been reached
        if( diff == 0 ) {
            System.out.println("BOTTOM");
        }

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
