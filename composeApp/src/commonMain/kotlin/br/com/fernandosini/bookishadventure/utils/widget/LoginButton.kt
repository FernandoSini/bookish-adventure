package br.com.fernandosini.bookishadventure.utils.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape

class LoginButton {


    @Composable
    fun LoginOutlinedButton(
        onClick: () -> Unit,
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        text: String,
        enabled: Boolean = true,
        elevation: ButtonElevation?,
        shape: Shape = MaterialTheme.shapes.small,
        border: BorderStroke?,
        colors: ButtonColors = ButtonDefaults.outlinedButtonColors(),
        contentPaddingValues: PaddingValues,
        content: @Composable() (RowScope.() -> Unit)
    ) {
        OutlinedButton(
            modifier = modifier,
            interactionSource = interactionSource,
            onClick = onClick,
            enabled = enabled,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPaddingValues,
            content = content
        )
    }

    @Composable
    fun LoginElevatedButton(
        onClick: () -> Unit,
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        text: String,
        enabled: Boolean = true,
        elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
        shape: Shape = MaterialTheme.shapes.small,
        border: BorderStroke?,
        colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
        contentPaddingValues: PaddingValues,
        content: @Composable() (RowScope.() -> Unit)
    ) {
   ElevatedButton(
            modifier = modifier,
            interactionSource = interactionSource,
            onClick = onClick,
            enabled = enabled,
            elevation = elevation,
            shape = shape,
            border = border,
            colors = colors,
            contentPadding = contentPaddingValues,
            content = content
        )
    }
}