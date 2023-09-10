package com.aj.ajnews.presentation.ui.screens.splash_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.aj.ajnews.R
import com.aj.ajnews.presentation.navigation.Screen
import com.aj.ajnews.util.Constants.Rubik
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(vm: SplashViewModel = hiltViewModel(), navController: NavController) {

    val density = LocalDensity.current

    LaunchedEffect(key1 = true) {
        /** Displaying Branding Name **/
        delay(500L)
        vm.isBrandNameLaunched = true

        /** Displaying App Name **/
        delay(1500L)
        vm.isAppNameLaunched = true

        /** Navigating to next screen and ending SplashScreen **/
        delay(1000L)
        navController.navigate(vm.nextScreenRoute) {
            /** popUpTo() will pop all the screens from the backstack until it founds the screen passed in the function **/
            popUpTo(Screen.SplashScreen.route) {
                /** inclusive specifies whether the popUpTo screen i.e the screen passed to the function should be popped too **/
                inclusive = true
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = vm.isBrandNameLaunched,
            enter = slideInVertically { with(density) { 1000.dp.roundToPx() } } + fadeIn(animationSpec = tween(durationMillis = 1000))
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.brand_name),
                    fontFamily = Rubik,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )

                AnimatedVisibility(
                    visible = vm.isAppNameLaunched,
                    enter = expandHorizontally { with(density) { -300.dp.roundToPx() } } + fadeIn(animationSpec = tween(durationMillis = 1000))
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(5.dp))
                        Text(
                            text = stringResource(id = R.string.app_category),
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium,
                            fontSize = 25.sp
                        )
                    }
                }
            }
        }
    }

}