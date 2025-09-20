package com.example.avatr.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.avatr.R

@Composable
fun CustomNavBar(
    navigateToHome: () -> Unit = {},
    currentScreen: String,
    navigateToCollections: () -> Unit = {},
    navigateToPreferences: () -> Unit = {},
) {
    val items = listOf(
        NavigationItem( "home", R.drawable.home_icon, R.drawable.home_icon_clicked, navigateToHome),
        NavigationItem( "collections", R.drawable.collections_icon, R.drawable.collection_icon_clicked , navigateToCollections),
        NavigationItem( "preferences", R.drawable.settings_icon, R.drawable.setting_icon_clicked, navigateToPreferences)
    )

    val savedImage = "saved_image/{savedPhotoId}"

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            tonalElevation = 5.dp,
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.45f)
                .height(45.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEachIndexed { _, item ->
                    val isSelected = (currentScreen == item.label) || ((currentScreen == savedImage) && item.label == "collections")
                    val scale by animateFloatAsState(
                        targetValue = if (isSelected) 1f else 0.9f,
                        animationSpec = tween(durationMillis = 200, easing = FastOutLinearInEasing),
                        label = ""
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(
                                if (isSelected) item.iconClicked else item.icon
                            ),
                            contentDescription = item.label,
                            tint = if (isSelected) Color.Unspecified else Color.Gray,
                            modifier = Modifier
                                .padding(vertical = 4.dp, horizontal = 6.dp)
                                .scale(scale)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    item.navigateTo()
                                }
                        )

                        AnimatedVisibility(
                            visible = isSelected,
                            enter = expandVertically(
                                expandFrom = Alignment.Bottom,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessVeryLow
                                )
                            ),
                            exit = shrinkVertically(
                                shrinkTowards = Alignment.Bottom,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessMedium
                                )
                            )
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.clicked_indicator),
                                tint = Color.White,
                                contentDescription = null,
                                modifier = Modifier.padding(0.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

data class NavigationItem(
    val label:String,
    val icon: Int,
    val iconClicked: Int,
    val navigateTo: () -> Unit
)