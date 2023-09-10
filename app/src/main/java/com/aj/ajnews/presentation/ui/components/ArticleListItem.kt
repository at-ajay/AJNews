package com.aj.ajnews.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.aj.ajnews.R
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Constants.Rubik
import kotlinx.coroutines.delay

@Composable
fun ArticleListItem(
    article: ArticleInfo,
    onSaveClicked: () -> Unit,
    onReadMoreClicked: () -> Unit
) {

    var isClicked by remember { mutableStateOf(false) }
    var showPreview by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isClicked) {
        showPreview = if (isClicked) {
            delay(300)
            true
        } else {
            false
        }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable { isClicked = !isClicked }
    ) {

        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp)
        ) {
            val (bannerImg, title, description, saveBtn) = createRefs()

            this@Card.AnimatedVisibility(
                visible = !isClicked,
                modifier = Modifier
                    .size(65.dp)
                    .clip(shape = RoundedCornerShape(20.dp))
                    .constrainAs(bannerImg) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200), initialOffsetX = { -300 }) + fadeIn(),
                exit = slideOutHorizontally(animationSpec = tween(durationMillis = 200), targetOffsetX = { -300 }) + fadeOut()
            ) {
                if (article.imageUrl != null) {
                    AsyncImage(
                        model = article.imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.bg_page_not_found),
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds
                    )
                }
            }

            Text(
                text = article.title,
                fontFamily = Rubik,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                textAlign = if (isClicked) TextAlign.Center else TextAlign.Start,
                modifier = Modifier
                    .animateContentSize(animationSpec = tween(durationMillis = 200))
                    .constrainAs(title) {
                        top.linkTo(parent.top)
                        if (!isClicked) {
                            bottom.linkTo(parent.bottom)
                        }

                        start.linkTo(bannerImg.end, 15.dp)
                        end.linkTo(saveBtn.start, 15.dp)

                        width = Dimension.fillToConstraints
                    }
            )

            this@Card.AnimatedVisibility(
                visible = showPreview,
                modifier = Modifier
                    .animateContentSize()
                    .constrainAs(description) {
                        top.linkTo(title.bottom, 15.dp)
                        start.linkTo(title.start)
                        end.linkTo(title.end)

                        width = Dimension.fillToConstraints
                    },
                enter = expandVertically()
            ) {
                Column {

                    Text(
                        text = article.description ?: stringResource(id = R.string.no_description),
                        fontFamily = Rubik,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = onReadMoreClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier.align(alignment = Alignment.End)
                    ) {
                        Text(
                            text = stringResource(id = R.string.read_more),
                            fontFamily = Rubik,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.width(10.dp))

                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                            contentDescription = null,
                            modifier = Modifier.size(10.dp)
                        )
                    }
                }
            }

            this@Card.AnimatedVisibility(
                visible = !isClicked,
                modifier = Modifier
                    .size(30.dp)
                    .constrainAs(saveBtn) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                enter = slideInHorizontally(animationSpec = tween(durationMillis = 200), initialOffsetX = { 300 }) + fadeIn(),
                exit = slideOutHorizontally(animationSpec = tween(durationMillis = 200), targetOffsetX = { 300 }) + fadeOut()
            ) {
                Icon(
                    imageVector = if (article.isSaved.value) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkBorder,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(
                            onClick = onSaveClicked,
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ),
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }

    }

    Spacer(modifier = Modifier.height(20.dp))

}

@Preview
@Composable
fun ArticleListItemPreview() {
    ArticleListItem(
        ArticleInfo(
            description = "The Tesla Cybertruck production candidate made its debut in Texas this week when CEO Elon Musk took the electric truck for a spin sharing the drive on X, the platform formally known as Twitter. The reveal marks a milestone for Tesla after Musk, last year, sai…",
            title = "Elon Musk's Tesla Cybertruck: 5 fast facts",
            url = "",
            imageUrl = "",
            isSaved = remember {
                mutableStateOf(false)
            }
        ),
        onSaveClicked = {

        },
        onReadMoreClicked = {

        }
    )
}