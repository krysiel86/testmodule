package io.github.krysiel86.utils;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.HashMap;


//public class MaxMediationBannerUtils implements PrebidMediationDelegate {
//    //    private static final String TAG = MaxMediationBannerUtils.class.getSimpleName();
//    private static final String TAG = "tpmn";
//    private   Bundle extras;
//    private final WeakReference<SuezXBannerAd> adViewReference;
//
////    public MaxMediationBannerUtils(SuezXBannerAd adView) {
////        Log.e("tpmn","MaxMediationBannerUtils");
////        adViewReference = new WeakReference<>(adView);
////    }
////
//    public MaxMediationBannerUtils(
//            Bundle adMobExtrasBundle,
//            SuezXBannerAd adView
//    ) {
//        this.extras = adMobExtrasBundle;
//        this.adViewReference = new WeakReference<>(adView);
//    }
//
//    public MaxMediationBannerUtils(
//            SuezXBannerAd adView
//    ) {
//        this.adViewReference = new WeakReference<>(adView);
//    }
//    @Override
//    public void setResponseToLocalExtras(@Nullable BidResponse response) {
//        Log.e("tpmn","setResponseToLocalExtras");
//        if (adViewReference.get() != null) {
////            String responseId ;
//            String responseId = null;
//            if (response != null) {
//                responseId = response.getId();
////            } else {
////                responseId = null;
//            }
//
//            Log.e("tpmn", "setResponseToLocalExtras responseId : " + responseId);
//            Log.e("tpmn", "setResponseToLocalExtras EXTRA_RESPONSE_ID : " + PrebidMaxMediationAdapter.EXTRA_RESPONSE_ID);
//
//            adViewReference.get().setLocalExtraParameter(PrebidMaxMediationAdapter.EXTRA_RESPONSE_ID, responseId);
//
//        }
//    }
//
//    @Override
//    public boolean canPerformRefresh() {
//        Log.e("tpmn", "setResponseToLocalExtras canPerformRefresh()");
//        SuezXBannerAd view = adViewReference.get();
//        if (view == null) {
//            LogUtil.error(TAG, "AdView is null, it can be destroyed as WeakReference");
//            return false;
//        }
//
//        final VisibilityTrackerOption visibilityTrackerOption = new VisibilityTrackerOption(NativeEventTracker.EventType.IMPRESSION);
//        final VisibilityChecker checker = new VisibilityChecker(visibilityTrackerOption);
//
//        boolean isVisible = checker.isVisibleForRefresh(view);
//        if (isVisible) {
//            Log.e(TAG, "Visibility checker result true ");
//        } else {
//            Log.e(TAG, "새로 고침을 수행할 수 없습니다. 광고보기가 보이지 않습니다." );
//        }
//        return true;
//    }
//
//    @Override
//    public void handleKeywordsUpdate(@Nullable HashMap<String, String> keywords) {
//    }
//
//}
