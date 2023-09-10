package com.aj.ajnews.presentation.ui.screens.category_selection_screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.aj.ajnews.R
import com.aj.ajnews.presentation.navigation.Screen
import com.aj.ajnews.presentation.ui.components.CategoryChips
import com.aj.ajnews.util.Constants.Rubik

@RequiresApi(Build.VERSION_CODES.N)
@Composable
fun CategorySelectionScreen(vm: CategorySelectionViewModel = hiltViewModel(), navController: NavHostController) {

    LaunchedEffect(key1 = true) {
        vm.uiEvent.collect { event ->
            when(event) {
                is CategorySelectionUIEvent.OnCategorySavingProcessed -> navController.navigate(Screen.HostScreen.route) {
                    popUpTo(Screen.CategorySelectionScreen.route) {
                        inclusive = true
                    }
                }
            }
        }
    }

    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .padding(20.dp)) {
        val (title, warningMsg, categories, savePrefsBtn) = createRefs()

        Text(
            text = stringResource(id = R.string.select_categories),
            fontFamily = Rubik,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            modifier = Modifier.constrainAs(title) {
                top.linkTo(parent.top, 40.dp)
                start.linkTo(parent.start)
            }
        )

        CategoryChips(
            categories = vm.categories,
            modifier =  Modifier.constrainAs(categories) {
                top.linkTo(title.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }
        ) {
            vm.onEvent(CategorySelectionEvent.OnChipSelected(it))
        }

        AnimatedVisibility(
            visible = vm.showErrorMsg,
            modifier = Modifier.constrainAs(warningMsg) {
                top.linkTo(categories.bottom, 30.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)

                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = stringResource(id = R.string.category_warning),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = { vm.onEvent(CategorySelectionEvent.OnSavePrefsClicked) },
            modifier = Modifier.constrainAs(savePrefsBtn) {
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
            Text(
                text = stringResource(id = R.string.next),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium
            )
        }

    }

}

@RequiresApi(Build.VERSION_CODES.N)
@Preview(showSystemUi = true)
@Composable
fun CategorySelectionScreenPreview() {
    CategorySelectionScreen(navController = rememberNavController())
}