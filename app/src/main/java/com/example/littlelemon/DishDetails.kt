package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.ui.theme.LittleLemonColor
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun DishDetails(id: Int) {
    val dish = requireNotNull(DishRepository.getDish(id))
    Column(verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.background(LittleLemonColor.white )) {
        TopAppBar()
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(8.dp) // Add some padding to the column
        ) {
            // 1. Add the Dish Image
            Image(
                painter = painterResource(id = dish.imageResource),
                contentDescription = dish.name,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillWidth
            )

            // 2. Add the Dish Name
            Text(
                text = dish.name,
                style = MaterialTheme.typography.headlineLarge
            )

            // 3. Add the Dish Description
            Text(
                text = dish.description,
                style = MaterialTheme.typography.bodyLarge
            )

            // 4. Add the Counter
            Counter()

            // 5. Add the "Add to cart" button
            Button(
                onClick = { /* TODO: Add to cart logic */ },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = LittleLemonColor.yellow
                )
            ) {
                Text(
                    text = stringResource(id = R.string.add_for) + " $${dish.price}",
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun Counter() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth(),
    ) {
        var counter by remember {
            mutableStateOf(1)
        }
        TextButton(
            onClick = {
                counter--
            }
        ) {
            Text(
                text = "-",
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Text(
            text = counter.toString(),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(16.dp)
        )
        TextButton(
            onClick = {
                counter++
            }
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}

@Preview
@Composable
fun DishDetailsPreview() {
    DishDetails(id = 3)
}
