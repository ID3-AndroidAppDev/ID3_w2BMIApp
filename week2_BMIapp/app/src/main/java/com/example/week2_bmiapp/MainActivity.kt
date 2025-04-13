package com.example.week2_bmiapp

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import com.example.week2_bmiapp.ui.theme.Week2_BMIappTheme
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import kotlin.math.pow
import kotlin.text.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Week2_BMIappTheme {
                Surface {
                    FieldsBmiScreen()
                }
            }
        }
    }
}

@Composable
fun FieldsBmiScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        val textTitle = "BMI App ID3"
        Text(
            textTitle,
            Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.SemiBold
        )

        var weightInput by remember {
            mutableStateOf("")
        }
        val weightTextField = "Please input your weight (kg)"
        TextField(
            value = weightInput,
            onValueChange = {
                weightInput = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = weightTextField)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        var heightInput by remember {
            mutableStateOf("")
        }
        val heightTextField = "Please input your height (m)"
        TextField(
            value = heightInput,
            onValueChange = {
                heightInput = it
            },
            Modifier.fillMaxWidth(),
            label = {
                Text(text = heightTextField)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        val context = LocalContext.current
        val textButton = "Calculate BMI"
        Button(
            onClick = {
                val textToast = analyseTextInputs(weightInput, heightInput)
                Toast.makeText(
                    context,
                    textToast, Toast.LENGTH_LONG
                ).show()
            },
            Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
        ) {
            Text(
                text = textButton,
                fontSize = 20.sp
            )
        }
    }
}

fun analyseTextInputs(weight: String, height: String): String {
    return if (weight.isEmpty() || height.isEmpty()) {
        "Please, complete all information!"
    } else if (weight.toFloatOrNull() == null || height.toFloatOrNull() == null) {
        "Please, input only numbers!"
    } else if (weight.toFloat() == 0.0f) {
        "Please, input your weight in kilograms (kg)!"
    } else if (height.toFloat() == 0.0f || height.toFloat() > 2.5f) {
        "Please, input your height in meters (m)!"
    } else {
        val bmiResult = calculateBmi(weight, height)
        val classification = bmiClassification(bmiResult)
        "BMI: $bmiResult\t\n$classification\t"
    }
}

fun calculateBmi(weight: String, height: String): Double {
    val result = weight.toFloat() / height.toDouble().pow(2)
    val decimalFormat = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ENGLISH))
    val round = decimalFormat.format(result).toDouble()
    return round
}

fun bmiClassification(bmi: Double): String {
    val classification = bmi.let {
        when {
            it < 18.4 -> "Underweight"
            it >= 18.5 && it < 24.9 -> "Normal Weight"
            it >= 25.0 && it < 29.9 -> "Overweight"
            it >= 30.0 && it < 34.9 -> "Obesity Class I"
            it >= 35.0 && it < 39.9 -> "Obesity Class II"
            it >= 40.0 -> "Obesity Class III"
            else -> {
                ""
            }
        }
    }
    return classification
}

@Preview
@Composable
fun FieldsBmiScreenPreview() {
    Week2_BMIappTheme {
        Surface {
            FieldsBmiScreen()
        }
    }
}




