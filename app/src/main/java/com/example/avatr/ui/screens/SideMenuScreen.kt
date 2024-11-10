package com.example.avatr.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.avatr.R
import kotlinx.coroutines.launch

@Composable
fun SideMenuScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var isDarkModeEnabled by remember { mutableStateOf(false) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {

           ModalDrawerSheet(
               modifier = Modifier.fillMaxWidth(0.85f),
               drawerContainerColor = MaterialTheme.colorScheme.surface
           ) {
               Icon(painter = painterResource(R.drawable.splash_icon), contentDescription = null, modifier = Modifier
                   .size(125.dp)
                   .padding(start = 12.dp)
               )

               Row {
                   NavigationDrawerItem(

                       label = { Text("Dark Mode", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.W900, color = MaterialTheme.colorScheme.onPrimary) },
                       selected = false,
                       icon = {
                           Icon(
                               painter = painterResource(R.drawable.mode_icon),
                               contentDescription = "App Theme",
                               tint = MaterialTheme.colorScheme.onPrimary
                           )
                       },
                       onClick = {
                           scope.launch{
                               drawerState.close()
                           }
                       }

                   )

                   Switch(
                       modifier = Modifier.scale(0.6f),
                       checked = isDarkModeEnabled,
                       onCheckedChange = {
                       isDarkModeEnabled = it
                       },
                       colors = SwitchDefaults.colors(
                           uncheckedThumbColor = Color.White,
                           uncheckedBorderColor = Color.Transparent,
                           uncheckedTrackColor = Color(0xffb1b7bc)
                       )
                   )
               }

               Row(
                   horizontalArrangement = Arrangement.SpaceBetween,
               ) {

                   NavigationDrawerItem(
                       label = { Text("About AVATR", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.W900, color = MaterialTheme.colorScheme.onPrimary)},
                       selected = false,
                       icon = {
                           Icon(
                               painter = painterResource(R.drawable.about_icon),
                               contentDescription = "App Theme",
                               tint = MaterialTheme.colorScheme.onPrimary
                           )
                       },
                       onClick = {
                           scope.launch{
                               drawerState.close()
                           }
                       }
                   )
                   
                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                       contentDescription = null,
                       tint = Color(0xff494d5a)
                   )

               }

               Row(
                   horizontalArrangement = Arrangement.SpaceBetween,
               ) {
                   NavigationDrawerItem(
                       label = { Text("Open Source Libraries", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.W900, color = MaterialTheme.colorScheme.onPrimary) },
                       selected = false,
                       icon = {
                           Icon(
                               painter = painterResource(R.drawable.open_source_icon),
                               contentDescription = "App Theme",
                               tint = MaterialTheme.colorScheme.onPrimary
                           )
                       },
                       onClick = {
                           scope.launch{
                               drawerState.close()
                           }
                       }
                   )

                   Icon(
                       imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                       contentDescription = null,
                       tint = Color(0xff494d5a)
                   )
               }
           }
        }
    ) {
        HomeScreen( scope, drawerState)
    }
}