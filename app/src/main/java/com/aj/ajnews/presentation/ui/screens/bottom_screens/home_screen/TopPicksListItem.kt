package com.aj.ajnews.presentation.ui.screens.bottom_screens.home_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aj.ajnews.R
import com.aj.ajnews.util.ArticleInfo
import com.aj.ajnews.util.Constants.Rubik

@Composable
fun TopPicksListItem(
    article: ArticleInfo,
    onReadMoreClicked: () -> Unit
) {
    val displayMetrics = LocalContext.current.resources.displayMetrics
    val screenWidth =  (displayMetrics.widthPixels / displayMetrics.density) - 40

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        Card(
            shape = RoundedCornerShape(25.dp),
            elevation = CardDefaults.cardElevation(5.dp),
            modifier = Modifier
                .width(screenWidth.dp)
                .clickable(onClick = onReadMoreClicked)
        ) {
            if (article.imageUrl != null) {
                AsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.bg_page_not_found),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            ) {
                Text(
                    text = article.title,
                    fontFamily = Rubik,
                    fontWeight = FontWeight.Medium,
                    maxLines = 2,
                    modifier = Modifier.weight(10f),
                    textAlign = TextAlign.Start
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier
                        .size(10.dp)
                        .weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))
    }

}

@Preview
@Composable
fun TopPicksListItemPreview() {
    TopPicksListItem(
        article = ArticleInfo(
            description = "The Tesla Cybertruck production candidate made its debut in Texas this week when CEO Elon Musk took the electric truck for a spin sharing the drive on X, the platform formally known as Twitter. The reveal marks a milestone for Tesla after Musk, last year, sai…",
            title = "Elon Musk's Tesla Cybertruck: 5 fast facts",
            url = "",
            imageUrl = "",
            isSaved = remember {
                mutableStateOf(false)
            }
        )
    ) {
        
    }
}