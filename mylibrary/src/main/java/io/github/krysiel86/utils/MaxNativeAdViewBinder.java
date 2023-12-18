package io.github.krysiel86.utils;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

public class MaxNativeAdViewBinder {
    protected final View mainView;
    @LayoutRes
    protected final int layoutResourceId;
    @IdRes
    protected final int titleTextViewId;
    @IdRes
    protected final int advertiserTextViewId;
    @IdRes
    protected final int bodyTextViewId;
    @IdRes
    protected final int iconImageViewId;
    @IdRes
    protected final int iconContentViewId;
    @IdRes
    protected final int optionsContentViewGroupId;
    @IdRes
    protected final int optionsContentFrameLayoutId;
    @IdRes
    protected final int mediaContentViewGroupId;
    @IdRes
    protected final int mediaContentFrameLayoutId;
    @IdRes
    protected final int callToActionButtonId;
    protected final String templateType;

    private MaxNativeAdViewBinder(View var1, @LayoutRes int var2, @IdRes int var3, @IdRes int var4, @IdRes int var5, @IdRes int var6, @IdRes int var7, @IdRes int var8, @IdRes int var9, @IdRes int var10, @IdRes int var11, @IdRes int var12, String var13) {
        this.mainView = var1;
        this.layoutResourceId = var2;
        this.titleTextViewId = var3;
        this.advertiserTextViewId = var4;
        this.bodyTextViewId = var5;
        this.iconImageViewId = var6;
        this.iconContentViewId = var7;
        this.optionsContentViewGroupId = var8;
        this.optionsContentFrameLayoutId = var9;
        this.mediaContentViewGroupId = var10;
        this.mediaContentFrameLayoutId = var11;
        this.callToActionButtonId = var12;
        this.templateType = var13;
    }

    public static class Builder {
        private final View a;
        @LayoutRes
        private final int b;
        @IdRes
        private int c;
        @IdRes
        private int d;
        @IdRes
        private int e;
        @IdRes
        private int f;
        @IdRes
        private int g;
        @IdRes
        private int h;
        @IdRes
        private int i;
        @IdRes
        private int j;
        @IdRes
        private int k;
        @IdRes
        private int l;
        private String m;

        public Builder(View var1) {
            this(-1, var1);
        }

        public Builder(@LayoutRes int var1) {
            this(var1, (View) null);
        }

        private Builder(@LayoutRes int var1, View var2) {
            this.c = -1;
            this.d = -1;
            this.e = -1;
            this.f = -1;
            this.g = -1;
            this.h = -1;
            this.i = -1;
            this.j = -1;
            this.k = -1;
            this.l = -1;
            this.b = var1;
            this.a = var2;
        }

        public Builder setTitleTextViewId(@IdRes int var1) {
            this.c = var1;
            return this;
        }

        public Builder setAdvertiserTextViewId(@IdRes int var1) {
            this.d = var1;
            return this;
        }

        public Builder setBodyTextViewId(@IdRes int var1) {
            this.e = var1;
            return this;
        }

        public Builder setIconImageViewId(@IdRes int var1) {
            this.f = var1;
            return this;
        }

        @Deprecated
        protected Builder setIconContentViewId(@IdRes int var1) {
            this.g = var1;
            return this;
        }

        public Builder setOptionsContentViewGroupId(@IdRes int var1) {
            this.h = var1;
            return this;
        }

        @Deprecated
        protected Builder setOptionsContentFrameLayoutId(@IdRes int var1) {
            this.i = var1;
            return this;
        }

        public Builder setMediaContentViewGroupId(@IdRes int var1) {
            this.j = var1;
            return this;
        }

        @Deprecated
        protected Builder setMediaContentFrameLayoutId(@IdRes int var1) {
            this.k = var1;
            return this;
        }

        public Builder setCallToActionButtonId(@IdRes int var1) {
            this.l = var1;
            return this;
        }

        protected Builder setTemplateType(String var1) {
            this.m = var1;
            return this;
        }

        public MaxNativeAdViewBinder build() {
            return new MaxNativeAdViewBinder(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m);
        }
    }
}
