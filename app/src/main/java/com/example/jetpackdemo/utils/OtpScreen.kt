package com.example.jetpackdemo.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpScreen(
    onOtpComplete: (String) -> Unit
) {

    /* ---------- state & helpers ---------- */
    val focusRequesters = remember { List(4) { FocusRequester() } }
    val digits          = remember { mutableStateListOf(*Array(4) { "" }) }

    fun allFilled() = digits.all { it.isNotEmpty() }

    /* ---------- UI ---------- */
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment     = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(32.dp)
    ) {
        repeat(4) { index ->
            val next = runCatching { focusRequesters[index + 1] }.getOrNull()
            val prev = runCatching { focusRequesters[index - 1] }.getOrNull()

            OutlinedTextField(
                value = digits[index],
                onValueChange = { new ->
                    /* keep only the last typed digit (if any) */
                    val digit = new.takeLast(1).filter { it.isDigit() }
                    if (digit.isNotEmpty()) {
                        digits[index] = digit
                        next?.requestFocus()                         // jump forward
                        if (allFilled()) onOtpComplete(digits.joinToString(""))
                    } else {
                        // user just deleted the only digit in this box
                        digits[index] = ""
                        prev?.requestFocus()                         // jump back
                    }
                },
                modifier = Modifier
                    .size(60.dp)
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { e ->
                        if (e.type == KeyEventType.KeyDown && e.key == Key.Backspace) {
                            if (digits[index].isEmpty()) {           // already empty → go back
                                prev?.requestFocus()
                                if (prev != null) digits[index - 1] = ""
                            }
                            false                                     // let onValueChange handle the rest
                        } else false
                    },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize  = 20.sp,
                    fontWeight = FontWeight.Bold,
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction    = if (index == 4 - 1) ImeAction.Done else ImeAction.Next
                ),
                shape  = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor   = Color.Blue,
                    unfocusedBorderColor = Color.Gray,
                    cursorColor          = Color.Blue
                )
            )
        }
    }

    /* focus the first box when the composable appears */
    LaunchedEffect(Unit) { focusRequesters.first().requestFocus() }
}