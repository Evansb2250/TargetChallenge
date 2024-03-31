package com.target.targetcasestudy.ui.screens.catalog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8

@Composable
fun PriceCard(
    modifier: Modifier = Modifier,
    regularPrice: String,
    specialPrice: String?,
    fulfillment: String,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = dpValue8,
                )
                .fillMaxWidth(),
        ) {
            //Price
            Text(
                modifier = Modifier.padding(horizontal = dpValue4),
                fontSize = TextUnit(21f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFAA0000),
                text = specialPrice ?: regularPrice
            )
            Text(
                fontSize = TextUnit(12f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF333333),
                text = "reg ${regularPrice}"
            )
        }
        Text(
            fontSize = TextUnit(14f, TextUnitType.Sp),
            modifier = Modifier.padding(horizontal = dpValue12),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Normal,
            color = Color(0xFF666666),
            text = fulfillment,
            textAlign = TextAlign.Left,
        )
    }
}