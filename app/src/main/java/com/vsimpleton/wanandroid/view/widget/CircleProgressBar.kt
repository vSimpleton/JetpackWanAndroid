package com.vsimpleton.wanandroid.view.widget

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.vsimpleton.wanandroid.R
import com.vsimpleton.wanandroid.utils.dp2px
import com.vsimpleton.wanandroid.utils.sp2px
import kotlin.math.atan
import kotlin.math.min

/**
 * NAME: vSimpleton
 * DATE: 2021/5/2
 * DESC: 圆形进度条
 */

class CircleProgressBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_MAX = 100
        private const val DEFAULT_PROGRESS = 0
        private const val DEFAULT_START_ANGLE = -90f
        private const val DEFAULT_REVERSE = false
        private const val DEFAULT_ROUND_CAP = true
        private val DEFAULT_RING_WIDTH = dp2px(3f).toFloat()
        private val DEFAULT_RING_COLOR = Color.parseColor("#5889ED")
        private val DEFAULT_RING_BACKGROUND_COLOR = Color.parseColor("#FFFFFF")
        private const val DEFAULT_INNER_BACKGROUND_COLOR = Color.TRANSPARENT
        private const val DEFAULT_DESC_TEXT = ""
        private val DEFAULT_DESC_TEXT_SIZE = sp2px(12f).toFloat()
        private val DEFAULT_DESC_TEXT_COLOR = Color.parseColor("#5889ED")
        private const val DEFAULT_PERCENT_TEXT = ""
        private val DEFAULT_PERCENT_TEXT_SIZE = sp2px(20f).toFloat()
        private val DEFAULT_PERCENT_TEXT_COLOR = Color.parseColor("#5889ED")
    }

    private var max = DEFAULT_MAX
    var progress = DEFAULT_PROGRESS
        get() = field
        set(value) {
            field = correctProgress(value)
            invalidate()
        }
    private var startAngle = DEFAULT_START_ANGLE //开始绘制的角度
    private var reverse = DEFAULT_REVERSE //进度条是否逆时针转动，默认为false
    private var roundCap = DEFAULT_ROUND_CAP //是否设置画笔帽为Paint.Cap.ROUND，默认为true
    private var ringWidth = DEFAULT_RING_WIDTH
    private var ringColor = DEFAULT_RING_COLOR
    private var rindColorArray: IntArray? = null
    private var ringBackgroundColor = DEFAULT_RING_BACKGROUND_COLOR
    private var innerBackgroundColor = DEFAULT_INNER_BACKGROUND_COLOR
    private var descText = DEFAULT_DESC_TEXT
    private var descTextSize = DEFAULT_DESC_TEXT_SIZE
    private var descTextColor = DEFAULT_DESC_TEXT_COLOR
    private var percentText = DEFAULT_PERCENT_TEXT
    private var percentTextSize = DEFAULT_PERCENT_TEXT_SIZE
    private var percentTextColor = DEFAULT_PERCENT_TEXT_COLOR

    private lateinit var mArcPaint: Paint        //绘制圆环
    private lateinit var mBgPaint: Paint         //绘制内部圆圈
    private lateinit var mTextPaint: Paint       //绘制文字
    private lateinit var mRectF: RectF           //圆环对应的RectF
    private lateinit var mShader: SweepGradient  //实现圆环渐变效果，如果有在代码中设置rindColorArray的话

    private var mWidth: Int = 0
    private var mHeight: Int = 0

    init {
        attrs?.let {
            parseAttribute(getContext(), it)
        }
        initPaint()
    }

    @SuppressLint("Recycle")
    private fun parseAttribute(context: Context, attrs: AttributeSet) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar)
        max = ta.getInt(R.styleable.CircleProgressBar_max, DEFAULT_MAX)
        if (max <= 0) {
            max = DEFAULT_MAX
        }
        startAngle = ta.getFloat(R.styleable.CircleProgressBar_startAngle, DEFAULT_START_ANGLE)
        reverse = ta.getBoolean(R.styleable.CircleProgressBar_reverse, DEFAULT_REVERSE)
        roundCap = ta.getBoolean(R.styleable.CircleProgressBar_roundCap, DEFAULT_ROUND_CAP)
        progress = ta.getInt(R.styleable.CircleProgressBar_progress, DEFAULT_PROGRESS)
        ringWidth = ta.getDimension(R.styleable.CircleProgressBar_ringWidth, DEFAULT_RING_WIDTH)
        ringColor = ta.getColor(R.styleable.CircleProgressBar_ringColor, DEFAULT_RING_COLOR)
        ringBackgroundColor = ta.getColor(
            R.styleable.CircleProgressBar_ringBackgroundColor,
            DEFAULT_RING_BACKGROUND_COLOR
        )
        innerBackgroundColor = ta.getColor(
            R.styleable.CircleProgressBar_innerBackgroundColor,
            DEFAULT_INNER_BACKGROUND_COLOR
        )
        descText = ta.getString(R.styleable.CircleProgressBar_descText) ?: DEFAULT_DESC_TEXT
        descTextSize =
            ta.getDimension(R.styleable.CircleProgressBar_descTextSize, DEFAULT_DESC_TEXT_SIZE)
        descTextColor =
            ta.getColor(R.styleable.CircleProgressBar_descTextColor, DEFAULT_DESC_TEXT_COLOR)
        percentText =
            ta.getString(R.styleable.CircleProgressBar_percentText) ?: DEFAULT_PERCENT_TEXT
        percentTextSize = ta.getDimension(
            R.styleable.CircleProgressBar_percentTextSize,
            DEFAULT_PERCENT_TEXT_SIZE
        )
        percentTextColor =
            ta.getColor(R.styleable.CircleProgressBar_percentTextColor, DEFAULT_PERCENT_TEXT_COLOR)

        ta.recycle()
    }

    private fun initPaint() {
        mArcPaint = Paint()
        mArcPaint.isAntiAlias = true

        mBgPaint = Paint()
        with(mBgPaint) {
            isAntiAlias = true
            color = innerBackgroundColor
        }

        mTextPaint = Paint()
        with(mTextPaint) {
            isAntiAlias = true
            textAlign = Paint.Align.CENTER
        }
    }

    fun startAnim(progress: Int, duration: Long = 2000) {
        val percent = correctProgress(progress)
        val animator = ValueAnimator.ofInt(0, percent)
        animator.duration = duration
        animator.addUpdateListener {
            this.progress = it.animatedValue as Int
        }
        animator.start()
    }

    fun correctProgress(progress: Int): Int {
        var mProgress = progress
        if (mProgress > max) {
            if (mProgress % max == 0) {
                mProgress = max
            } else {
                mProgress %= max
            }
        }
        return mProgress
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 重写该方法支持wrap_content
        setMeasuredDimension(measure(widthMeasureSpec), measure(heightMeasureSpec))
    }

    private fun measure(measureSpec: Int): Int {
        var result = 0
        val specSize = MeasureSpec.getSize(measureSpec)
        val specMode = MeasureSpec.getMode(measureSpec)

        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = dp2px(90f)
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }

        return result
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = measuredWidth
        mHeight = measuredHeight
        mRectF = RectF(
            ringWidth / 2.0f, ringWidth / 2.0f,
            mWidth - ringWidth / 2.0f, mHeight - ringWidth / 2.0f
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.let {
            drawBackground(it)
            drawArc(it)
            drawText(it)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawCircle(mRectF.centerX(), mRectF.centerY(), mWidth / 2.0f, mBgPaint)
    }

    private fun drawArc(canvas: Canvas) {
        mArcPaint.apply {
            color = ringBackgroundColor
            style = Paint.Style.STROKE
            strokeWidth = ringWidth
            shader = null
        }

        canvas.drawArc(mRectF, 0f, 360f, false, mArcPaint)

        mArcPaint.apply {
            color = ringColor
            //strokeCap取值：ROUND(圆形)、SQUARE(方形)和BUTT，默认是BUTT
            if (roundCap) {
                strokeCap = Paint.Cap.ROUND
            }
        }

        //绘制圆环
        rindColorArray?.let {
            //绘制渐变效果
            mShader = SweepGradient(mRectF.centerX(), mRectF.centerY(), rindColorArray!!, null)
            val matrix = Matrix()
            matrix.setRotate(
                if (roundCap) calculateOffset() else startAngle,
                mRectF.centerX(),
                mRectF.centerY()
            )
            mShader.setLocalMatrix(matrix)
            mArcPaint.setShader(mShader)
        }

        var sweepAngle = progress.toFloat() / max * 360f
        if (reverse) {
            sweepAngle = -sweepAngle  //逆时针滚动
        }
        canvas.drawArc(mRectF, startAngle, sweepAngle, false, mArcPaint)
    }

    // 设置渐变效果时让SweepGradient偏移一定角度来保证圆角凸出部分和圆环进度条颜色一样
    private fun calculateOffset(): Float {
        //计算strokeCap为Paint.Cap.ROUND时圆角凸出部分相当于整个圆环占的比例，半圆的直径 = 线的宽度
        val roundPercent =
            (atan(ringWidth / 2.0 / (mWidth / 2.0 - ringWidth / 2.0)) / (2 * Math.PI)).toFloat()
        val currentPercent = progress / max.toFloat()
        return if (currentPercent + roundPercent >= 1.0f) {
            startAngle
        } else if (currentPercent + 2 * roundPercent >= 1.0f) {
            if (reverse) {
                startAngle + (1 - currentPercent - roundPercent) * 360f
            } else {
                startAngle - (1 - currentPercent - roundPercent) * 360f
            }
        } else {
            if (reverse) {
                startAngle + roundPercent * 360f
            } else {
                startAngle - roundPercent * 360f
            }
        }
    }


    private fun drawText(canvas: Canvas) {
        with(mTextPaint) {
            color = descTextColor
            textSize = descTextSize
        }

        //绘制描述文本
        canvas.drawText(descText, mRectF.centerX(), mHeight * 0.42f, mTextPaint)

        with(mTextPaint) {
            color = percentTextColor
            textSize = percentTextSize
        }

        //绘制进度文本
        canvas.drawText(percentText, mRectF.centerX(), mHeight * 0.69f, mTextPaint)

    }

}