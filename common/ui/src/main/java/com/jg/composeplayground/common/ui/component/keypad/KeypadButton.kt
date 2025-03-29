package com.jg.composeplayground.common.ui.component.keypad

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun RippleCharButton(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    bgColor: Color,
    rippleColor: Color,
    onPressed: () -> Unit,
    textStyle: TextStyle,
    value: Char
) {
    Card (
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = onPressed,
                interactionSource = remember{ MutableInteractionSource() },
                indication = ripple(color = rippleColor)
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier
                .height(48.dp)
                .fillMaxSize()
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value.toString(),
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun RippleTextButton(
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    bgColor: Color,
    rippleColor: Color,
    onPressed: () -> Unit,
    textStyle: TextStyle,
    value: String
) {
    Card (
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = onPressed,
                interactionSource = remember{ MutableInteractionSource() },
                indication = ripple(color = rippleColor)
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier
                .height(48.dp)
                .fillMaxSize()
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                style = textStyle,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun RippleImageButton(
    modifier: Modifier = Modifier,
    bgColor: Color,
    rippleColor: Color,
    onPressed: () -> Unit,
    imageId: Int
) {
    Card (
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                onClick = onPressed,
                interactionSource = remember{ MutableInteractionSource() },
                indication = ripple(color = rippleColor)
            ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier
                .height(48.dp)
                .fillMaxSize()
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painterResource(id = imageId),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
    }
}