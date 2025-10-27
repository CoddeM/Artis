package com.artis.artis.presentation.drawing.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview // Import for Preview
import androidx.compose.ui.unit.dp
import android.graphics.Color as AndroidColor // Import Android's Color for HSV conversion

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onColorSelected: (Color) -> Unit,
    onDismiss: () -> Unit
) {
    // Internal state that reflects the current selection in the dialog
    var selectedColorInternal by remember { mutableStateOf(initialColor) }

    // State for the Hue/Saturation part of the wheel (always full brightness)
    var colorWheelHueSat by remember { mutableStateOf(Color.Red) } // Initialize with a dummy color
    // State for the Value/Brightness slider
    var valueSlider by remember { mutableStateOf(1f) } // Initialize with full brightness

    // Effect to re-initialize internal states when initialColor changes (dialog is launched/re-launched with new color)
    LaunchedEffect(initialColor) {
        selectedColorInternal = initialColor
        val hsv = FloatArray(3)
        AndroidColor.colorToHSV(initialColor.toArgb(), hsv)
        colorWheelHueSat = Color(AndroidColor.HSVToColor(floatArrayOf(hsv[0], hsv[1], 1f))) // Reset hue/sat to max value
        valueSlider = hsv[2] // Reset value slider to initial color's value
    }

    // Effect to update the final selectedColorInternal whenever hue/sat or value changes
    LaunchedEffect(colorWheelHueSat, valueSlider) {
        val hsvHueSat = FloatArray(3)
        AndroidColor.colorToHSV(colorWheelHueSat.toArgb(), hsvHueSat)
        val newFinalColor = Color(AndroidColor.HSVToColor(floatArrayOf(hsvHueSat[0], hsvHueSat[1], valueSlider)))
        selectedColorInternal = newFinalColor
    }

    // Extract hue and saturation from the current colorWheelHueSat for the gradient slider
    val (currentHueForSlider, currentSaturationForSlider) = remember(colorWheelHueSat) {
        val hsv = FloatArray(3)
        AndroidColor.colorToHSV(colorWheelHueSat.toArgb(), hsv)
        Pair(hsv[0], hsv[1])
    }

    Surface(
        shape = RoundedCornerShape(20.dp),
        color = Color(0xFF28283E),
        shadowElevation = 8.dp,
        modifier = Modifier
            .width(300.dp)
            .wrapContentHeight()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                "Pick a Color",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Color Wheel
            ColorWheel(
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp),
                onColorChange = { newHueSatColor ->
                    colorWheelHueSat = newHueSatColor
                },
                // The initialColor for the wheel should reflect the current hue/sat being edited
                // but its *brightness* should not affect its initial positioning.
                // We reconstruct it here with the valueSlider's value to make it react.
                initialColor = Color(AndroidColor.HSVToColor(floatArrayOf(currentHueForSlider, currentSaturationForSlider, valueSlider))),
                currentValue = valueSlider // Pass the current valueSlider to the wheel for display
            )

            // Current Selected Color Preview
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(selectedColorInternal, CircleShape)
                    .border(2.dp, Color.White.copy(alpha = 0.7f), CircleShape)
                    .padding(bottom = 16.dp)
            )

            // Horizontal Value Slider with Custom Gradient
            ColorGradientSlider(
                value = valueSlider,
                onValueChange = { newValue ->
                    valueSlider = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                hue = currentHueForSlider,
                saturation = currentSaturationForSlider
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray.copy(alpha = 0.3f)),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text("Cancel", color = Color.White)
                }
                Button(
                    onClick = { onColorSelected(selectedColorInternal) },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 4.dp)
                ) {
                    Text("Select", color = Color.White)
                }
            }
        }
    }
}

// --- Preview Composable ---
@Preview(showBackground = true, backgroundColor = 0xFFCCCCCC)
@Composable
fun ColorPickerDialogPreview() {
    val initialPreviewColor = Color(0xFF4CAF50) // A shade of green

    ColorPickerDialog(
        initialColor = initialPreviewColor,
        onColorSelected = { color ->
            println("Selected color: $color")
        },
        onDismiss = {
            println("Dialog dismissed")
        }
    )
}