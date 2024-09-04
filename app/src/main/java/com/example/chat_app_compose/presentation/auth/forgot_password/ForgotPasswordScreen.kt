package com.example.chat_app_compose.presentation.auth.forgot_password
import android.widget.Toast
import androidx.compose.foundation.Image
import com.example.chat_app_compose.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.presentation.auth.common.CustomBackButton
import com.example.chat_app_compose.presentation.auth.common.CustomButton
import com.example.chat_app_compose.presentation.auth.common.CustomTextField
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.util.MyResult
import com.example.chat_app_compose.util.Sizes

@Composable
fun ForgotPasswordScreen(
    onBackClick:()->Unit,
    resetAuthState:(MyResult?)->Unit,
    forgotPasswordState:ForgotPasswordState,
    onEvent:(ForgotPasswordEvent)->Unit,
    authState:MyResult?
){
    val state= rememberScrollState()
    val context = LocalContext.current


    when(authState)
    {
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
        is MyResult.Failure->{
            Toast.makeText(context,authState.error,Toast.LENGTH_SHORT).show()
            resetAuthState(null)
        }
        is MyResult.Success->{
            onBackClick()
            Toast.makeText(context,"Password Reset Link Sent",Toast.LENGTH_LONG).show()
            resetAuthState(MyResult.Loading)
        }
        else->{
            Box(
                Modifier
                    .statusBarsPadding()
                    .padding(bottom = Sizes.paddingMedium)
                    .verticalScroll(state = state)
            ) {
                CustomBackButton(
                    Modifier
                        .align(Alignment.TopStart)
                        .padding(Sizes.paddingXSmall)
                ) {
                    onBackClick()
                }
                Column(modifier= Modifier
                    .align(Alignment.Center)
                    .padding(horizontal = Sizes.authHorizontalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.padding(top=Sizes.paddingMedium,bottom = Sizes.paddingXLarge),
                        text = stringResource(id = R.string.forgot_password_heading),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 45.sp),
                        color = green2
                    )
                    Image(
                        modifier = Modifier
                            .padding(bottom = Sizes.paddingSmall)
                            .size(200.dp, 200.dp)
                        ,
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.forgot_password), contentDescription = null
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = stringResource(id = R.string.forgot_password_sub_heading),
                        style = MaterialTheme.typography.titleMedium,
                        color = grey2
                    )
                    CustomTextField(
                        Modifier.padding(top = Sizes.paddingLarge, bottom = Sizes.paddingXXSmall),
                        onValueChange = {
                            onEvent(ForgotPasswordEvent.OnEmailChange(it))
                        },
                        startValue = forgotPasswordState.email,
                        label = stringResource(id = R.string.forgot_password_email_textField_label),
                        labelIcon = Icons.Rounded.Email,
                        errorText = forgotPasswordState.emailErrorText
                    )
                    CustomButton(
                        text = stringResource(id = R.string.forgot_password_email_verify_button_text)) {
                        onEvent(ForgotPasswordEvent.SendCode)
                    }
                }
            }
        }
    }



}

//@Preview
//@Composable
//fun PreviewForgotPasswordScreen(){
//    val state = ForgotPasswordState()
//
//    ForgotPasswordScreen(state,{}) {}
//}