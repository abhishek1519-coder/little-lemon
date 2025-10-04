package com.example.littlelemoncapstone

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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


@Composable
fun Profile(navController: NavHostController) {
    val context = LocalContext.current
    var userDataManage = remember { UserDataManager(context) }
    val coroutineScope = rememberCoroutineScope()

    val fName by userDataManage.firstNameFlow.collectAsState(initial = false)
    val lName by userDataManage.lastNameFlow.collectAsState(initial = false)
    val email by userDataManage.emailFlow.collectAsState(initial = false)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LittleLemonColor.white)
    ) {
        // --- Header ---
        TopAppBarForOnboarding()



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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 80.dp),
                textAlign = TextAlign.Start
            )
            Text(
                text = "First name",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 60.dp)
            )
            OutlinedTextField(
                value = "${fName?:""}",
                onValueChange = {}, // Empty lambda makes it non-interactive
                readOnly = true, // This is the key property to make it not editable
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors( // Optional: Style it for read-only state
                    disabledTextColor = Color.Black,
                    disabledLabelColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray,
                    disabledContainerColor = Color.White
                ),

                enabled = false // Setting enabled to false triggers the 'disabled' colors
            )

            Text(
                text = "Last name",
                fontSize = 12.sp,
                color = Color.Gray
            )
            OutlinedTextField(
                value = "${lName?:""}",
                onValueChange = {}, // Empty lambda makes it non-interactive
                readOnly = true, // This is the key property to make it not editable
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors( // Optional: Style it for read-only state
                    disabledTextColor = Color.Black,
                    disabledLabelColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray,
                    disabledContainerColor = Color.White
                ),

                enabled = false // Setting enabled to false triggers the 'disabled' colors
            )

            Text(
                text = "Email",
                fontSize = 12.sp,
                color = Color.Gray
            )
            OutlinedTextField(
                value = "${email?:""}",
                onValueChange = {}, // Empty lambda makes it non-interactive
                readOnly = true, // This is the key property to make it not editable
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors( // Optional: Style it for read-only state
                    disabledTextColor = Color.Black,
                    disabledLabelColor = Color.Gray,
                    disabledIndicatorColor = Color.Gray,
                    disabledContainerColor = Color.White
                ),

                enabled = false // Setting enabled to false triggers the 'disabled' colors
            )



            Spacer(modifier = Modifier.weight(1f)) // Pushes the button to the bottom

            // --- Register Button ---
            Button(
                onClick = {
                    // Simple validation check

                        Toast.makeText(context,  R.string.logout, Toast.LENGTH_SHORT).show()
                        // TODO: Navigate to Home screen here
                        navController.navigate(Onboarding.route)

                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(LittleLemonColor.yellow),
                // Enable button only if all fields have text
                ) {
                Text(
                    text = stringResource(id = R.string.log_me_out),
                    color = Color.Black
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewProfile(){
    Profile(rememberNavController())
}