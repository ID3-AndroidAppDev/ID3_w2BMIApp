/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.bmi4watch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import com.example.bmi4watch.R
import com.example.bmi4watch.presentation.theme.BMI4watchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {

            WearApp()
        }
    }
}

@Composable
fun WearApp() {

    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<Float?>(null) }

    BMI4watchTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                WeightAndHeightInputs(weight, height, onWeightChange = { weight = it }, onHeightChange = { height = it })
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { bmiResult = calculateBMI(weight, height) }) {
                    Text(text = stringResource(id = R.string.calculate_bmi))
                }
                Spacer(modifier = Modifier.height(16.dp))
                bmiResult?.let {
                    Text(
                        text = "Your BMI is: %.2f".format(it),
                        style = MaterialTheme.typography.body2,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

    }
}

@Composable
fun WeightAndHeightInputs(
    weight: String,
    height: String,
    onWeightChange: (String) -> Unit,
    onHeightChange: (String) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = weight,
            onValueChange = onWeightChange,
            label = { Text(text = stringResource(id = R.string.enter_weight)) },
            modifier = Modifier.fillMaxWidth(),
            //keyboardOptions = KeyboardOptions.Default.copy(
            //    keyboardType = KeyboardType.Number
            //)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = height,
            onValueChange = onHeightChange,
            label = { Text(text = stringResource(id = R.string.enter_height)) },
            modifier = Modifier.fillMaxWidth(),
            //keyboardOptions = KeyboardOptions.Default.copy(
            //    keyboardType = KeyboardType.Number
            //)
        )
    }
}

fun calculateBMI(weight: String, height: String): Float? {
    val weightFloat = weight.toFloatOrNull()
    val heightFloat = height.toFloatOrNull()
    return if (weightFloat != null && heightFloat != null && heightFloat > 0) {
        weightFloat / (heightFloat * heightFloat)
    } else {
        null
    }
}


@Composable
fun Greeting(greetingName: String) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.primary,
        text = stringResource(R.string.hello_world, greetingName)
    )
}

@Preview(device = Devices.WEAR_OS_SMALL_ROUND, showSystemUi = true)
@Composable
fun DefaultPreview() {
    WearApp()
}