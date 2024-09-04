package com.example.chat_app_compose.presentation.auth.login

import android.credentials.CredentialManager
import android.credentials.GetCredentialRequest
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.chat_app_compose.R
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.presentation.auth.common.CustomButton
import com.example.chat_app_compose.presentation.auth.common.CustomTextField
import com.example.chat_app_compose.presentation.auth.common.GoogleAuthButton
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.theme.grey3
import com.example.chat_app_compose.util.MyResult
import com.example.chat_app_compose.util.Sizes
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.oAuthCredential
import kotlinx.coroutines.launch


//@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
@Composable
fun LoginScreen(
    //navigation methods
    navigateToEmailVerify:()->Unit,
    navigateToHome:()->Unit,
    onForgotPasswordClick:()->Unit,
    onSignUpClick:()->Unit,

    //view model methods
    emailVerified:Boolean,
    loginState: LoginState,
    resetAuthState:(MyResult?)->Unit,
    authState: MyResult?,
    onEvent:(LoginEvent)->Unit
){
    val state = rememberScrollState()
    val context = LocalContext.current
    when(authState)
    {
        is MyResult.Failure->{
            Toast.makeText(context,authState.error,Toast.LENGTH_SHORT).show()
            resetAuthState(null)
        }
        is MyResult.Success->{
            if(emailVerified)
            {
                    navigateToHome()
            }else{
                    navigateToEmailVerify()
            }
            resetAuthState(MyResult.Loading)
        }
            is MyResult.Loading->{
                Column(
                    Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)) {
                    CircularProgressIndicator(
                        color = green2
                    )
                }
            }
            else ->{
                Column(
                    Modifier
                        .background(color = Color.White)
                        .statusBarsPadding()
                        .padding(
                            horizontal = Sizes.authHorizontalPadding,
                            vertical = Sizes.paddingSmall
                        )
                        .verticalScroll(state = state)
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon_ui),
                        contentDescription = null,
                        modifier = Modifier.size(200.dp,150.dp)
                    )
                    Text(
                        modifier = Modifier.padding(vertical = Sizes.paddingXSmall),
                        text = stringResource(id = R.string.login_welcome), color = green2,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = stringResource(id = R.string.login_to_your_account),
                        style = MaterialTheme.typography.titleMedium,
                        color = grey2
                    )
                    CustomTextField(
                        Modifier.padding(top = Sizes.paddingMedium, bottom = Sizes.paddingXXSmall),
                        onValueChange = {
                            onEvent(LoginEvent.EmailValueChange(it))
                        },
                        startValue = loginState.emailText,
                        label = stringResource(id = R.string.email_text_field_label),
                        Icons.Rounded.Email,
                        loginState.emailErrorText
                    )
                    CustomTextField(
                        onValueChange = {
                            onEvent(LoginEvent.PasswordValueChange(it))
                        },
                        startValue = loginState.passwordText,
                        label= stringResource(id = R.string.password_text_field_label),
                        labelIcon =  Icons.Rounded.Lock,
                        errorText =  loginState.passwordErrorText
                    )
                    Box(
                        modifier = Modifier
                            .clickable {
                                onForgotPasswordClick()
                            }
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = stringResource(id = R.string.login_forgot_password),
                            style = MaterialTheme
                                .typography
                                .labelSmall.copy(fontWeight = FontWeight.Medium,),
                            color = grey2
                        )
                    }
                    CustomButton(Modifier.padding(top = Sizes.paddingLarge, bottom = Sizes.paddingSmall), text = "Login") {
                        onEvent(LoginEvent.UserLogIn)
                    }
                    GoogleAuthButton(text = stringResource(id = R.string.login_google_log_in)) {
                        credential -> onEvent(LoginEvent.UserGoogleSignIn(credential))
                    }
                    Row(
                        modifier = Modifier.padding(top=40.dp, bottom = Sizes.paddingSmall),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.login_do_not_have_account),
                            style = MaterialTheme
                                .typography
                                .labelMedium
                                .copy(fontSize = 15.sp), color = grey3
                        )
                        Spacer(modifier = Modifier.width(Sizes.paddingXSmall))
                        Button(
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.White,
                                containerColor = green2
                            ),
                            onClick = {
                                onSignUpClick()
                            }) {
                            Text(
                                text = stringResource(id = R.string.login_sign_up_text),
                                style = MaterialTheme
                                    .typography
                                    .labelMedium
                                    .copy(fontSize = 16.sp))
                        }
                    }
                }
            }
        }


}
