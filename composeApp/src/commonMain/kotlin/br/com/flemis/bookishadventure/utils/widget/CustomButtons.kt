package br.com.flemis.bookishadventure.utils.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bookishadventure.composeapp.generated.resources.DMSans_Bold
import bookishadventure.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun CustomElevatedButton(
    placeholder: String,
    textStyle: TextStyle = TextStyle(
        fontSize = 13.sp, fontFamily = FontFamily(
            Font(Res.font.DMSans_Bold)
        ), color = Color.White
    ),
    onClick: () -> Unit,
    modifier: Modifier = Modifier.fillMaxWidth().height(50.dp),
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    ElevatedButton(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = Color(0xff3449A7), contentColor = Color.White
        ),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,


            ) {
            if (trailingIcon != null) {
                Spacer(Modifier.width(30.dp))
            } else {
                Spacer(
                    Modifier.width(IntrinsicSize.Min)
                )
            }
            Text(placeholder, softWrap = true, style = textStyle)
            if (trailingIcon != null) {
                trailingIcon()
            } else {
                Spacer(Modifier.width(IntrinsicSize.Min))
            }

        }
    }
}

@Composable
fun CustomOutlinedButton(
    placeholder: String,
    textStyle: TextStyle,
    onClick: () -> Unit,
    containerColor: Color = Color.Transparent,
    contentColor: Color = Color(0xffA3A3A0),
    disabledContentColor: Color = Color.Unspecified,
    disabledContainerColor: Color = Color.Unspecified,
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, contentColor),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = containerColor,
            contentColor = contentColor,
            disabledContentColor = disabledContentColor,
            disabledContainerColor = disabledContainerColor

        )
    ) {
        Text(placeholder, softWrap = true, style = textStyle)
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RoundedButton(
    cardColor: Color = Color(0xff1E1E1E),
    color: Color,
    icon: ImageVector,
    iconColor: Color,
    onClick: () -> Unit,
    shape: RoundedCornerShape,
    text: String,
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = cardColor,
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(horizontal = 15.dp),
        onClick = onClick
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxSize().padding(start = 15.dp)
        ) {

            Box(
                Modifier.height(30.dp).width(30.dp).background(color = color, shape = shape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,

                    contentDescription = null,
                    modifier = Modifier.size(18.dp),
                    // tint = Color(0xffA3A0A0),
                    tint = Color.White,
                )
            }
            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth().padding(start = 15.dp),
                text = text,
                maxLines = 1,
                style = MaterialTheme.typography.titleMedium.copy(textIndent = TextIndent(10.sp))

            )
        }
    }
}