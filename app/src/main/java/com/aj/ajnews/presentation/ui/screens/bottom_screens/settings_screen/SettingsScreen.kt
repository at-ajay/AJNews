package com.aj.ajnews.presentation.ui.screens.bottom_screens.settings_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.aj.ajnews.R
import com.aj.ajnews.presentation.ui.MainActivity.Companion.isAppInDarkTheme
import com.aj.ajnews.presentation.ui.components.CategoryChips
import com.aj.ajnews.util.Constants.Rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(vm: SettingsViewModel = hiltViewModel()) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {

            val (featureName, featureDescription, buttonEnableDarkTheme) = createRefs()

            Text(
                text = stringResource(id = R.string.dark_theme),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(featureName) {
                    top.linkTo(parent.top, 30.dp)
                    start.linkTo(parent.start)
                    end.linkTo(buttonEnableDarkTheme.start, 10.dp)

                    width = Dimension.fillToConstraints
                }
            )

            Text(
                text = stringResource(id = R.string.dark_theme_description),
                fontFamily = Rubik,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier.constrainAs(featureDescription) {
                    top.linkTo(featureName.bottom, 5.dp)
                    start.linkTo(parent.start)
                    end.linkTo(buttonEnableDarkTheme.start, 10.dp)

                    width = Dimension.fillToConstraints
                }
            )

            Switch(
                checked = isAppInDarkTheme,
                onCheckedChange = { vm.onEvent(SettingsEvent.OnThemeStateChanged(it)) },
                modifier = Modifier.constrainAs(buttonEnableDarkTheme) {
                    top.linkTo(featureName.top)
                    end.linkTo(parent.end)
                    bottom.linkTo(featureDescription.bottom)
                }
            )

        }

        Spacer(modifier = Modifier.height(50.dp))

        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (featureName, featureContent, warning) = createRefs()

            Text(
                text = stringResource(id = R.string.categories),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(featureName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            CategoryChips(
                categories = vm.categories,
                modifier = Modifier.constrainAs(featureContent) {
                    top.linkTo(featureName.bottom, 10.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                }
            ) {
                vm.onEvent(SettingsEvent.OnChipSelected(it))
            }

            this@Column.AnimatedVisibility(
                visible = vm.showErrorMsg,
                modifier = Modifier.constrainAs(warning) {
                    top.linkTo(featureContent.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                }
            ) {
                Text(
                    text = stringResource(id = R.string.category_one_must_be_selected),
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        Spacer(modifier = Modifier.height(50.dp))
        
        ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
            val (featureName, countryPicker) = createRefs()
            
            Text(
                text = stringResource(id = R.string.country),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                modifier = Modifier.constrainAs(featureName) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
            )

            ExposedDropdownMenuBox(
                expanded = vm.isExpanded,
                onExpandedChange = { vm.isExpanded = !vm.isExpanded },
                modifier = Modifier.constrainAs(countryPicker) {
                    top.linkTo(featureName.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                }
            ) {
                TextField(
                    value = vm.selectedCountry,
                    textStyle = TextStyle(
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    ),
                    onValueChange = {},
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = vm.isExpanded) },
                    readOnly = true,
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(15.dp))
                )

                ExposedDropdownMenu(
                    expanded = vm.isExpanded,
                    onDismissRequest = { vm.isExpanded = false }
                ) {
                    vm.countries.forEach { (key, value) ->
                        DropdownMenuItem(
                            text = {
                                   Text(
                                       text = value,
                                       fontFamily = Rubik,
                                       fontWeight = FontWeight.Medium,
                                       fontSize = 16.sp
                                   )
                            },
                            onClick = { vm.onEvent(SettingsEvent.OnCountrySelected(key, value)) },
                            contentPadding = PaddingValues(10.dp),
                            modifier = Modifier.clip(shape = RoundedCornerShape(10.dp))
                        )
                    }
                }
            }
        }

    }

}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}