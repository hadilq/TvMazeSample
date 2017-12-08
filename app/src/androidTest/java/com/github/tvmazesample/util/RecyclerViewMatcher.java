package com.github.tvmazesample.util;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RecyclerViewMatcher {
    private final Matcher<View> recyclerMatcher;

    RecyclerViewMatcher(Matcher<View> recyclerMatcher) {
        this.recyclerMatcher = recyclerMatcher;
    }

    public Matcher<View> atPosition(final int position) {
        return atIndexedPositionOnView(0, position, -1);
    }

    public Matcher<View> atIndexedPosition(int index, int position) {
        return atIndexedPositionOnView(index, position, -1);
    }

    public Matcher<View> atIndexedPositionOnView(final int index, final int position, final int targetViewId) {

        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;
            View targetView;
            Resources resources;

            public void describeTo(Description description) {
                String idDescription = Integer.toString(targetViewId);
                if (this.resources != null && targetViewId != -1) {
                    try {
                        idDescription = this.resources.getResourceName(targetViewId);
                    } catch (Resources.NotFoundException var4) {
                        idDescription = String.format("%s (resource name not found)", targetViewId);
                    }
                }
                description.appendText("with id: " + idDescription);
                recyclerMatcher.describeTo(description);
            }

            public boolean matchesSafely(View view) {
                if (resources == null) {
                    resources = view.getResources();
                }

                if (recyclerMatcher.matches(view) && currentIndex++ == index && view instanceof RecyclerView) {
                    View childView = ((RecyclerView) view).findViewHolderForAdapterPosition(position).itemView;
                    if (childView != null && targetViewId != -1) {
                        targetView = childView.findViewById(targetViewId);
                    } else {
                        targetView = view;
                    }
                    return targetViewId == -1;
                } else if (targetView == null) {
                    return false;
                }
                return view == targetView;

            }
        };
    }
}