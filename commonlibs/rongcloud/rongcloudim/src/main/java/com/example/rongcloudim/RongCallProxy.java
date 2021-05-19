package com.example.rongcloudim;

import android.view.SurfaceView;

import java.util.HashMap;

import io.rong.calllib.IRongCallListener;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;

public class RongCallProxy implements IRongCallListener {

    private IRongCallListener mCallListener;  // 增加一个监听。
    private IRongCallListener mAppCallListener;  // 增加一个监听。

    public interface AppCallListener {
        void onCallOutgoing(RongCallSession var1, SurfaceView var2);
    }

    /*设置自己应用的监听*/
    public void setAppCallListener(IRongCallListener listener) {
        this.mAppCallListener = listener;
    }

    /*修改对应的通话状态回调的方法，使其回调到您设置的应用自身的监听*/
    @Override
    public void onCallOutgoing(RongCallSession callSession, SurfaceView localVideo) {
        if (mCallListener != null) {
            mCallListener.onCallOutgoing(callSession, localVideo);
        }
        /*增加的代码，回调应用设置的监听*/
        if (mAppCallListener != null) {
            mAppCallListener.onCallOutgoing(callSession, localVideo);
        }
    }

    @Override
    public void onCallConnected(RongCallSession rongCallSession, SurfaceView surfaceView) {

    }

    @Override
    public void onCallDisconnected(RongCallSession rongCallSession, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {

    }

    @Override
    public void onRemoteUserRinging(String s) {

    }

    @Override
    public void onRemoteUserJoined(String s, RongCallCommon.CallMediaType callMediaType, int i, SurfaceView surfaceView) {

    }

    @Override
    public void onRemoteUserInvited(String s, RongCallCommon.CallMediaType callMediaType) {

    }

    @Override
    public void onRemoteUserLeft(String s, RongCallCommon.CallDisconnectedReason callDisconnectedReason) {

    }

    @Override
    public void onMediaTypeChanged(String s, RongCallCommon.CallMediaType callMediaType, SurfaceView surfaceView) {

    }

    @Override
    public void onError(RongCallCommon.CallErrorCode callErrorCode) {

    }

    @Override
    public void onRemoteCameraDisabled(String s, boolean b) {

    }

    @Override
    public void onRemoteMicrophoneDisabled(String s, boolean b) {

    }

    @Override
    public void onNetworkReceiveLost(String s, int i) {

    }

    @Override
    public void onNetworkSendLost(int i, int i1) {

    }

    @Override
    public void onFirstRemoteVideoFrame(String s, int i, int i1) {

    }

    @Override
    public void onAudioLevelSend(String s) {

    }

    @Override
    public void onAudioLevelReceive(HashMap<String, String> hashMap) {

    }

    @Override
    public void onRemoteUserPublishVideoStream(String s, String s1, String s2, SurfaceView surfaceView) {

    }

    @Override
    public void onRemoteUserUnpublishVideoStream(String s, String s1, String s2) {

    }
    // 根据您的需要，同样的方式修改其它通话状态回调函数。
}

