package com.example.multi_application.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val PrimaryBlue = Color(0xFF3366FF)
private val FieldGray = Color(0xFFF5F5F7)
private val HintGray = Color(0xFF9A9AA0)

data class RegisterFormState(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val agreedToTerms: Boolean = false
)

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onCreateAccount: (RegisterFormState) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var agreedToTerms by remember { mutableStateOf(false) }

    val fieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedContainerColor = FieldGray,
        focusedContainerColor = FieldGray,
        unfocusedBorderColor = Color.Transparent,
        focusedBorderColor = PrimaryBlue
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 24.dp)
            .verticalScroll(rememberScrollStateCompat())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Illustration placeholder
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(90.dp)
                .background(Color(0xFFDCE3F5), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Filled.ShoppingCart,
                contentDescription = null,
                tint = PrimaryBlue,
                modifier = Modifier.size(36.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Create Account",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Join MultiMarket for a premium shopping experience.",
            fontSize = 13.sp,
            color = HintGray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        LabeledField(
            label = "Full Name",
            value = fullName,
            onValueChange = { fullName = it },
            placeholder = "Enter your full name",
            leadingIcon = Icons.Filled.Person,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledField(
            label = "Email Address",
            value = email,
            onValueChange = { email = it },
            placeholder = "name@example.com",
            leadingIcon = Icons.Filled.Email,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        LabeledField(
            label = "Phone Number",
            value = phone,
            onValueChange = { phone = it },
            placeholder = "+1 (555) 000-0000",
            leadingIcon = Icons.Filled.Phone,
            colors = fieldColors
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Password", fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            placeholder = { Text("Create a strong password", color = HintGray) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = HintGray) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = HintGray
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Confirm Password", fontSize = 13.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            placeholder = { Text("Repeat your password", color = HintGray) },
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null, tint = HintGray) },
            trailingIcon = {
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(
                        imageVector = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle password visibility",
                        tint = HintGray
                    )
                }
            },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = fieldColors,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Terms checkbox
        Row(verticalAlignment = Alignment.Top) {
            Checkbox(
                checked = agreedToTerms,
                onCheckedChange = { agreedToTerms = it },
                colors = CheckboxDefaults.colors(checkedColor = PrimaryBlue)
            )
            Column(modifier = Modifier.padding(top = 12.dp)) {
                Row {
                    Text("I agree to the MultiMarket ", fontSize = 12.sp, color = Color(0xFF4A4A4F))
                    Text(
                        text = "Terms of Service",
                        fontSize = 12.sp,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(onClick = onTermsClick)
                    )
                }
                Row {
                    Text("and ", fontSize = 12.sp, color = Color(0xFF4A4A4F))
                    Text(
                        text = "Privacy Policy",
                        fontSize = 12.sp,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.clickable(onClick = onPrivacyClick)
                    )
                    Text(".", fontSize = 12.sp, color = Color(0xFF4A4A4F))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onCreateAccount(
                    RegisterFormState(
                        fullName = fullName,
                        email = email,
                        phone = phone,
                        password = password,
                        confirmPassword = confirmPassword,
                        agreedToTerms = agreedToTerms
                    )
                )
            },
            enabled = agreedToTerms,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlue)
        ) {
            Text("Create Account", fontSize = 16.sp, fontWeight = FontWeight.Medium)
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
    leadingIcon: androidx.compose.ui.graphics.vector.ImageVector,
    colors: TextFieldColors
) {
    Text(label, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    Spacer(modifier = Modifier.height(6.dp))
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = HintGray) },
        leadingIcon = { Icon(leadingIcon, contentDescription = null, tint = HintGray) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        colors = colors,
        modifier = Modifier.fillMaxWidth()
    )
}

// Small wrapper so this file only needs foundation.layout + verticalScroll import at call site
@Composable
private fun rememberScrollStateCompat() = androidx.compose.foundation.rememberScrollState()