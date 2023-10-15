package com.catnip.retrofitexample.util

import java.text.NumberFormat
import java.util.Locale

fun Long.toIdrCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    numberFormat.maximumFractionDigits = 0
    val formatted = numberFormat.format(this)
    val subStr = formatted.substring(2, formatted.length)
    return "Rp " + subStr.replace(",", ".")
}
fun Long.toUsCurrency(): String {
    val numberFormat = NumberFormat.getCurrencyInstance(Locale(Locale.US.toString(), Locale.US.toString()))
    numberFormat.maximumFractionDigits = 0
    val formatted = numberFormat.format(this)
    val subStr = formatted.substring(2, formatted.length)
    return "$ " + subStr.replace(",", ".")
}