package com.example.movieapp.DetailFimActivity

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.domain.CastModel
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import kotlinx.coroutines.launch

sealed class TabLayoutItem(
    val title:String
){
    data object MoreLikeThis : TabLayoutItem( title = "More like this")
    data object About : TabLayoutItem( title = "About")
    data object Comment : TabLayoutItem( title = "Comment")
    data object Filmography : TabLayoutItem( title = "Filmography")
    data object Biography : TabLayoutItem( title = "Biography")


}


@Composable
fun TabLayout(
    modifier: Modifier,
    viewmodel: ViewModel?,
    listTabs:List<TabLayoutItem>,
    setUpTabLayouts:@Composable (ViewModel?,Int)->Unit
){
    val scope= rememberCoroutineScope()
    val pagerState = rememberPagerState(pageCount = {listTabs.size})
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }
    Scaffold(
        modifier = modifier
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TabRow(
                containerColor = colorResource(R.color.blackBackground),
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.blackBackground))
                    .padding(horizontal = 16.dp),
                indicator = {
                    tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier
                            .tabIndicatorOffset(tabPositions[selectedTabIndex.value]),
                        color = Color.Red,
                        height = 3.dp
                    )
                }
            ) {
                listTabs.forEachIndexed{
                    index,currentTab->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = Color.White.copy(alpha = 0.8f),
                        unselectedContentColor = Color.White.copy(alpha = 0.1f),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {Text(text = currentTab.title , style = TextStyle(
                            fontFamily = FontFamily.SansSerif, fontSize = 14.sp, fontWeight = FontWeight.Bold
                        ))},

                    )
                }
            }
            HorizontalPager(
                state=pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = colorResource(R.color.blackBackground))
            ) {
                page->
                setUpTabLayouts(viewmodel,page)
            }
        }
    }
}
