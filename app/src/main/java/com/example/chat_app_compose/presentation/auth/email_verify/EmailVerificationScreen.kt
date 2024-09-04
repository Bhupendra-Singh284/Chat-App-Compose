package com.example.chat_app_compose.presentation.auth.email_verify

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.example.chat_app_compose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chat_app_compose.presentation.auth.common.CustomBackButton
import com.example.chat_app_compose.presentation.auth.common.CustomButton
import com.example.chat_app_compose.theme.green1
import com.example.chat_app_compose.theme.green2
import com.example.chat_app_compose.theme.grey1
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.theme.grey3
import com.example.chat_app_compose.util.MyResult
import com.example.chat_app_compose.util.Sizes

@Composable
fun EmailVerificationScreen(
    isUserVerified:Boolean=false,
    verificationLinkSent:Boolean,
    userEmail:String?,
    authState:MyResult?,
    onBackClick:()->Unit,
    sendLink:()->Unit,
    onContinue:()->Unit,
    navigateToHome:()->Unit,
    resetAuthState:(MyResult?)->Unit,
)
{

    Log.d("MainActivity","Email verification screen called , resultState->${authState.toString()}")
    val context = LocalContext.current
    val state = rememberScrollState()

    when(authState)
    {
        is MyResult.Success->{
            if(isUserVerified)
            {
                navigateToHome()
                resetAuthState(MyResult.Loading)
            }else{
            Toast.makeText(context,"Email Verification Link Sent",Toast.LENGTH_LONG).show()
                resetAuthState(null)
            }
        }
        is MyResult.Failure->{
            Toast.makeText(context,authState.error,Toast.LENGTH_SHORT).show()
            resetAuthState(null)
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
                Column(
                    Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = Sizes.authHorizontalPadding),
                    horizontalAlignment = Alignment.CenterHorizontally) {

                    Text(
                        modifier = Modifier.padding(vertical = Sizes.paddingLarge),
                        text = stringResource(id = R.string.email_verify_heading),
                        style = MaterialTheme.typography.headlineMedium.copy(fontSize = 45.sp),
                        color = green2)

                    Image(
                        modifier = Modifier.size(200.dp, 200.dp),
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.email_verify),
                        contentDescription = null)
                    Text(
                        modifier=Modifier.padding(top = Sizes.paddingSmall, bottom = Sizes.paddingMedium),
                        text = stringResource(id = R.string.email_verify_sub_heading)+userEmail,
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        color = grey2)
                    CustomButton(
                        Modifier.padding(bottom = Sizes.paddingMedium),
                        text = stringResource(id = if(verificationLinkSent) R.string.email_verify_resend_link_button_text else R.string.email_verify_get_link_button_text),
                        containerColor = grey1,
                        textColor = grey3
                        ){
                        sendLink()
                    }
                    CustomButton(
                        Modifier.padding(bottom = Sizes.paddingSmall),
                        text = stringResource(id = R.string.email_verify_continue_button_text),
                    ){
                        onContinue()
                    }
                }

            }






        }
    }

}