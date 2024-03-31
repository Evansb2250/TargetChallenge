package com.target.targetcasestudy.ui.components.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue12
import com.target.targetcasestudy.theme.dpValue140
import com.target.targetcasestudy.theme.dpValue4


@Composable
fun MenuButton(
    text: String,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = Modifier
            .height(dpValue140)
            .fillMaxWidth()
            .padding(dpValue12),
        onClick = onClick,
        shape = RoundedCornerShape(dpValue4)
    ) {
        Text(
            fontFamily = RebotoFontFamily ,
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(18f, TextUnitType.Sp),
            text = text,
            color = Color.Gray,
        )
    }
}