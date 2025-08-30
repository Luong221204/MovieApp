package com.example.movieapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.movieapp.R

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
    val t6 :TextStyle = TextStyle(
        color = Color.White,
        fontSize = 32.sp,
        fontFamily = FontFamily.Cursive
    ),
    val t7 :TextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 12.sp
    ),
    val t8 :TextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 19.sp,
    ),
    val t9 :TextStyle = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontSize = 10.sp
    ),
    val t10 :TextStyle =TextStyle(
        color = Color.White,
        fontSize = 15.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
    ),
    val t11:TextStyle = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = FontFamily.SansSerif,
        color = Color.White.copy(alpha =0.8f),
    ),
    val t12 :TextStyle =  TextStyle(
        fontSize = 8.sp,
        color = Color.White
    ),
    val t13 :TextStyle =TextStyle(
        color = Color.White.copy(alpha = 0.8f),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp
    ),
    val t14 :TextStyle =TextStyle(
        fontSize = 11.sp,
        color = Color.White,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
    ),
    val t15 :TextStyle = TextStyle(
        fontSize = 8.sp,
        color = Color.White,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
    ),
    val t16 :TextStyle = TextStyle(
        fontSize = 11.sp,
        color = Color.White,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
    ),
    val t17 :TextStyle = TextStyle(
        fontSize = 9.sp,
        color = Color.White,
        fontWeight = FontWeight.SemiBold
    ),
    val t18 :TextStyle =TextStyle(color = Color.White.copy(alpha = 0.8f),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    val t19 :TextStyle = TextStyle(
        color = Purple,
        fontSize = 12.sp,
        fontFamily = FontFamily.SansSerif
    ),
    val t20 :TextStyle = TextStyle(
        color = Color.White,
        fontSize = 18.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
    ),
    val t21 :TextStyle = TextStyle(
        fontSize = 12.sp,
        color = Color.White,
        fontFamily = FontFamily.SansSerif
    ),
    val t22 :TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily.Monospace,
        fontSize = 15.sp,
    ),
    val t23 :TextStyle = TextStyle(
        color = Color.White,
        fontFamily = FontFamily.SansSerif,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 22.sp
    ),
    val t24 :TextStyle = TextStyle(
        color = Color.White.copy(alpha = 0.8f),
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    val t25 :TextStyle = TextStyle(
        color = Color.White,
        fontSize = 13.sp,
        fontFamily = FontFamily.SansSerif
    )


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
    var heightForTextField :Dp = 70.dp,
    var roundCornerForTextField: Dp = 20.dp,
    var borderWidthForTextField :Dp = 2.dp,
    var iconSize:Dp = 40.dp,
    var iconSize2:Dp = 20.dp,
    var bottomMenuIconSize:Dp = 20.dp,
    var floatingButtonSize:Dp=60.dp,

    var e1 :Dp = 8.dp
    )


data class RoundedCornerDimension(
    val r1: Dp = 1.dp,
    val r2 : Dp= 2.dp,
    val r3 : Dp= 3.dp,
    val r4: Dp = 4.dp,
    val r5: Dp = 5.dp,
    val r6 : Dp= 6.dp,
    val r7: Dp = 7.dp,
    val r8 : Dp= 8.dp,
    val r9: Dp = 9.dp,
    val r10: Dp = 10.dp,
    val r11: Dp = 11.dp,
    val r12: Dp = 12.dp,
    val r13: Dp = 13.dp,
    val r14 : Dp= 14.dp,
    val r15 : Dp= 15.dp,
    val r16 : Dp= 16.dp,
    val r17 : Dp= 17.dp,
    val r18: Dp = 18.dp,
    val r19: Dp = 19.dp,
    val r20: Dp = 20.dp,
    val r30: Dp = 30.dp,

    )
data class SpacerDimension(
    var spacer0: Dp = 4.dp,
    var spacer1 :Dp = 8.dp,
    var spacer2: Dp = 12.dp,
    var spacer3: Dp = 16.dp,
    var spacer4:Dp= 20.dp,
    val spacer5: Dp =24 .dp,
    val spacer6: Dp = 28.dp,
    val spacer7 : Dp = 32.dp,
    val spacer10 : Dp = 36.dp,
    val spacer11 : Dp = 40.dp,

    val spacer9 : Dp = 54.dp,

    val spacer8 :Dp = 64.dp
)

data class ButtonDimension(
    val d1 :Dp = 50.dp,
    val d2: Dp=100.dp,
    val d3 : Dp = 150.dp,
    val d4: Dp = 200.dp
)

data class PaddingDimension(
    var default:Dp = 0.dp,
    var padding0: Dp = 4.dp,
    var padding1 :Dp = 8.dp,
    var padding2: Dp = 12.dp,
    var padding3: Dp = 16.dp,
    var padding4: Dp= 20.dp,
    val padding5: Dp =24 .dp,
    val padding6: Dp = 28.dp,
    val padding7 : Dp = 32.dp,
    val padding8 : Dp = 36.dp,
    val padding9 : Dp = 40.dp,
    val padding10 : Dp = 44.dp,
    val padding11 : Dp = 48.dp,
    val padding12 :Dp = 64.dp
)

data class ViewDimension(
    var v0: Dp = 4.dp,
    var v1: Dp = 8.dp,
    var v2: Dp = 12.dp,
    var v3: Dp = 16.dp,
    var v4: Dp = 20.dp,
    val v5: Dp = 24.dp,
    val v6: Dp = 28.dp,
    val v7: Dp = 32.dp,
    val v8: Dp = 36.dp,
    val v9: Dp = 40.dp,
    val v10: Dp = 44.dp,
    val v11: Dp = 48.dp,
    val v12: Dp = 64.dp,
    val v14:Dp = 56.dp,
    val v15:Dp = 72.dp,

    val v13:Dp = 176.dp
)

data class BlockDimension(
    val b1 :Dp= 10.dp,
    val b2 :Dp= 20.dp,
    val b3 :Dp= 30.dp,
    val b4:Dp = 40.dp,
    val b5:Dp = 50.dp,
    val b6 :Dp= 60.dp,
    val b7:Dp = 70.dp,
    val b8:Dp = 80.dp,
    val b9:Dp = 90.dp,
    val b10 :Dp= 100.dp,
    val b12: Dp = 120.dp,
    val b14: Dp = 140.dp,

    val b15: Dp = 150.dp,
    val b17: Dp = 170.dp,

    val b18: Dp = 180.dp,
    val b19: Dp = 190.dp,

    val b21: Dp = 210.dp,
    val b20: Dp = 200.dp,
    val b25: Dp = 250.dp,
    val b24: Dp = 240.dp,
    val b38: Dp = 380.dp,

    val b30: Dp = 300.dp,
    val b60: Dp = 600.dp,
    val b40: Dp = 400.dp,
    val b42: Dp = 420.dp,
    val b23: Dp =230.dp

)

data class Alpha(
    val a1: Float = 0.1f,
    val a2: Float = 0.2f,
    val a3: Float = 0.3f,
    val a4: Float = 0.4f,
    val a5: Float = 0.5f,
    val a6: Float = 0.6f,
    val a7: Float = 0.7f,
    val a8: Float = 0.8f,
    val a9: Float = 0.9f,
    val a10: Float = 1.0f,

    )
data class IconDimension(
    var i0: Dp = 4.dp,
    var i1: Dp = 8.dp,
    var i2: Dp = 12.dp,
    var i3: Dp = 16.dp,
    var i4: Dp = 20.dp,
    val i5: Dp = 24.dp,
    val i6: Dp = 28.dp,
    val i7: Dp = 32.dp,
    val i8: Dp = 36.dp,
    val i9: Dp = 40.dp,
    val i10: Dp = 44.dp,
    val i11: Dp = 48.dp,
    val i12: Dp = 64.dp,

    )

data class ThinDimension(
    val t1:Dp = 0.5.dp,
    val t2 :Dp = 1.dp,
    val t3:Dp= 2.dp,
    val t4:Dp= 3.dp,

    )
val LocalThinDp = staticCompositionLocalOf { ThinDimension() }

val LocalViewDp = staticCompositionLocalOf { ViewDimension() }
val LocalBlockDp = staticCompositionLocalOf { BlockDimension() }
val LocalRoundedDp = staticCompositionLocalOf { RoundedCornerDimension() }
val LocalAlpha = staticCompositionLocalOf { Alpha() }
val LocalIconSizeDimension = staticCompositionLocalOf { IconDimension() }

val LocalPaddingDp = staticCompositionLocalOf { PaddingDimension() }
var LocalSpacerDp = staticCompositionLocalOf { SpacerDimension() }
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
    val spacerDimension:SpacerDimension
    @Composable
    get() = LocalSpacerDp.current
    val paddingDimension:PaddingDimension
        @Composable
        get() = LocalPaddingDp.current
    val viewDimension:ViewDimension
        @Composable
        get() = LocalViewDp.current
    val blockDimension:BlockDimension
        @Composable
        get() = LocalBlockDp.current
    val roundedCornerDimension:RoundedCornerDimension
        @Composable
        get() = LocalRoundedDp.current
    val alpha:Alpha
        @Composable
        get() = LocalAlpha.current
    val iconDimension:IconDimension
        @Composable
        get() = LocalIconSizeDimension.current
    val thinDimension:ThinDimension
        @Composable
        get() = LocalThinDp.current
}