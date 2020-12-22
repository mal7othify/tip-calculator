package com.example.tiptime.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.tiptime.R
import com.example.tiptime.util.calculateTip

@Composable
fun Content() {
    val modifier = Modifier.padding(8.dp)
    val radioOptions = listOf("Amazing (20%)", "Good (18%)", "OK (15%)")
    val textField = remember { mutableStateOf(TextFieldValue("")) }
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val checkedState = remember { mutableStateOf(true) }
    val tip = remember { mutableStateOf(0.00) }
    val tipPercentage = when (selectedOption) {
        radioOptions[0] -> 0.20
        radioOptions[1] -> 0.18
        else -> 0.15
    }

    Column(
        modifier = Modifier.padding(16.dp).fillMaxSize().verticalScroll(rememberScrollState())
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_store_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )

            OutlinedTextField(
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text("Cost of Service") },
                placeholder = { Text("Cost of Service") },
                value = textField.value,
                onValueChange = {
                    textField.value = it
                    tip.value =
                        calculateTip(textField.value.text, tipPercentage, checkedState.value)
                },
                modifier = modifier.padding(start = 16.dp)
            )
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_room_service_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text("How was the service?", modifier.padding(start = 16.dp))
        }
        radioOptions.forEach { text ->
            Row(
                modifier.fillMaxWidth()
                    .selectable(
                        selected = (text == selectedOption),
                        onClick = {
                            onOptionSelected(text)
                            tip.value = calculateTip(
                                textField.value.text,
                                tipPercentage,
                                checkedState.value
                            )
                        }
                    )
            ) {
                RadioButton(
                    selected = (text == selectedOption),
                    onClick = {
                        onOptionSelected(text)
                        tip.value =
                            calculateTip(
                                textField.value.text,
                                tipPercentage,
                                checkedState.value
                            )
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
                contentDescription = null,
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_baseline_call_made_24),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
            Text(" Round up tip?", modifier = modifier.padding(start = 16.dp))
            Spacer(Modifier.requiredWidth(150.dp))
            Switch(
                colors = SwitchDefaults.colors(MaterialTheme.colors.secondary),
                checked = checkedState.value,
                onCheckedChange = {
                    checkedState.value = it
                    tip.value =
                        calculateTip(textField.value.text, tipPercentage, checkedState.value)
                }
            )
        }
        Divider(modifier = Modifier.padding(8.dp))
        Text("Tip Amount: $${tip.value}", modifier = modifier.align(Alignment.End))
    }
}
