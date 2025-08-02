package br.com.flemis.bookishadventure.utils.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bookishadventure.composeapp.generated.resources.DMSans_Medium
import bookishadventure.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun LinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeHolder: String,
    textColor: Color,
    trailingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    passwordVisible: Boolean? = null,
    modifier: Modifier = Modifier.fillMaxWidth().height(60.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    maxLines: Int = 1,
    minLines: Int = 1,
    singleLine: Boolean = false,
    supportingText: @Composable (() -> Unit)? = null,
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeHolder,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.inversePrimary,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.DMSans_Medium))
                ),
            )
        },
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        modifier = modifier,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = if (passwordVisible != null && !passwordVisible) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        colors = TextFieldDefaults.colors(
            cursorColor = MaterialTheme.colorScheme.outlineVariant,
            focusedTextColor = textColor,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            focusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.outlineVariant,
            unfocusedTextColor = textColor
        ),
        supportingText = supportingText
    )
}

