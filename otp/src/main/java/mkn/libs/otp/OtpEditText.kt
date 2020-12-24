package mkn.libs.otp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.text.*
import android.util.AttributeSet
import android.util.TypedValue
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat

class OTPEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : AppCompatEditText(context), TextWatcher {
    private var mWidth: Int = 0

    var otpCount = 6
        set(value) {
            field = value
            filters = arrayOf(InputFilter.LengthFilter(value))
        }

    private var paddingPx: Int = 0
    private var internalStopFormatFlag: Boolean = false
    private val spaceBetweenCharactersEm = 2
    private val emSize: Float
    private var lineWidth = 0f
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var activeColor = Color.parseColor("#FFFFFF")
    private var normalColor = Color.parseColor("#66FFFFFF")
    private var successColor = Color.parseColor("#1AE8B8")
    private val lineRect = Array(otpCount) { RectF() }


    init {

        setTextColor(Color.WHITE)
        inputType = InputType.TYPE_CLASS_NUMBER
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 26f)
        filters = arrayOf(InputFilter.LengthFilter(otpCount))
        typeface = ResourcesCompat.getFont(context, R.font.irsansbold_monospace)

        addTextChangedListener(this)
        emSize = paint.measureText("1")
        lineWidth = emSize * 2f

        paddingPx = (spaceBetweenCharactersEm * emSize).toInt()
        setPadding(
            paddingPx,
            0,
            paddingPx,
            0
        )

        background = null
        linePaint.apply {
            style = Paint.Style.FILL
        }
    }

    override fun onSelectionChanged(selStart: Int, selEnd: Int) {

        val text: CharSequence? = text
        if (text != null) {
            if (selStart != text.length || selEnd != text.length) {
                setSelection(text.length, text.length)
                return
            }
        }

        super.onSelectionChanged(selStart, selEnd)

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val charSpaceWidth = emSize * spaceBetweenCharactersEm
        val allSpacesWidth = (otpCount - 1) * charSpaceWidth
        val allCharactersWidth = otpCount * emSize
        val horizontalPadding = paddingLeft + paddingRight

        mWidth = allCharactersWidth.toInt() + allSpacesWidth.toInt() + horizontalPadding
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(mWidth, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(56.dp(), MeasureSpec.EXACTLY)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        lineRect[0].set(
            (paddingLeft + emSize.div(2) - lineWidth.div(2)),
            height - 10.dpf(),
            paddingLeft + emSize.div(2) - lineWidth.div(2) + lineWidth,
            height - 7.dpf()
        )

        lineRect.forEachIndexed { index, rectF ->
            if (index > 0) {
                rectF.set(
                    lineRect[index - 1].right - lineWidth.div(2) - emSize.div(2) + emSize.plus(
                        paddingPx
                    ) + emSize.div(2) - lineWidth.div(2),
                    height - 10.dpf(),
                    lineRect[index - 1].right - lineWidth.div(2) - emSize.div(2) + emSize.plus(
                        paddingPx
                    ) + emSize.div(2) - lineWidth.div(2) + lineWidth,
                    height - 7.dpf()
                )
            }
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        lineRect.forEachIndexed { index, rectF ->
            if (index <= selectionStart) {
                linePaint.color = activeColor

            } else {
                linePaint.color = normalColor
            }

            if (selectionStart == otpCount) {
                linePaint.color = successColor
            }

            canvas?.drawRoundRect(rectF, 6.dpf(), 6.dpf(), linePaint)
        }

    }

    override fun afterTextChanged(s: Editable?) {
        if (internalStopFormatFlag || s == null) {
            return
        }

        internalStopFormatFlag = true
        formatText(s, paddingPx)
        internalStopFormatFlag = false
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
    }

    private fun formatText(
        ccNumber: Editable,
        paddingPx: Int
    ) {
        val textLength = ccNumber.length
        // first remove any previous span
        val spans = ccNumber.getSpans(0, ccNumber.length, RightPaddingSpan::class.java)
        for (i in spans.indices) {
            ccNumber.removeSpan(spans[i])
        }

        for (i in 0 until textLength) {
            val padding = if (i != otpCount - 1) paddingPx else 0
            val marginSPan = RightPaddingSpan(
                padding
            )
            ccNumber.setSpan(marginSPan, i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }

    }
}
