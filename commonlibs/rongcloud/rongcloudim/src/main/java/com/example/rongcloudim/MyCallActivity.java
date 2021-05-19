package com.example.rongcloudim;

import android.view.SurfaceView;

import java.util.HashMap;

import io.rong.callkit.BaseCallActivity;
import io.rong.calllib.RongCallCommon;
import io.rong.calllib.RongCallSession;

public class MyCallActivity extends BaseCallActivity {
    /**
     * 电话已拨出。
     * 主叫端拨出电话后，通过回调 onCallOutgoing 通知当前 call 的详细信息。
     *
     * @param callSession 通话实体。
     * @param localVideo  本地 camera 信息。
     */
    @Override
    public void onCallOutgoing(RongCallSession callSession, SurfaceView localVideo) {
        super.onCallOutgoing(callSession, localVideo);
    }

    /**
     * 已建立通话。
     * 通话接通时，通过回调 onCallConnected 通知当前 call 的详细信息。
     *
     * @param callSession 通话实体。
     * @param localVideo  本地 camera 信息。
     */
    @Override
    public void onCallConnected(RongCallSession callSession, SurfaceView localVideo) {
        super.onCallConnected(callSession, localVideo);
    }

    /**
     * 通话结束。
     * 通话中，对方挂断，己方挂断，或者通话过程网络异常造成的通话中断，都会回调 onCallDisconnected。
     *
     * @param callSession 通话实体。
     * @param reason      通话中断原因。
     */
    @Override
    public void onCallDisconnected(RongCallSession callSession, RongCallCommon.CallDisconnectedReason reason) {
        super.onCallDisconnected(callSession, reason);
    }

    /**
     * 被叫端正在振铃。
     * 主叫端拨出电话，被叫端收到请求，发出振铃响应时，回调 onRemoteUserRinging。
     *
     * @param userId 振铃端用户 id。
     */
    @Override
    public void onRemoteUserRinging(String userId) {
        super.onRemoteUserRinging(userId);
    }

    /**
     * 被叫端加入通话。
     * 主叫端拨出电话，被叫端收到请求后，加入通话，回调 onRemoteUserJoined。
     *
     * @param userId      加入用户的 id。<br />
     * @param mediaType   加入用户的媒体类型，audio or video。<br />
     * @param userType    加入用户的类型，1:正常用户,2:观察者。<br />
     * @param remoteVideo 加入用户者的 camera 信息。如果 userType为2，remoteVideo对象为空；<br />
     *                    如果对端调用{@linkRongCallClient#startCall(int, boolean, Conversation.ConversationType, String, List, List, RongCallCommon.CallMediaType, String, StartCameraCallback)} 或
     *                    {@linkRongCallClient#acceptCall(String, int, boolean, StartCameraCallback)}开始的音视频通话，则可以使用如下设置改变对端视频流的镜像显示：<br />
     *                    <pre class="prettyprint">
     *                                            public void onRemoteUserJoined(String userId, RongCallCommon.CallMediaType mediaType, int userType, SurfaceView remoteVideo) {
     *                                                 if (null != remoteVideo) {
     *                                                     ((RongRTCVideoView) remoteVideo).setMirror( boolean);//观看对方视频流是否镜像处理
     *                                                 }
     *                                            }
     *                                            </pre>
     */
    @Override
    public void onRemoteUserJoined(String userId, RongCallCommon.CallMediaType mediaType, int userType, SurfaceView remoteVideo) {
        super.onRemoteUserJoined(userId, mediaType, userType, remoteVideo);
    }

    /**
     * 通话中的某一个参与者，邀请好友加入通话，发出邀请请求后，回调 onRemoteUserInvited。
     * @param userId 被邀请者的ID ,可以通过RongCallClient.getInstance().getCallSession().getObserverUserList().contains(userId) ，查看加入的用户是否在观察者列表中
     * @param mediaType
     */
    @Override
    public void onRemoteUserInvited(String userId, RongCallCommon.CallMediaType mediaType) {
        super.onRemoteUserInvited(userId, mediaType);
    }

    /**
     * 通话中的远端参与者离开。
     * 回调 onRemoteUserLeft 通知状态更新。
     *
     * @param userId 远端参与者的 id。
     * @param reason 远端参与者离开原因。
     */
    @Override
    public void onRemoteUserLeft(String userId, RongCallCommon.CallDisconnectedReason reason) {
        super.onRemoteUserLeft(userId, reason);
    }

    /**
     * 当通话中的某一个参与者切换通话类型，例如由 audio 切换至 video，回调 onMediaTypeChanged。
     *
     * @param userId    切换者的 userId。
     * @param mediaType 切换者，切换后的媒体类型。
     * @param video     切换者，切换后的 camera 信息，如果由 video 切换至 audio，则为 null。
     */
    @Override
    public void onMediaTypeChanged(String userId, RongCallCommon.CallMediaType mediaType, SurfaceView video) {
        super.onMediaTypeChanged(userId, mediaType, video);
    }

    /**
     * 通话过程中，发生异常。
     *
     * @param errorCode 异常原因。
     */
    @Override
    public void onError(RongCallCommon.CallErrorCode errorCode) {
        super.onError(errorCode);
    }

    /**
     * 远端参与者 camera 状态发生变化时，回调 onRemoteCameraDisabled 通知状态变化。
     *
     * @param userId   远端参与者 id。
     * @param disabled 远端参与者 camera 是否可用。
     */
    @Override
    public void onRemoteCameraDisabled(String userId, boolean disabled) {
        super.onRemoteCameraDisabled(userId, disabled);
    }

    /**
     * 远端参与者 麦克风 状态发生变化时，回调 onRemoteMicrophoneDisabled 通知状态变化。
     *
     * @param userId   远端参与者 id。
     * @param disabled 远端参与者 Microphone 是否可用。
     */
    @Override
    public void onRemoteMicrophoneDisabled(String userId, boolean disabled) {
        super.onRemoteMicrophoneDisabled(userId, disabled);
    }


    /**
     * 接收丢包率信息回调
     *
     * @param userId   远端用户的ID
     * @param lossRate 丟包率：0-100
     */
    @Override
    public void onNetworkReceiveLost(String userId, int lossRate) {
        super.onNetworkReceiveLost(userId, lossRate);
    }

    /**
     * 发送丢包率信息回调
     *
     * @param lossRate 丢包率，0-100
     * @param delay 发送端的网络延迟
     */
    @Override
    public void onNetworkSendLost(int lossRate, int delay) {
        super.onNetworkSendLost(lossRate, delay);
    }

    /**
     * 收到某个用户的第一帧视频数据
     *
     * @param userId
     * @param height
     * @param width
     */
    @Override
    public void onFirstRemoteVideoFrame(String userId, int height, int width) {
        super.onFirstRemoteVideoFrame(userId, height, width);
    }

    /**
     * 本端音量大小回调
     *
     * @param audioLevel
     */
    @Override
    public void onAudioLevelSend(String audioLevel) {
        super.onAudioLevelSend(audioLevel);
    }

    /**
     * 对端音量大小回调
     *
     * @param audioLevel
     */
    @Override
    public void onAudioLevelReceive(HashMap<String, String> audioLevel) {
        super.onAudioLevelReceive(audioLevel);
    }

    /**
     * 远端用户发布了自定义视频流
     *
     * @param userId 发布了自定义视频流的用户
     * @param streamId 自定义视频流Id
     * @param tag 流标签
     * @param surfaceView
     */
    @Override
    public void onRemoteUserPublishVideoStream(String userId, String streamId, String tag, SurfaceView surfaceView) {
        super.onRemoteUserPublishVideoStream(userId, streamId, tag, surfaceView);
    }

    /**
     * 远端用户取消发布自定义视频流
     *
     * @param userId 取消发布自定义视频流的用户
     * @param streamId 自定义视频流Id
     * @param tag 流标签
     */
    @Override
    public void onRemoteUserUnpublishVideoStream(String userId, String streamId, String tag) {
        super.onRemoteUserUnpublishVideoStream(userId, streamId, tag);
    }
}
