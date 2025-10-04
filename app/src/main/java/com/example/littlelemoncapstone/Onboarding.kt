package com.example.littlelemoncapstone

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.littlelemoncapstone.data.UserDataManager
import com.example.littlelemoncapstone.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch


@Composable
fun Onboarding(navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }


    val context = LocalContext.current
    // Create a coroutine scope to launch the DataStore operation
    val coroutineScope = rememberCoroutineScope()
    // Instantiate your UserDataManager
    val userDataManager = remember { UserDataManager(context) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LittleLemonColor.white)
    ) {
        // --- Header ---
        TopAppBarForOnboarding()

        // --- "Let's get to know you" Banner ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(LittleLemonColor.green)
                .padding(vertical = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.know_you),
                fontSize = 24.sp,
                color = LittleLemonColor.cloud
            )
        }

        // --- Form ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(20.dp) // Adds space between form elements
        ) {
            Text(
                text = stringResource(id = R.string.pers_info),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            // --- First Name Field ---
            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(stringResource(id = R.string.f_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // --- Last Name Field ---
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(stringResource(id = R.string.l_name)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // --- Email Field ---
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.email)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // --- Register Button ---
            Button(
                onClick = {
                    // Simple validation check
                    if (firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()) {
                        // Launch a coroutine to save data without blocking the UI
                        coroutineScope.launch {
                            userDataManager.saveUserData(firstName, lastName, email)
                        }
                        Toast.makeText(context,  R.string.register_successful, Toast.LENGTH_SHORT).show()
                        // TODO: Navigate to Home screen here
                        navController.navigate(Home.route){
                            popUpTo(Onboarding.route){
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else {
                        Toast.makeText(context, R.string.register_unsuccessful, Toast.LENGTH_SHORT).show()
                    }

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow),
                // Enable button only if all fields have text
                enabled = firstName.isNotBlank() && lastName.isNotBlank() && email.isNotBlank()
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    color = Color.Black
                )
            }
        }
    }
}

@Preview
@Composable
fun OnboardingPreview(){
    Onboarding(rememberNavController())
}


