package com.alfikri.rizky.core.presentation.utils

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author Rizky Alfikri Rachmat (rizkyalfikri@gmail.com)
 * @version DateFormat, v 0.1 1/2/2023 8:43 PM by Rizky Alfikri Rachmat
 */
object DateFormat {
    fun formatDateWithFormatTimeAgo(dateStr: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ROOT)
        val date = inputFormat.parse(dateStr)
        return if (date != null) {
            return DateUtils.getRelativeTimeSpanString(
                date.time,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
            ).toString()
        } else dateStr
    }
}