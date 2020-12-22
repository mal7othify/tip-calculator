package com.example.tiptime

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.savedInstanceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.math.BigDecimal
import java.math.RoundingMode

@Composable
fun Screen() {
    Scaffold(
        topBar = {
            TopAppBar {
                Text(
                    "Tip Time",
                    modifier = Modifier.align(Alignment.CenterVertically).padding(8.dp),
                    fontSize = 20.sp
                )
            }
        }
    ) {
        Content()
    }
}

@Composable
fun Content() {
    val modifier = Modifier.padding(8.dp)
    val radioOptions = listOf("Amazing (20%)", "Good (18%)", "OK (15%)")
    var value by savedInstanceState(saver = TextFieldValue.Saver) { TextFieldValue() }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val checkedState = remember { mutableStateOf(true) }
    val tip = remember { mutableStateOf(0.00) }
    val tipPercentage = when (selectedOption) {
        radioOptions[0] -> 0.20
        radioOptions[1] -> 0.18
        else -> 0.15
    }

    ScrollableColumn(
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = vectorResource(id = R.drawable.ic_baseline_store_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            OutlinedTextField(
                label = { Text("Cost of Service") },
                placeholder = { Text("Cost of Service") },
                value = value,
                onValueChange = {
                    value = it
                    tip.value = calculateTip(value.text, tipPercentage, checkedState.value)
                },
                modifier = modifier
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = vectorResource(id = R.drawable.ic_baseline_room_service_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text("How was the service?", modifier)
        }
        radioOptions.forEach { text ->
            Row(
                modifier.fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            tip.value = calculateTip(value.text, tipPercentage, checkedState.value)
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                        tip.value = calculateTip(value.text, tipPercentage, checkedState.value)
                    }
                )
                Text(
                    text = text,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = vectorResource(id = R.drawable.ic_baseline_call_made_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(" Round up tip?")
            Spacer(modifier = Modifier.preferredWidth(175.dp))
            Switch(
                colors = SwitchDefaults.colors(MaterialTheme.colors.secondary),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    tip.value = calculateTip(value.text, tipPercentage, checkedState.value)
                }
            )
        }
        Divider(modifier = Modifier.padding(8.dp))
        Text("Tip Amount: $${tip.value}", modifier = modifier.align(Alignment.End))
    }
}

fun calculateTip(servicePrice: String?, tipPercentage: Double, rounded: Boolean): Double {
    var tip = 0.00

    tip = if (servicePrice == "" && rounded) {
        kotlin.math.ceil(tip)
    } else if (servicePrice != "" && rounded) {
        kotlin.math.ceil(servicePrice!!.toDouble() * tipPercentage)
    } else servicePrice!!.toDouble() * tipPercentage
    return BigDecimal(tip).setScale(2, RoundingMode.HALF_EVEN).toDouble()
}
//43.14159265358979323

@Preview("Screen Preview")
@Composable
fun ScreenPreview() {
    Screen()
}