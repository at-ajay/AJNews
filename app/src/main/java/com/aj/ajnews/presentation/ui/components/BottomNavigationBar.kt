package com.aj.ajnews.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.aj.ajnews.presentation.navigation.BottomNavScreen
import com.aj.ajnews.util.Constants.Rubik

@Composable
fun BottomNavigationBar(navController : NavHostController) {

    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val navItems = listOf(
        BottomNavScreen.HomeScreen,
        BottomNavScreen.SavedArticlesScreen,
        BottomNavScreen.SettingsScreen,
        BottomNavScreen.SearchScreen
    )

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .padding(12.dp)
    ) {
        navItems.forEach { screen ->
            BottomNavItem(
                screen = screen,
                isSelected = currentBackStackEntry?.destination?.route == screen.route
            ) {
                navController.navigate(screen.route) {
                    /** launchSingleTop makes sure that only one copy of the destination route will be in the backstack **/
                    launchSingleTop = true
                }
            }
        }
    }

}

@Composable
fun BottomNavItem(
    screen: BottomNavScreen,
    isSelected : Boolean,
    onClick : () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .clip(shape = CircleShape)
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.background
            )
            .padding(10.dp)
    ) {
        Icon(
            imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
            else MaterialTheme.colorScheme.onBackground
        )

        AnimatedVisibility(visible = isSelected) {
            Text(
                text = stringResource(id = screen.label),
                color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer
                else MaterialTheme.colorScheme.onBackground,
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                modifier = Modifier.padding(end = 5.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    BottomNavigationBar(navController = rememberNavController())
}

@Preview
@Composable
fun BottomNavItemPreview() {
    BottomNavItem(screen = BottomNavScreen.HomeScreen, isSelected = true) { }
}