package com.example.multi_application.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.multi_application.R

// ---------- Palette (green) ----------
private val PrimaryGreen = Color(0xFF16A34A)
private val FieldGray = Color(0xFFF3F4F8)
private val HintGray = Color(0xFFAEAEB8)
private val LabelGray = Color(0xFF6B6B76)
private val TitleDark = Color(0xFF1C1C28)

data class SignUpFormState(
    val name: String = "",
    val email: String = "",
    val password: String = ""
)

@Composable
fun SignUpScreen(
    onForgotPassword: () -> Unit,
    onSignUp: (SignUpFormState) -> Unit,
    onSignInClick: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = FieldGray,
        focusedContainerColor = FieldGray,
        unfocusedBorderColor = Color.Transparent,
        focusedBorderColor = PrimaryGreen,
        cursorColor = PrimaryGreen
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 28.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Illustration image — swap in your own asset at
        // res/drawable/img_signup_illustration.xml (or .png/.webp)
        Image(
            painter = painterResource(id = R.drawable.img_signup_illustration),
            contentDescription = "Sign up illustration",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "SignUp",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = TitleDark
        )

        Spacer(modifier = Modifier.height(28.dp))

        LabeledField(
            label = "Name",
            value = name,
            onValueChange = { name = it },
            placeholder = "Enter Name",
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledField(
            label = "Email",
            value = email,
            onValueChange = { email = it },
            placeholder = "Enter Email",
            colors = fieldColors,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledField(
            label = "Password",
            value = password,
            onValueChange = { password = it },
            placeholder = "Enter Password",
            colors = fieldColors,
            isPassword = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "Forget Password",
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = PrimaryGreen,
                modifier = Modifier.clickable(onClick = onForgotPassword)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                onSignUp(SignUpFormState(name = name, email = email, password = password))
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryGreen)
        ) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("SignUp", fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row {
            Text(
                text = "Already have an account?  ",
                fontSize = 13.sp,
                color = LabelGray
            )
            Text(
                text = "sign in",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = PrimaryGreen,
                modifier = Modifier.clickable(onClick = onSignInClick)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun LabeledField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    colors: TextFieldColors,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = LabelGray)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = HintGray, fontSize = 14.sp) },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = colors,
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
            modifier = Modifier.fillMaxWidth()
        )
    }
}