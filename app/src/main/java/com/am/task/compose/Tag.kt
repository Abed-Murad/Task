package com.am.task.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable

@Composable
fun Tag(
    text: @Composable () -> Unit,
) {

    Box{
        TextButton(
            onClick = {},
            colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.primaryContainer)),
            text = text
        )
    }
}