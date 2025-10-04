package com.example.littlelemoncapstone

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch


@Composable
fun Home(navController: NavController) {
    Button(
        onClick = {
            // Simple validation check
              navController.navigate(Profile.route)

        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow),
    ) {
        Text(
            text = stringResource(id = R.string.profile),
            color = Color.Black
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewHome(){
    Home(rememberNavController())
}