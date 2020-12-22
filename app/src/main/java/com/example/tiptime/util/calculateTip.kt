package com.example.tiptime.util

import java.math.BigDecimal
import java.math.RoundingMode

fun calculateTip(servicePrice: String?, tipPercentage: Double, rounded: Boolean): Double {
    var tip = 0.00

    tip = if (servicePrice == "" && rounded) {
        kotlin.math.ceil(tip)
    } else if (servicePrice == "" && !rounded) {
        tip
    } else if (servicePrice != "" && rounded) {
        kotlin.math.ceil(servicePrice!!.toDouble() * tipPercentage)
    } else servicePrice!!.toDouble() * tipPercentage

    return BigDecimal(tip).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}
//43.14159265358979323
