package com.example.avatr.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.avatr.R
import com.example.avatr.ui.navigation.AvatrNavHost
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.Q)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun SideMenuScreen(
    isDarkTheme: MutableState<Boolean>
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

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
               Row(horizontalArrangement = Arrangement.spacedBy(30.dp)) {
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
                       },
                       badge = {
                           Switch(
                               modifier = Modifier.scale(0.6f),
                               checked = isDarkTheme.value,
                               onCheckedChange = {
                                   run { isDarkTheme.value = !isDarkTheme.value }
                               },
                               colors = SwitchDefaults.colors(
                                   uncheckedThumbColor = Color.White,
                                   uncheckedBorderColor = Color.Transparent,
                                   uncheckedTrackColor = Color(0xffb1b7bc),
                                   checkedBorderColor = MaterialTheme.colorScheme.onPrimary
                               )
                           )
                       }
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
                               modifier = Modifier.padding(0.dp),
                               contentDescription = "App Theme",
                               tint = MaterialTheme.colorScheme.onPrimary
                           )
                       },
                       onClick = {
                           scope.launch{
                               drawerState.close()
                           }
                       },
                       badge = {
                           IconButton(
                               onClick = {}
                           ) {
                              Icon(
                                  imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                  contentDescription = null,
                                  tint = Color(0xffb1b7bc)
                              )
                           }
                       }
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
                       },
                       badge = {
                           IconButton(
                               onClick = {}
                           ) {
                               Icon(
                                   imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                   contentDescription = null,
                                   tint = Color(0xffb1b7bc)
                               )
                           }
                       }
                   )
               }
           }
        }
    ) {
        AvatrNavHost(navController, drawerState, scope)
    }
}