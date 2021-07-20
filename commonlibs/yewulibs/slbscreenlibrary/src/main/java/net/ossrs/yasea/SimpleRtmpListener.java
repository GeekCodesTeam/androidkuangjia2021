package net.ossrs.yasea;

import com.github.faucamp.simplertmp.RtmpHandler;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by Administrator on 2017/12/12.
 */
public abstract class SimpleRtmpListener implements RtmpHandler.RtmpListener,
        SrsRecordHandler.SrsRecordListener, SrsEncodeHandler.SrsEncodeListener {
    @Override
    public void onNetworkWeak() {

    }

    @Override
    public void onNetworkResume() {

    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRecordPause() {

    }

    @Override
    public void onRecordResume() {

    }

    @Override
    public void onRecordStarted(String msg) {

    }

    @Override
    public void onRecordFinished(String msg) {

    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRecordIOException(IOException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRtmpVideoStreaming() {

    }

    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRtmpIOException(IOException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        if (SimplePublisher.getInstance().isRecording()) {
            onRtmpException(e);
        }
    }

    public abstract void onRtmpException(Exception e);
}
