package mkn.libs

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import mkn.libs.otp.OTPEditText
import mkn.libs.otp.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /*val otpEditText = OTPEditText(this).apply {

        }

        setContentView(FrameLayout(this).apply {
            setBackgroundColor(Color.parseColor("#13103E"))
            addView(otpEditText, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.CENTER
                leftMargin = 16.dp()
                rightMargin = 16.dp()
                bottomMargin = 64.dp()
            })
        })*/


    }
}

