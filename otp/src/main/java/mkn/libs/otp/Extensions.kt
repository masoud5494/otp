package mkn.libs.otp

import android.content.res.Resources
import kotlin.math.ceil
val Int.dp: Int
    get() = if (this == 0) 0 else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()

val Float.dp: Int
    get() = if (this == 0f) 0 else ceil((Resources.getSystem().displayMetrics.density * this).toDouble()).toInt()

val Int.dpf: Float
    get() = this.dp.toFloat()

val Float.dpf: Float
    get() = this.dp.toFloat()
    

