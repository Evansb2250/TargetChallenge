package com.target.targetcasestudy.ui.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import coil.compose.AsyncImage
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue140
import com.target.targetcasestudy.theme.dpValue141
import com.target.targetcasestudy.theme.dpValue16
import com.target.targetcasestudy.theme.dpValue172
import com.target.targetcasestudy.theme.dpValue4
import com.target.targetcasestudy.theme.dpValue8
import com.target.targetcasestudy.ui.screens.catalog.domain.Deal

@Composable
fun DealCard(
    deal: Deal,
    onClick: (dealId: String) -> Unit = {},
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                all = dpValue16,
            )
            .clickable {
                onClick(deal.id.toString())
            }
    ) {
        Box(
            modifier = Modifier
                .size(dpValue140)
                .clip(RoundedCornerShape(dpValue8))
                .background(
                    color = Color.Gray,
                    shape = RoundedCornerShape(dpValue8)
                )
        ) {
            AsyncImage(
                model = deal.imageUrl,
                contentDescription = "Product image of a ${deal.title} in catalog"
            )
        }
        Column(
            modifier = Modifier
                .size(
                    width = dpValue172,
                    height = dpValue141,
                )
        ) {
            PriceCard(
                regularPrice = deal.regularPrice.displayString,
                specialPrice = deal.salePrice?.displayString,
                fulfillment = deal.fulfillment,
            )
            Text(
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontFamily = RebotoFontFamily,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .padding(
                        vertical = dpValue16,
                        horizontal = dpValue12,
                    )
                    .weight(1f, fill = true)
                    .fillMaxWidth(),
                text = deal.title,
            )

            Row(
                modifier = Modifier.padding(
                    horizontal = dpValue8,
                )
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = dpValue4,
                    ),
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF008300),
                    text = deal.availability,
                )
                Text(
                    fontSize = TextUnit(12f, TextUnitType.Sp),
                    fontFamily = RebotoFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF666666),
                    text = "in aisle ${deal.aisle}"
                )

            }
        }
    }
}