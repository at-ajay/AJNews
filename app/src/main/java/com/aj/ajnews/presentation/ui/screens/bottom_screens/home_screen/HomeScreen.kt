package com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(vm: HomeViewModel = hiltViewModel()) {

    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = vm.state.isInitialLoadingInProgress)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(if (showWebView) 0.dp else 20.dp)
    ) {
        val (appName, newsContent, errorMsg, loadingIndicator, webView) = createRefs()

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.SemiBold)
                ) {
                    append(stringResource(id = R.string.brand_name))
                    append(" ")
                }
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.Medium)
                ) {
                    append(stringResource(id = R.string.app_category))
                }
            },
            fontFamily = Rubik,
            fontSize = 22.sp,
            modifier = Modifier.constrainAs(appName) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        
        AnimatedVisibility(
            visible = !vm.state.isInitialLoadingInProgress && vm.state.error.isBlank() && !showWebView,
            modifier = Modifier.constrainAs(newsContent) {
                top.linkTo(appName.bottom, 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, 50.dp)

                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        ) {
            SwipeRefresh(
                state = swipeRefreshState,
                onRefresh = { vm.refresh() },
                indicator = { state, refreshTrigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = refreshTrigger,
                        backgroundColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        largeIndication = true
                    )
                }
            ) {
                LazyColumn {
                    item {
                        Text(
                            text = stringResource(id = R.string.top_picks),
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 15.dp)
                        )
                    }

                    item {
                        LazyRow(modifier = Modifier.padding(1.dp)) {
                            items(vm.state.topPicksArticles.size) { index ->
                                TopPicksListItem(article = vm.state.topPicksArticles[index]) {
                                    vm.onEvent(HomeScreenEvent.OnTopPickArticleClicked(index))
                                }
                            }
                        }
                    }

                    item {
                        Text(
                            text = stringResource(id = R.string.trending_now) + " ${vm.currentCountry}",
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(top = 20.dp, bottom = 15.dp)
                        )
                    }

                    items(vm.state.trendingArticles.size) { index ->
                        val article = vm.state.trendingArticles[index]
                        if (index >= vm.state.trendingArticles.size - 1 && !vm.state.isEndReached && !vm.state.isPaginating) {
                            vm.loadArticles()
                        }

                        ArticleListItem(article = article, onSaveClicked = { vm.onEvent(HomeScreenEvent.OnSaveArticleClicked(index = index)) }) {
                            vm.onEvent(HomeScreenEvent.OnReadMoreClicked(index))
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
        }

        AnimatedVisibility(
            visible = vm.state.isInitialLoadingInProgress,
            modifier = Modifier.constrainAs(loadingIndicator) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
            }
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
        }

        AnimatedVisibility(
            visible = vm.state.error.isNotBlank(),
            modifier = Modifier.constrainAs(errorMsg) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom)
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
                vm.onEvent(HomeScreenEvent.OnSaveArticledClickedInWebView)
            }
        }
    }

    BackHandler(showWebView) {
        vm.onEvent(HomeScreenEvent.OnBackPressedOnWebView)
    }

}

@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}