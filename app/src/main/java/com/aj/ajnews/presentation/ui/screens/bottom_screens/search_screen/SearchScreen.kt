package com.aj.ajnews.presentation.ui.screens.bottom_screens.search_screen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.aj.ajnews.R
import com.aj.ajnews.presentation.ui.MainActivity.Companion.showWebView
import com.aj.ajnews.presentation.ui.components.ArticleListItem
import com.aj.ajnews.presentation.ui.components.WebView
import com.aj.ajnews.util.Constants.Rubik
import com.aj.ajnews.util.rememberKeyBoardAsState

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(vm: SearchViewModel = hiltViewModel()) {

    val keyboardState by rememberKeyBoardAsState()

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (searchResult, searchBar, webView, loadingIndicator, errorMsg, infoMsg) = createRefs()

        AnimatedVisibility(
            visible = !vm.state.isInitialLoadingInProgress && vm.state.error.isBlank() && !showWebView,
            modifier = Modifier.constrainAs(searchResult) {
                top.linkTo(parent.top, if (keyboardState == com.aj.ajnews.util.Keyboard.OPENED) 250.dp else 20.dp)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)
                bottom.linkTo(searchBar.top, 20.dp)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }.animateContentSize(animationSpec = tween(durationMillis = 500))
        ) {
            LazyColumn {

                items(vm.state.searchResult.size) { index ->
                    val article = vm.state.searchResult[index]
                    if (index >= vm.state.searchResult.size - 1 && !vm.state.isEndReached && !vm.state.isPaginating) {
                        vm.loadArticles()
                    }

                    ArticleListItem(article = article, onSaveClicked = { vm.onEvent(SearchScreenEvent.OnSaveArticleClicked(index = index)) }) {
                        vm.onEvent(SearchScreenEvent.OnReadMoreClicked(index))
                    }
                }

                item {
                    AnimatedVisibility(visible = vm.state.isPaginating) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }

            }
        }

        AnimatedVisibility(
            visible = vm.state.searchText.isNotBlank() && vm.state.searchResult.isEmpty() && vm.state.error.isBlank() && !vm.state.isInitialLoadingInProgress,
            modifier = Modifier.constrainAs(infoMsg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)
                bottom.linkTo(searchBar.top)

                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = stringResource(id = R.string.no_result),
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
        }

        AnimatedVisibility(
            visible = vm.state.isInitialLoadingInProgress,
            modifier = Modifier.constrainAs(loadingIndicator) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(searchBar.top)
            }
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        AnimatedVisibility(
            visible = vm.state.error.isNotBlank(),
            modifier = Modifier.constrainAs(errorMsg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)
                bottom.linkTo(searchBar.top)

                width = Dimension.fillToConstraints
            }
        ) {
            Text(
                text = vm.state.error,
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
            )
        }


        AnimatedVisibility(
            visible = !showWebView,
            modifier = Modifier.constrainAs(searchBar) {
                bottom.linkTo(parent.bottom, 70.dp)
                start.linkTo(parent.start, 20.dp)
                end.linkTo(parent.end, 20.dp)

                width = Dimension.fillToConstraints
            }
        ) {
            TextField(
                value = vm.state.searchText,
                onValueChange = { vm.onEvent(SearchScreenEvent.OnSearchTextChanged(it)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedLeadingIconColor =  MaterialTheme.colorScheme.onSurfaceVariant,
                    unfocusedLeadingIconColor =  MaterialTheme.colorScheme.onSurfaceVariant
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
                },
                shape = RoundedCornerShape(15.dp),
                singleLine = true,
                textStyle = TextStyle(
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                ),
                placeholder = {
                    Text(
                        text = stringResource(id =  R.string.search_here),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        AnimatedVisibility(
            visible = showWebView,
            enter = slideInHorizontally(initialOffsetX = { 500 }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { 500 }) + fadeOut(),
            modifier = Modifier.constrainAs(webView) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            WebView(url = vm.currentUrl, isArticleSaved = vm.isArticleSaved) {
                vm.onEvent(SearchScreenEvent.OnSaveArticledClickedInWebView)
            }
        }
    }

    BackHandler(showWebView) {
        vm.onEvent(SearchScreenEvent.OnBackPressedOnWebView)
    }

}
