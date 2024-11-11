package com.example.avatr.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.rememberDrawerState
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.avatr.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navigateToHome: () -> Unit,
    navigateToCollections: () -> Unit,
    navigateToSettings: () -> Unit,
) {

    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val navController = rememberNavController()

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
                .fillMaxHeight(0.9f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.large_padding))
        ) {

            Header(scope, headerText = R.string.home_screen_header, drawerState)

            ImageContainer()

            DescriptionTextField()

            AdvancedOptions()
        }

        BottomNavigationBar(navController, navigateToHome, navigateToCollections, navigateToSettings)
    }
}

@Composable
fun Header(scope: CoroutineScope, headerText: Int, drawerState: DrawerState) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        IconButton(
            modifier = Modifier.size(30.dp),
            onClick = {
            scope.launch{ drawerState.open() } }
        ) {
            Icon(
                painter = painterResource(R.drawable.menu_icon),
                contentDescription = "menu"
            )
        }
        Text(
            text = stringResource(headerText),
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.onPrimary,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )
    }
}

@Composable
private fun DescriptionTextField() {
    var text by remember {
        mutableStateOf("")
    }

    var isFocused by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text(stringResource(R.string.describe_your_ai_masterpiece),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a)) },
        shape = RoundedCornerShape(8.dp),
        textStyle = MaterialTheme.typography.bodySmall,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        ),
        leadingIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(R.drawable.upload_icon),
                    contentDescription = "upload",
                    tint = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
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
            .width(450.dp)
            .height(dimensionResource(R.dimen.button_height))
            .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
            .onFocusChanged { focusState -> isFocused = focusState.isFocused },
        enabled = true,
        singleLine = true
    )
}

@Composable
private fun ImageContainer() {
 Card(
     modifier = Modifier
         .fillMaxWidth()
         .border(2.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp))
         .height(dimensionResource(id = R.dimen.button_width))
         .width(dimensionResource(id = R.dimen.button_width)),
    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
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
private fun AdvancedOptions() {

    var text by remember {
        mutableStateOf("")
    }
    var expanded by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

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
                color = MaterialTheme.colorScheme.tertiary
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
                value = text,
                onValueChange = { text = it },
                placeholder = { Text(stringResource(R.string.i_dont_wanna_see),  style = MaterialTheme.typography.bodyMedium, color = Color(0xff494d5a)) },
                shape = RoundedCornerShape(8.dp),
                textStyle = MaterialTheme.typography.bodySmall,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                leadingIcon = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(R.drawable.exclude_icon),
                            contentDescription = "upload",
                            tint = if (isFocused) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
                        )
                    }
                },
                modifier = Modifier
                    .width(450.dp)
                    .height(dimensionResource(R.dimen.button_height))
                    .border(2.dp, Color.Transparent, shape = RoundedCornerShape(8.dp))
                    .onFocusChanged { focusState -> isFocused = focusState.isFocused },
                enabled = true,
                singleLine = true
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    navigateToHome: () -> Unit = {},
    navigateToCollections: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
) {

    val items = listOf(
        NavigationItem( "Home", R.drawable.home_icon, navigateToHome),
        NavigationItem( "Collections", R.drawable.collections_icon, navigateToCollections),
        NavigationItem( "Settings", R.drawable.settings_icon, navigateToSettings)
    )

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .background(Color.Transparent), // Transparent background for the outer box
        contentAlignment = Alignment.Center
    ) {
        // Pebble-like rounded background
        Surface(
            color = Color.Black,
            shape = RoundedCornerShape(30.dp), // Creates the rounded "pebble" effect
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.45f)
                .height(60.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { index, item ->
                    val isSelected = item.label == currentDestination
                    Icon(
                        painter = painterResource(
                            item.unselectedIcon
                        ),
                        contentDescription = item.label,
                        tint = if (isSelected) Color.White else Color.Gray,// Different tint for selected/unselected icons
                        modifier = Modifier
                            .clickable {
                                item.navigateTo()
                            }
                    )
                }
            }
        }
    }
}

data class NavigationItem(
    val label:String,
    val unselectedIcon: Int,
    val navigateTo: () -> Unit
)