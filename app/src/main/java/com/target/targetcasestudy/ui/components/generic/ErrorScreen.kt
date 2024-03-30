package com.target.targetcasestudy.ui.components.generic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.target.targetcasestudy.R
import com.target.targetcasestudy.theme.RebotoFontFamily
import com.target.targetcasestudy.theme.dpValue16

@Composable
fun ErrorScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(324.dp),
            painter = painterResource(
                id = R.drawable.error_icon
            ),
            contentDescription = "Error"
        )

        Text(
            modifier = Modifier.padding(vertical = dpValue16),
            fontSize = TextUnit(24f, TextUnitType.Sp),
            fontFamily = RebotoFontFamily,
            fontWeight = FontWeight.Bold,
            text = "Error Detected Please Try later"
        )
    }
}