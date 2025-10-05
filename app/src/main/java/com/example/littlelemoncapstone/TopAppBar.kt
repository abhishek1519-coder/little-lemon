package com.example.littlelemoncapstone

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor


/*@Composable
fun TopAppBar(scaffoldState: ScaffoldState? = null, scope: CoroutineScope? = null) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = {
            scope?.launch { scaffoldState?.drawerState?.open() }
        }) {
            Image(
                painter = painterResource(id = R.drawable.ic_hamburger_menu),
                contentDescription = "Menu Icon",
                modifier = Modifier.size(24.dp)
            )
        }
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier.fillMaxWidth(0.5F)
                .padding(horizontal = 20.dp)
        )
        IconButton(onClick = { }) {
            Image(
                painter = painterResource(id = R.drawable.ic_cart),
                contentDescription = "Cart",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}*/

@Composable
fun TopAppBarForOnboarding() {
    Row(horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.white)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically) {

        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .padding(horizontal = 20.dp)
        )

    }
}

@Composable
fun TopAppBar(navController: NavController) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(LittleLemonColor.white)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Spacer(modifier = Modifier.size(48.dp))
        Image(
            painter = painterResource(id = R.drawable.littlelemonimgtxt_nobg),
            contentDescription = "Little Lemon Logo",
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .padding(horizontal = 20.dp)
        )
        IconButton(
            onClick = {   navController.navigate(Profile.route) },
            modifier = Modifier.size(48.dp) // A fixed size for the tappable area
        ) {
            Image(
                painter = painterResource(id = R.drawable.img), // This is your profile image
                contentDescription = "Profile Image",
                contentScale = ContentScale.Crop, // Crop the image to fit the circle
                modifier = Modifier
                    .size(40.dp) // The actual image size
                    .clip(CircleShape) // Make it circular
            )
        }

    }
}