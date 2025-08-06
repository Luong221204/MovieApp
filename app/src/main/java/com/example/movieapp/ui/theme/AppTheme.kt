package com.example.movieapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class AppTypoTheme(
    var buttonTitle: TextStyle = TextStyle(
        fontSize = 22.sp,
        color = Color.White, fontFamily = FontFamily.Serif
    ),
    var textFieldTitle :TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily.Serif
    ),
    var textRecommend :TextStyle = TextStyle(
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        color = Color.White
    ),

    var textFieldOutline :TextStyle =TextStyle(
        color = Color.White,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Serif
    ),
    var textStyleForSearch :TextStyle =TextStyle(
        color = Color.White,
        fontSize = 15.sp,
        fontFamily = FontFamily.SansSerif
    ),

)
data class ColorScheme(
    var linearGradientColorsForButton: List<Color> = listOf(Pink,Red),
    var linearGradientColorsForTextField: List<Color> = listOf(Pink,Gray),
    var contentColorForButton:Color =Color.White,
    var containerColorForButton:Color = Color.Transparent,
    var backGroundColor :Color = BlackBackground,
    var transparent :Color =Color.Transparent,
    var focusedBorderColor :Color = Color.Transparent,
    var unfocusedBorderColor :Color =Color.Transparent,
    var focusIndicatorColor :Color =Color.Transparent,
    var unfocusIndicatorColor :Color =Color.Transparent,
    var focusedPlaceHolderColor :Color =Color.White,
    var cursorColor :Color =Color.White,
    var focusedLabelColor  :Color =Color.White,
    var unfocusedLabelColor  :Color =Color.White,
    var floatingButtonBackgroundColor:Color= Black3,
    var iconTint :Color = Color.Red.copy(alpha = 0.8f),
    )
data class DimensionValue(
    var borderWidthForButton: Dp = 3.dp,
    var borderWidthForTag: Dp = 1.dp,

    var roundCornerForButton: Dp = 50.dp,
    var horizontalPadding:Dp = 32.dp,
    var verticalPadding:Dp = 16.dp,
    var spacer1 :Dp = 8.dp,
    var heightForTextField :Dp = 70.dp,
    var roundCornerForTextField: Dp = 20.dp,
    var borderWidthForTextField :Dp = 2.dp,
    var iconSize:Dp = 40.dp,
    var iconSize2:Dp = 20.dp,
    var bottomMenuIconSize:Dp = 20.dp,
    var floatingButtonSize:Dp=60.dp,
    )
var LocalAppTypo = staticCompositionLocalOf { AppTypoTheme() }
var LocalColorScheme = staticCompositionLocalOf { ColorScheme() }
var LocalDimension = staticCompositionLocalOf { DimensionValue() }
@Composable
fun MovieAppTheme(
    content:@Composable ()->Unit
){
    val dimensionValue = DimensionValue()
    val appTypoTheme = AppTypoTheme()
    val colorScheme = ColorScheme()
    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalAppTypo provides appTypoTheme,
        LocalDimension provides dimensionValue
        ) {
        content.invoke()
    }
}
object MovieAppTheme{
    val appTypoTheme : AppTypoTheme
        @Composable
        get() = LocalAppTypo.current
    val colorScheme : ColorScheme
        @Composable
        get() = LocalColorScheme.current
    val dimensionValue : DimensionValue
        @Composable
        get() = LocalDimension.current
}