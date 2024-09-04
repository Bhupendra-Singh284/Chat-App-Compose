package com.example.chat_app_compose.presentation.auth.sign_up

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.Display.Mode
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.chat_app_compose.util.Sizes
import com.example.chat_app_compose.R
import com.example.chat_app_compose.presentation.auth.common.CustomBackButton
import com.example.chat_app_compose.presentation.auth.common.CustomButton
import com.example.chat_app_compose.presentation.auth.common.CustomTextField
import com.example.chat_app_compose.presentation.auth.common.GoogleAuthButton
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.theme.grey3
import com.example.chat_app_compose.theme.lightBlue1
import com.example.chat_app_compose.util.MyResult

@Composable
fun SignUpScreen(
    userVerified:Boolean=false,
    navigateToEmailVerify:()->Unit,
    onBackClick:()->Unit,
    signUpState: SignUpState,
    navigateToHome:()->Unit,
    authState:MyResult?,
    resetAuthState: (MyResult?)->Unit,
    onEvent:(SignUpEvent)->Unit
) {
    Log.d("MainActivity","Sign up screen called , resultState->${authState.toString()}")
    val state = rememberScrollState()
    val context = LocalContext.current
    val imageSelectorLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            uri: Uri? -> uri?.let{ onEvent(SignUpEvent.UserImageChange(it)) }
        }
    )
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            isGranted: Boolean -> if (isGranted) { imageSelectorLauncher.launch("image/*") }
        }
    )

    when (authState) {
        is MyResult.Failure -> {
            Toast.makeText(context, authState.error, Toast.LENGTH_SHORT).show()
            resetAuthState(null)
        }
        is MyResult.Success -> {
            if(userVerified)
            {
                navigateToHome()
                resetAuthState(MyResult.Loading)
            }else{
                Log.d("MainActivity","Sign up screen, email verification screen called , resultState->${authState.toString()}")
                navigateToEmailVerify()
                resetAuthState(null)
            }
        }
        is MyResult.Loading -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator(color = green2)
               }
        }
        else -> {
            Box(
                Modifier
                    .statusBarsPadding()
                    .verticalScroll(state = state)
            ) {
                CustomBackButton(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(Sizes.paddingXSmall)
                ) {
                    onBackClick()
                }
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = Sizes.authHorizontalPadding)
                        .padding(bottom = Sizes.paddingMedium),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        modifier = Modifier.padding(bottom = Sizes.paddingSmall),
                        text = stringResource(id = R.string.signUp_heading),
                        color = green2,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        modifier = Modifier.padding(bottom = Sizes.paddingMedium),
                        text = stringResource(id = R.string.signUp_sub_heading),
                        style = MaterialTheme.typography.labelMedium,
                        color = grey2
                    )
                    Box(
                        modifier = Modifier
                            .padding(bottom = Sizes.paddingMedium)
                            .clip(shape = CircleShape)
                            .background(color = lightBlue1)
                            .size(100.dp)
                            .clickable
                            {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val isGranted = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.READ_MEDIA_IMAGES
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (isGranted) {
                                        imageSelectorLauncher.launch("image/*")
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                                    }
                                } else {
                                    val isGranted = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.READ_EXTERNAL_STORAGE
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (isGranted) {
                                        imageSelectorLauncher.launch("image/*")
                                    } else {
                                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                                    }
                                }
                            },
                    ) {
                        AsyncImage(
                            modifier = Modifier.align(Alignment.Center),
                            model = ImageRequest.Builder(context).data(signUpState.imageUri).build(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop
                        )
                        Icon(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .padding(end = Sizes.paddingXXSmall, top = Sizes.paddingXSmall)
                                .size(30.dp),
                            imageVector = Icons.Rounded.Add,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    CustomTextField(
                        Modifier.padding(bottom = Sizes.paddingXXSmall),
                        onValueChange = { onEvent(SignUpEvent.UserNameValueChange(it)) },
                        startValue = signUpState.userName,
                        label = stringResource(id = R.string.signUp_userName_textField_label),
                        labelIcon = Icons.Rounded.Person,
                        errorText = signUpState.userNameErrorText
                    )
                    CustomTextField(
                        Modifier.padding(bottom = Sizes.paddingXXSmall),
                        onValueChange = { onEvent(SignUpEvent.EmailValueChange(it)) },
                        startValue = signUpState.email, label = stringResource(id = R.string.signUp_email_textField_label),
                        labelIcon = Icons.Rounded.Email, errorText = signUpState.emailErrorText
                    )
                    CustomTextField(
                        onValueChange = { onEvent(SignUpEvent.PasswordValueChange(it)) },
                        startValue = signUpState.password, label = stringResource(id = R.string.signUp_password_textField_label),
                        labelIcon = Icons.Rounded.Lock, errorText = signUpState.passwordErrorText
                    )
                    CustomButton(
                        Modifier.padding(top = Sizes.paddingMedium, bottom = Sizes.paddingSmall),
                        text = stringResource(id = R.string.signUp_signUp_button_text),
                        onCLick = { onEvent(SignUpEvent.OnSignUp) }
                    )
                    GoogleAuthButton(text = stringResource(id = R.string.signUp_google_sign_in)) {
                        credential->
                        onEvent(SignUpEvent.OnGoogleSignUp(credential))  }
                }
            }
        }
    }
}


