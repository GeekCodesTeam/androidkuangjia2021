package com.fosung.lighthouse.vpndenglu.widget

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.fosung.lighthouse.vpndenglu.R


/**
 *@author: lhw
 *@date: 2021/7/23
 *@desc
 */
class SeeEditText(context: Context, attr: AttributeSet) : AppCompatEditText(context, attr) {

    private var isShow = false
    private var showDrawable: Drawable? = null
    private var hideDrawable: Drawable? = null

    init {
        showDrawable = ContextCompat.getDrawable(context, R.mipmap.my_pas_show)
        hideDrawable = ContextCompat.getDrawable(context, R.mipmap.my_see)

        /*设置图片的范围*/
        showDrawable!!.setBounds(0, 0, showDrawable!!.minimumWidth, showDrawable!!.minimumHeight)
        hideDrawable!!.setBounds(0, 0, hideDrawable!!.minimumWidth, hideDrawable!!.minimumHeight)

        /*设置EditText和删除按钮图片的间距*/
        compoundDrawablePadding = 10;
        typeface = Typeface.DEFAULT
        transformationMethod = PasswordTransformationMethod()
        /*设置是否显示删除按钮*/
        setHideRightClearDrawable(false)

    }

    private fun setHideRightClearDrawable(isVisible: Boolean) {

        if (isVisible) {
            this.setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                showDrawable,
                compoundDrawables[3]
            )
            transformationMethod = HideReturnsTransformationMethod.getInstance();
        } else {
            this.setCompoundDrawables(
                compoundDrawables[0],
                compoundDrawables[1],
                hideDrawable,
                compoundDrawables[3]
            )
            transformationMethod = PasswordTransformationMethod.getInstance();
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        /*判断手指按下的x坐标*/
        val x = event.x

        /*获取自定义EditText宽度*/
        val width = this@SeeEditText.width.toFloat()

        /*获取EditText右Padding值*/
        val totalPaddingRight = this@SeeEditText.totalPaddingRight.toFloat()

        /*判断手指按下的区域是否在删除按钮宽高范围内*/
        if (event.action == MotionEvent.ACTION_UP) {
            if (x > width - totalPaddingRight && x < width && event.y < this@SeeEditText.height) {
                isShow = !isShow
                setHideRightClearDrawable(isShow)

            }
        }
        return super.onTouchEvent(event)
    }
}