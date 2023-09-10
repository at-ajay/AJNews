package com.aj.ajnews.presentation.ui.screens.bottom_screens.saved_articles_screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ClearAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun SavedArticlesScreen(vm: SavedArticlesViewModel = hiltViewModel()) {

    val articles = vm.savedArticles.collectAsState(initial = listOf())

    BackHandler(showWebView) {
        vm.onEvent(SavedArticlesEvent.OnBackPressedOnWebView)
    }

    Box(modifier = Modifier.fillMaxSize()) {

        AnimatedVisibility(visible = articles.value.isNotEmpty(), modifier = Modifier.fillMaxSize()) {

            ConstraintLayout(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                val (deleteAllBtn, articleList) = createRefs()

                Surface(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(20.dp),
                    modifier = Modifier
                        .clickable(onClick = { vm.onEvent(SavedArticlesEvent.OnDeleteAllClicked) })
                        .constrainAs(deleteAllBtn) {
                            top.linkTo(parent.top, 20.dp)
                            end.linkTo(parent.end, 20.dp)
                        }
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(
                            top = 10.dp,
                            start = 20.dp,
                            end = 20.dp,
                            bottom = 10.dp
                        )
                    ) {
                        Text(
                            text = stringResource(id = R.string.delete_all),
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Icon(
                            imageVector = Icons.Rounded.ClearAll,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                LazyColumn(modifier = Modifier.constrainAs(articleList) {
                    top.linkTo(deleteAllBtn.bottom, 20.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom, 50.dp)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }) {
                    items(articles.value) { article ->
                        ArticleListItem(article = article.toArticleInfo(), onSaveClicked = { vm.onEvent(SavedArticlesEvent.OnDeleteArticleClicked(article)) }) {
                            vm.onEvent(SavedArticlesEvent.OnReadMoreClicked(article.url))
                        }
                    }
                }
            }

        }

        AnimatedVisibility(
            visible = showWebView,
            enter = slideInHorizontally(initialOffsetX = { 500 }) + fadeIn(),
            exit = slideOutHorizontally(targetOffsetX = { 500 }) + fadeOut(),
            modifier = Modifier.fillMaxSize()
        ) {
            WebView(isSaveBtnVisible = false, url = vm.currentUrl, isArticleSaved = true) {

            }
        }

        AnimatedVisibility(visible = articles.value.isEmpty(), modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.il_no_data),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(start = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = stringResource(id = R.string.no_data_available),
                        fontSize = 20.sp,
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

    }

}

@Preview(showSystemUi = true)
@Composable
fun SavedArticlesScreenPreview() {
    SavedArticlesScreen()
}