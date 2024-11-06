package com.example.avatr.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.avatr.R

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.large_padding)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {

            Header(headerText = R.string.home_screen_header)

            ImageContainer()

            DescriptionTextField()

            AdvancedOptions()
        }

        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun Header(headerText: Int) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = "menu"
            )
        }
        Text(
            text = stringResource(headerText),
            style = MaterialTheme.typography.displayMedium,
            color = Color.Black
        )
    }
}

@Composable
fun DescriptionTextField() {
    OutlinedTextField(
        value = "",
        onValueChange = { },
        placeholder = { Text(stringResource(R.string.describe_your_ai_masterpiece),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a)) },
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodySmall,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color(0xffdfe0e0),
            unfocusedLeadingIconColor = Color(0xffdfe0e0),
            focusedLeadingIconColor = Color.Black,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = MaterialTheme.colorScheme.background,
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.upload_icon),
                    contentDescription = "upload"
                )
            }
        },

        trailingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.generate_icon),
                    contentDescription = "generate"
                )
            }
        },
        modifier = Modifier
            .width(
//                dimensionResource(id = R.dimen.button_width)
                450.dp
            )
            .height(
                dimensionResource(R.dimen.button_height)
            )
            .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp)),
        enabled = true,
        singleLine = true
    )
}

@Composable
fun ImageContainer() {
 Card(
     modifier = Modifier
         .fillMaxWidth()
         .border(2.dp, Color(0xffdfe0e0), shape = RoundedCornerShape(8.dp))
         .height(dimensionResource(id = R.dimen.button_width))
         .width(dimensionResource(id = R.dimen.button_width)),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
 ) {
     Column(
         modifier = Modifier.fillMaxSize(),
         verticalArrangement = Arrangement.Center,
         horizontalAlignment = Alignment.CenterHorizontally,
     ) {
         Image(
             painter = painterResource(R.drawable.brush_icon),
             contentDescription = null,
             contentScale = ContentScale.Crop
         )

         Text(text = stringResource(R.string.no_generated_image), style = MaterialTheme.typography.bodyMedium, color = Color(0xff747b82))
     }
    }

}

@Composable
fun AdvancedOptions() {

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .animateContentSize (
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessMedium
                )
            ),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.small_padding))
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.wrapContentWidth()
        ) {

            Text(
                text = stringResource(R.string.advanced_options),
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xff747b82)
            )

            IconButton(onClick = { expanded = !expanded}, Modifier.size(20.dp)) {
                Icon(
                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = stringResource(R.string.advanced_options),
                    tint = Color(0xff494d5a)
                )
            }
        }

        if(expanded) {
            OutlinedTextField(
                value = "",
                onValueChange = { },
                placeholder = { Text(stringResource(R.string.i_dont_wanna_see),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a)) },
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodySmall,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color(0xffdfe0e0),
                    unfocusedLeadingIconColor = Color(0xffdfe0e0),
                    focusedLeadingIconColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                ),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.exclude_icon),
                            contentDescription = "upload"
                        )
                    }
                },
                modifier = Modifier
                    .width(
//                dimensionResource(id = R.dimen.button_width)
                        450.dp
                    )
                    .height(
                        dimensionResource(R.dimen.button_height)
                    )
                    .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp)),
                enabled = true,
                singleLine = true
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.45f)
            .fillMaxHeight(0.3f)
            .clip(RoundedCornerShape(32.dp)),
        contentAlignment = Alignment.Center
    ) {

        NavigationBar(
            modifier = Modifier.wrapContentSize(),
            containerColor = Color.Black
        ) {
            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.home_icon),
                        contentDescription = "home"
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.collections_icon),
                        contentDescription = "collections"
                    )
                }
            )

            NavigationBarItem(
                selected = false,
                onClick = { /*TODO*/ },
                icon = {
                    Icon(
                        painter = painterResource(R.drawable.settings_icon),
                        contentDescription = "settings"
                    )
                }
            )
        }
    }
}