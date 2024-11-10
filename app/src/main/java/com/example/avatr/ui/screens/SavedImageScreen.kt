package com.example.avatr.ui.screens

import android.annotation.SuppressLint
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.avatr.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)


@Composable
fun SavedImageScreen(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 15.dp, bottom = 0.dp, end = 15.dp, top = 40.dp)
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {

            SavedImageTopAppBar()

            FirstColumn()

            HorizontalDivider(color = Color(0xffdfe0e0))


            SecondColumn()
        }

        BottomNavigationBar()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FirstColumn() {

    Column(
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.Start
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.45f)
                .border(2.dp, Color.Transparent),
            shape = RoundedCornerShape(5.dp)
        ) {
            Image(
                painter = painterResource(R.drawable._4),
                contentDescription = "Generated Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
        Text(text = "White Modern Cyborg Wearing a Helmet \nin a Dramatic Shot", style = MaterialTheme.typography.labelMedium)
        Text(text = "Saved on: 16th April 2024", style = MaterialTheme.typography.labelSmall, color = Color(0xff494d5a))
    }
}

@Composable
private fun SecondColumn() {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.Start
    ) {
       Row(
           horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
       ){
           Icon(painterResource(R.drawable.twitter_icon), contentDescription = "twitter", tint = Color(0xff747b82), modifier = Modifier.size(18.dp))
           Text(text = "Share on Twitter", style = MaterialTheme.typography.labelSmall)
       }
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {
            Icon(painterResource(R.drawable.remove_collections), contentDescription = "remove from collection", tint = Color(0xff747b82),  modifier = Modifier.size(18.dp))
            Text(text = "Remove from Collection", style = MaterialTheme.typography.labelSmall)
        }
        Button(imageVector = ImageVector.vectorResource(R.drawable.save_icon), text = R.string.save_to_device)
    }
}

@Composable
private fun SavedImageTopAppBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        Box(
            modifier = Modifier
                .size(14.dp) // set the desired size for your icon
                .clickable { /* handle click here */ }
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.arrowback_icon),
                contentDescription = "Your icon",
                modifier = Modifier.fillMaxSize()
            )
        }
        Text("Saved Image", style =  MaterialTheme.typography.labelLarge)
    }
}

@Composable
private fun Button(imageVector: ImageVector, text: Int) {
    androidx.compose.material3.Button(
        onClick = { /* Handle click */ },
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.primary
        ),
        shape = RoundedCornerShape(8.dp)
    ) {


        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.images_padding))
        ) {

            Icon(imageVector = imageVector, contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(20.dp))
            Text(stringResource(text), style = MaterialTheme.typography.bodySmall)

        }
    }
}

@Composable
@Preview
fun preview() {
    SavedImageScreen()
}