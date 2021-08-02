package com.fosung.xuanchuanlan.common.util

import android.hardware.Camera
import android.os.Build

/**
 * Created by fosung on 2019/8/29.
 */
class CameraUtil {

    companion object {
        private fun checkCameraFacing(facing: Int): Boolean {
            if (getSdkVersion() < Build.VERSION_CODES.GINGERBREAD) {
                return false
            }
            val cameraCount = Camera.getNumberOfCameras()
            var info = Camera.CameraInfo()
            for (index in 0..cameraCount-1) {
                Camera.getCameraInfo(index, info)
                if (facing == info.facing) {
                    return true
                }

            }
            return false
        }

        fun hasBackFacingCamera(): Boolean {
            val CAMERA_FACING_BACK = 0
            return checkCameraFacing(CAMERA_FACING_BACK)

        }

        fun hasFrontFacingCamera(): Boolean {
            val CAMERA_FACING_BACK = 1
            return checkCameraFacing(CAMERA_FACING_BACK)
        }

        fun getSdkVersion(): Int {
            return android.os.Build.VERSION.SDK_INT
        }
    }
}

