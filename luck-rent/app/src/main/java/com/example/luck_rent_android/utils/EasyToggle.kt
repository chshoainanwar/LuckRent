package com.example.luck_rent_android.utils

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.os.Bundle
import android.os.Parcelable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import com.example.luck_rent_android.R


/**
 * ToggleView, the default settings acts like ios toggle button style.
 *
 * Created by huzn on 2017/7/17.
 */
class EasyToggle : View {

    companion object {
        private const val KEY_CURRENT_STATE = "key_state"
        private const val KEY_SUPER_STATE = "key_super_state"
    }

    private var length = 0
    private var radius = 0
    private var range = 0 // default 4 times of radius
    private var bgOffColor = 0
    private var bgOnColor = 0
    private var bgStrokeWidth = 0
    private var bgTopColor = 0
    private var buttonColor = 0
    private var buttonStrokeColor = 0
    private var buttonStrokeWidth = 0
    private var shadowOffsetY = 0

    private var cx = 0f
    private var cy = 0f
    private var bgRectM = RectF()
    private var bgRectL = RectF()
    private var bgRectR = RectF()
    private var bgTopRectM = RectF()
    private var bgTopRectL = RectF()
    private var bgTopRectR = RectF()
    private var buttonRectL = RectF()
    private var buttonRectR = RectF()
    private var buttonExtensionLength = 0f

    private var bgPaint = Paint()
    private var bgPath = Path()
    private var bgTopPaint = Paint()
    private var bgTopPath = Path()
    private var buttonPaint = Paint()
    private var buttonStrokePaint = Paint()
    private var buttonPath = Path()
    private var shadowPaint = Paint()

    private var bgColorAnimator: ValueAnimator? = null
    private var bgColorFraction = 1f
    private var bgTopExtensionAnimator: ValueAnimator? = null
    private var bgTopExtensionValue = 1f
    private var buttonExtensionAnimator: ValueAnimator? = null
    private var buttonExtensionValue = 0f
    private var buttonMoveAnimator: ValueAnimator? = null
    private var buttonMoveValue = 0f
    private val ANIMATION_DURATION = 300L

    val STATE_OFF = 0
    val STATE_ON = 1
    private var currentState = STATE_OFF
    private var lastState = STATE_OFF
    private var hasToggled = false
    private var isTouchOutOfRange = false
    private var hasBeenTouchOutOfRange = false

    private var downX = 0f
    private var downY = 0f

    private var onToggledListener: ((Boolean) -> Unit)? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        context?.theme?.obtainStyledAttributes(attrs, R.styleable.EasyToggle, defStyleAttr, 0).let {
            length = it?.getDimensionPixelSize(R.styleable.EasyToggle_etLength, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 22.5f, context?.resources?.displayMetrics).toInt())!!
            radius = it.getDimensionPixelSize(R.styleable.EasyToggle_etRadius, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 15f, context?.resources?.displayMetrics).toInt())
            range = it.getDimensionPixelSize(R.styleable.EasyToggle_etRange, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 60f, context?.resources?.displayMetrics).toInt())
            bgOffColor = it.getColor(R.styleable.EasyToggle_etBgOffColor, Color.parseColor("#FFE4E4E4"))
            bgOnColor = it.getColor(R.styleable.EasyToggle_etBgOnColor, Color.parseColor("#FF4DD863"))
            bgStrokeWidth = it.getDimensionPixelSize(R.styleable.EasyToggle_etBgStrokeWidth, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 2f, context?.resources?.displayMetrics).toInt())
            bgTopColor = it.getColor(R.styleable.EasyToggle_etBgTopColor, Color.WHITE)
            buttonColor = it.getColor(R.styleable.EasyToggle_etButtonColor, Color.WHITE)
            buttonStrokeColor = it.getColor(R.styleable.EasyToggle_etButtonStrokeColor, Color.parseColor("#FFE4E4E4"))
            buttonStrokeWidth = it.getDimensionPixelSize(R.styleable.EasyToggle_etButtonStrokeWidth, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 0.1f, context?.resources?.displayMetrics).toInt())
            shadowOffsetY = it.getDimensionPixelSize(R.styleable.EasyToggle_etShadowOffsetY, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, -1f, context?.resources?.displayMetrics).toInt())
            it.recycle()
        }

        buttonStrokeWidth =
                if (buttonStrokeWidth / 2f > radius - bgStrokeWidth / 2f)
                    (radius - bgStrokeWidth / 2) * 2
                else
                    buttonStrokeWidth

        if (shadowOffsetY < 0)
            shadowOffsetY = bgStrokeWidth * 2 // default shadow offset y

        with(bgPaint) {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE
            color = bgOffColor
            strokeWidth = bgStrokeWidth.toFloat()
        }

        with(bgTopPaint) {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = bgTopColor
        }

        with(buttonPaint) {
            isAntiAlias = true
            style = Paint.Style.FILL
            color = buttonColor
        }

        with(shadowPaint) {
            isAntiAlias = true
            style = Paint.Style.FILL_AND_STROKE // this looks like wrong in xml preview, actually it is correct
            color = Color.parseColor("#11333333")
            strokeWidth = buttonStrokeWidth.toFloat()
        }

        with(buttonStrokePaint) {
            isAntiAlias = true
            style = Paint.Style.STROKE
            color = buttonStrokeColor
            strokeWidth = buttonStrokeWidth.toFloat()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var widthSize: Int = MeasureSpec.getSize(widthMeasureSpec)
        var mode: Int? = MeasureSpec.getMode(widthMeasureSpec)
        if (mode != MeasureSpec.EXACTLY) {
            widthSize = paddingLeft + paddingRight + radius * 2 + length

            if (bgStrokeWidth > buttonStrokeWidth / 2f)
                widthSize += bgStrokeWidth
            else
                widthSize += buttonStrokeWidth - bgStrokeWidth
        }

        var heightSize: Int = MeasureSpec.getSize(heightMeasureSpec)
        mode = MeasureSpec.getMode(heightMeasureSpec)
        if (mode != MeasureSpec.EXACTLY) {
            heightSize = paddingTop + paddingBottom + radius * 2

            if (bgStrokeWidth > buttonStrokeWidth / 2f + Math.abs(shadowOffsetY))
                heightSize += bgStrokeWidth
            else
                heightSize += buttonStrokeWidth - bgStrokeWidth + Math.abs(shadowOffsetY) * 2
        }

        val halfStrokeWidth = bgStrokeWidth / 2f

        cx = widthSize / 2f
        cy = heightSize / 2f

        // bg rect
        with(bgRectM) {
            left = cx - length / 2f
            top = cy - radius
            right = cx + length / 2f
            bottom = cy + radius
        }
        with(bgRectL) {
            left = bgRectM.left - radius
            top = bgRectM.top
            right = bgRectM.left + radius
            bottom = bgRectM.bottom
        }
        with(bgRectR) {
            left = bgRectM.right - radius
            top = bgRectM.top
            right = bgRectM.right + radius
            bottom = bgRectM.bottom
        }

        // bg top rect
        with(bgTopRectM) {
            left = bgRectM.left + halfStrokeWidth
            top = bgRectM.top + halfStrokeWidth
            right = bgRectM.right - halfStrokeWidth
            bottom = bgRectM.bottom - halfStrokeWidth
        }
        with(bgTopRectL) {
            left = bgTopRectM.left - radius
            top = bgTopRectM.top
            right = bgTopRectM.left + radius
            bottom = bgTopRectM.bottom
        }
        with(bgTopRectR) {
            left = bgTopRectM.right - radius
            top = bgTopRectM.top
            right = bgTopRectM.right + radius
            bottom = bgTopRectM.bottom
        }

        // button rect
        with(buttonRectL) {
            top = bgRectM.top + halfStrokeWidth
            bottom = bgRectM.bottom - halfStrokeWidth
        }
        with(buttonRectR) {
            top = bgRectM.top + halfStrokeWidth
            bottom = bgRectM.bottom - halfStrokeWidth
        }

        buttonExtensionLength = radius / 2f

        setMeasuredDimension(widthSize, heightSize)
    }

    override fun onDraw(canvas: Canvas?) {
        val halfStrokeWidth = bgStrokeWidth / 2f
        val halfExtensionLength = buttonExtensionLength * buttonExtensionValue / 2f

        // bg
        with(bgPath) {
            reset()
            moveTo(bgRectM.left, bgRectM.top)
            lineTo(bgRectM.right, bgRectM.top)
            arcTo(bgRectR, -90f, 180f, false)
            lineTo(bgRectM.left, bgRectM.bottom)
            arcTo(bgRectL, 90f, 180f, false)
        }
        val startColor: Int
        val endColor: Int
        if (currentState == STATE_ON) {
            startColor = bgOffColor
            endColor = bgOnColor
        } else {
            startColor = bgOnColor
            endColor = bgOffColor
        }
        val currentColor = getCurrentColor(bgColorFraction, startColor, endColor)
        bgPaint.color = currentColor
        canvas?.drawPath(bgPath, bgPaint)

        // bg top
        with(bgTopPath) {
            reset()
            moveTo(bgTopRectM.left, bgTopRectM.top)
            lineTo(bgTopRectM.right, bgTopRectM.top)
            arcTo(bgTopRectR, -90f, 180f, false)
            lineTo(bgTopRectM.left, bgTopRectM.bottom)
            arcTo(bgTopRectL, 90f, 180f, false)
        }
        canvas?.let {
            it.save()
            it.scale(bgTopExtensionValue, bgTopExtensionValue, cx, cy)
            it.drawPath(bgTopPath, bgTopPaint)
            it.restore()
        }

        // button
        // extension
        var buttonCenterX: Float
        if (currentState == STATE_ON)
            buttonCenterX = bgRectM.right - halfExtensionLength
        else
            buttonCenterX = bgRectM.left + halfExtensionLength

        // move
        buttonCenterX += (length - halfExtensionLength * 2) * buttonMoveValue

        with(buttonRectL) {
            left = buttonCenterX - halfExtensionLength - radius + halfStrokeWidth
            right = buttonCenterX - halfExtensionLength + radius - halfStrokeWidth
        }
        with(buttonRectR) {
            left = buttonCenterX + halfExtensionLength - radius + halfStrokeWidth
            right = buttonCenterX + halfExtensionLength + radius - halfStrokeWidth
        }
        with(buttonPath) {
            reset()
            moveTo(buttonCenterX - halfExtensionLength, bgRectM.top + halfStrokeWidth)
            lineTo(buttonCenterX + halfExtensionLength, bgRectM.top + halfStrokeWidth)
            arcTo(buttonRectR, -90f, 180f, false)
            lineTo(buttonCenterX - halfExtensionLength, bgRectM.bottom - halfStrokeWidth)
            arcTo(buttonRectL, 90f, 180f, false)
        }

        canvas?.let {
            it.save()
            it.translate(0f, shadowOffsetY.toFloat()) // move the canvas to draw shadow
            it.drawPath(buttonPath, shadowPaint)
            it.restore()
            it.drawPath(buttonPath, buttonPaint)
            it.drawPath(buttonPath, buttonStrokePaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> doActionDown(event)
            MotionEvent.ACTION_MOVE -> doActionMove(event)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> doActionUp(event)
        }
        return true
    }

    private fun doActionDown(event: MotionEvent?) {
        downX = event?.rawX!!
        downY = event.rawY

        doButtonExtensionAnimation(0f, 1f)

        if (currentState == STATE_OFF)
            doBgTopExtensionAnimation(1f, 0f)
    }

    private fun doActionMove(event: MotionEvent?) {
        val nowX = event?.rawX!!
        val nowY = event.rawY
        val offsetX = nowX - downX
        val offsetY = nowY - downY

        isTouchOutOfRange = Math.abs(offsetY) > range

        if (currentState == STATE_ON && offsetX < -length) {
            downX = nowX
            toggle()

            if (isTouchOutOfRange) {
                doBgTopExtensionAnimation(0f, 1f)
            } else if (hasBeenTouchOutOfRange) {
                hasBeenTouchOutOfRange = false
                doButtonExtensionAnimation(0f, 1f)
            }

        } else if (currentState == STATE_OFF && offsetX > length) {
            downX = nowX
            toggle()

            if (isTouchOutOfRange) {
                doBgTopExtensionAnimation(1f, 0f)
            } else if (hasBeenTouchOutOfRange) {
                hasBeenTouchOutOfRange = false
                doButtonExtensionAnimation(0f, 1f)
                doBgTopExtensionAnimation(1f, 0f)
            }
        }

        if (!hasBeenTouchOutOfRange && Math.abs(offsetY) > range) {
            hasBeenTouchOutOfRange = true
            doButtonExtensionAnimation(1f, 0f)
            if (!hasToggled)
                toggle()
            if (currentState == STATE_OFF)
                doBgTopExtensionAnimation(0f, 1f)
        }
    }

    private fun doActionUp(event: MotionEvent?) {
        if (!hasBeenTouchOutOfRange)
            doButtonExtensionAnimation(1f, 0f)

        if (!hasToggled && lastState == currentState)
            toggle()

        if (!hasBeenTouchOutOfRange && currentState == STATE_OFF)
            doBgTopExtensionAnimation(0f, 1f)

        reset()
    }

    private fun reset() {
        lastState = currentState
        hasToggled = false
        buttonMoveValue = 0f
        hasBeenTouchOutOfRange = false
    }

    private fun toggle() {
        hasToggled = true

        val startMove: Float
        val endMove: Float
        if (currentState == STATE_ON) {
            currentState = STATE_OFF
            startMove = 1f
            endMove = 0f
        } else {
            currentState = STATE_ON
            startMove = -1f
            endMove = 0f
        }

        buttonMoveAnimator?.end()
        buttonMoveAnimator = ValueAnimator.ofFloat(startMove, endMove).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                buttonMoveValue = it.animatedValue as Float
                invalidate()
            }
            start()
        }

        bgColorAnimator?.end()
        bgColorAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                bgColorFraction = it.animatedFraction
                invalidate()
            }
            start()
        }

        onToggledListener?.invoke(currentState == STATE_ON)
    }

    private fun toggleImmediately() {
        bgColorFraction = 1f
        bgTopExtensionValue = if (currentState == STATE_OFF) 1f else 0f
        buttonExtensionValue = 0f
        buttonMoveValue = 0f
    }
    fun toggleImmediately(state:Int) {
        currentState = state
        bgColorFraction = 1f
        bgTopExtensionValue = if (currentState == STATE_OFF) 1f else 0f
        buttonExtensionValue = 0f
        buttonMoveValue = 0f
    }

    private fun doButtonExtensionAnimation(start: Float, end: Float) {
        buttonExtensionAnimator?.end()
        buttonExtensionAnimator = ValueAnimator.ofFloat(start, end).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                buttonExtensionValue = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun doBgTopExtensionAnimation(start: Float, end: Float) {
        bgTopExtensionAnimator?.end()
        bgTopExtensionAnimator = ValueAnimator.ofFloat(start, end).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                bgTopExtensionValue = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    private fun getCurrentColor(fraction: Float, startColor: Int, endColor: Int): Int {
        val redCurrent: Int
        val blueCurrent: Int
        val greenCurrent: Int
        val alphaCurrent: Int

        val redStart = Color.red(startColor)
        val blueStart = Color.blue(startColor)
        val greenStart = Color.green(startColor)
        val alphaStart = Color.alpha(startColor)

        val redEnd = Color.red(endColor)
        val blueEnd = Color.blue(endColor)
        val greenEnd = Color.green(endColor)
        val alphaEnd = Color.alpha(endColor)

        val redDifference = redEnd - redStart
        val blueDifference = blueEnd - blueStart
        val greenDifference = greenEnd - greenStart
        val alphaDifference = alphaEnd - alphaStart

        redCurrent = (redStart + fraction * redDifference).toInt()
        blueCurrent = (blueStart + fraction * blueDifference).toInt()
        greenCurrent = (greenStart + fraction * greenDifference).toInt()
        alphaCurrent = (alphaStart + fraction * alphaDifference).toInt()

        return Color.argb(alphaCurrent, redCurrent, greenCurrent, blueCurrent)
    }

    /**
     * Set the listener of toggle, true: on, false: off
     */
    fun setOnToggledListener(l: (Boolean) -> Unit) {
        this.onToggledListener = l
    }

    override fun onSaveInstanceState(): Parcelable {
        return Bundle().apply {
            putInt(KEY_CURRENT_STATE, currentState)
            putParcelable(KEY_SUPER_STATE, super.onSaveInstanceState())
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is Bundle) {
            super.onRestoreInstanceState(state.getParcelable(KEY_SUPER_STATE))
            currentState = state.getInt(KEY_CURRENT_STATE)
            lastState = currentState
            toggleImmediately()
        }
    }
}