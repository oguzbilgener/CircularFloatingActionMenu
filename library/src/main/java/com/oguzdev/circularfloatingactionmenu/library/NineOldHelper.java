package com.oguzdev.circularfloatingactionmenu.library;

import android.view.View;


import static com.nineoldandroids.view.animation.AnimatorProxy.NEEDS_PROXY;
import static com.nineoldandroids.view.animation.AnimatorProxy.wrap;

/**
 * Created by DwGG on 2015/4/4.
 */
public final class NineOldHelper {

    public static Object getRealTarget(Object obj) {
        return (NEEDS_PROXY ? wrap((View) obj) : obj);
    }
}
