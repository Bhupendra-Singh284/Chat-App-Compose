package com.example.chat_app_compose.presentation.auth.common

import android.credentials.Credential
import android.credentials.GetCredentialException
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.chat_app_compose.R
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.CredentialManager
import com.example.chat_app_compose.theme.grey1
import com.example.chat_app_compose.theme.grey2
import com.example.chat_app_compose.util.Sizes
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.launch


@Composable
fun GoogleAuthButton(modifier: Modifier=Modifier,text: String, onCLick:(androidx.credentials.Credential)->Unit){
    val scope= rememberCoroutineScope()
    val context= LocalContext.current
    val manager= CredentialManager.create(context)
    Button(
        shape = RoundedCornerShape(Sizes.authButtonCorner),
        colors = ButtonDefaults.buttonColors(
            containerColor = grey1,
            contentColor = grey2
        ),
        modifier = modifier.fillMaxWidth(), onClick = {
            val options = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.default_web_client_id))
                .build()
            val request = androidx.credentials.GetCredentialRequest.Builder()
                .addCredentialOption(options).build()
            scope.launch {
                try {
                    val result = manager.getCredential(context = context,request)
                    onCLick(result.credential)
                }catch (e:androidx.credentials.exceptions.GetCredentialException)
                {
                    Toast.makeText(context,e.message.toString(),Toast.LENGTH_SHORT).show()
                }
            }
        }) {
        Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier.size(30.dp, 30.dp)
            )
            Spacer(modifier = Modifier.width(Sizes.paddingMedium))
            Text(text = text, style = MaterialTheme.typography.labelMedium.copy(fontSize = 16.sp))
        }
    }
}