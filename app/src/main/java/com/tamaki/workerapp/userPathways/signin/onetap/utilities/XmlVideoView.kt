package com.tamaki.workerapp.userPathways.signin.onetap.utilities

import android.content.Context
import android.widget.VideoView

class FixedSizeVideoView(ctx: Context) : VideoView(ctx) {
    private var mVideoWidth = 0
    private var mVideoHeight = 0

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mVideoWidth > 0 && mVideoHeight > 0) {
            if (mVideoWidth * height > width * mVideoHeight) {
                height = width * mVideoHeight / mVideoWidth
            } else if (mVideoWidth * height < width * mVideoHeight) {
                width = height * mVideoWidth / mVideoHeight
            } else {
            }
        }
        setMeasuredDimension(width, height)
    }
}