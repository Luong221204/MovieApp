package com.example.movieapp.MainActivity.screens.ExploreScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.domain.Country
import com.example.movieapp.ui.theme.BlackGray
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onHideBottomSheet:(String,List<String>,String,String)->Unit,
    sheetState: SheetState,
){
    val viewModel :FilterBottomViewModel = viewModel()
    val currentFilter=viewModel.currentFilter
    val yearSelected =viewModel.yearSelected
    val countryState =viewModel.countryState
    val sortState =viewModel.sortState
    val movieOption =viewModel.movieOption
    val cartoonOption  =viewModel.cartoonOption
    val tvOption  =viewModel.tvOption
    val seriesOption =viewModel.seriesOption

    val filterSheetState = rememberModalBottomSheetState()

    val scope = rememberCoroutineScope()
    val listCountry = viewModel.listCountry
    val listGenre = viewModel.listGenre

    val isShow = viewModel.isShow
    val hideBottomSheet:(String,String)->Unit ={
        nameFilter,data->
        scope.launch {
            filterSheetState.hide()
        }.invokeOnCompletion {
            viewModel.close(nameFilter,data)
            viewModel.optionFilter(nameFilter,data)
        }
    }


    val showBottomSheet:(String)->Unit={
        nameFilter->
        scope.launch {
            filterSheetState.show()
        }.invokeOnCompletion {
            viewModel.open(nameFilter)
        }
    }
    if(isShow){
        OptionBottomSheet(onHideBottomSheet= hideBottomSheet,filterSheetState,currentFilter,listCountry)
    }
    MovieAppTheme{
        ModalBottomSheet(
            modifier = Modifier.height(MovieAppTheme.blockDimension.b60).fillMaxWidth(),
            onDismissRequest = {
                onHideBottomSheet(currentFilter,viewModel.listGenreSelected,countryState,yearSelected)
            }
            ,
            sheetState = sheetState,
            dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Red.copy(alpha = MovieAppTheme.alpha.a8)) },
            containerColor = colorResource(R.color.blackBackground)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryOption(R.string.movies.toString(),movieOption) {
                        viewModel.changeOption(R.string.movies.toString())
                    }
                    CategoryOption(R.string.cartoons.toString(),cartoonOption) {
                        viewModel.changeOption(R.string.cartoons.toString())
                    }
                }
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CategoryOption(R.string.tv.toString(),tvOption) {
                        viewModel.changeOption(R.string.tv.toString())
                    }
                    CategoryOption(R.string.series.toString(),seriesOption) {
                        viewModel.changeOption(R.string.series.toString())
                    }
                }
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer0))
                SectionTitle(R.string.genre.toString(),false) { }
                ListGenre(listGenre,viewModel.listGenreSelected){
                        isSelected,title->
                    viewModel.onGenreClick(isSelected,title)
                }

                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer7))
                Filter(R.string.callendar.toString(),R.drawable.calendar,yearSelected) { showBottomSheet(R.string.callendar.toString()) }

                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                Filter(R.string.country.toString(),R.drawable.world,countryState) {  showBottomSheet(R.string.country.toString()) }

                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                Filter(R.string.sort_by.toString(),R.drawable.sort,sortState) {  }

                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                Box(
                    modifier = Modifier.height(MovieAppTheme.thinDimension.t2).fillMaxWidth().padding(horizontal = MovieAppTheme.paddingDimension.padding3).background(color = BlackGray)
                )
                Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer5))
                Row(
                    modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding3),
                    horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding5)
                ) {
                    ApplyButton(R.string.reset.toString(), BlackGray) { }
                    ApplyButton(R.string.apply.toString(), Color.Red.copy(alpha = MovieAppTheme.alpha.a8)) {
                        onHideBottomSheet(currentFilter,viewModel.listGenreSelected,countryState,yearSelected)
                    }

                }
            }
        }
    }

}

@Composable
fun ListGenre(list: List<String>,listGenreSelected:List<String>,onClick: (Boolean,String) -> Unit){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding3),
        contentPadding = PaddingValues(horizontal = MovieAppTheme.paddingDimension.padding3, vertical = MovieAppTheme.paddingDimension.padding1)
    ) {
        items(list){
            Genre(it,listGenreSelected) {
                isSelected,title
                ->onClick(isSelected,title)
            }
        }
    }
}

@Composable
fun CategoryOption(title:String,isSelected:Boolean,onClick:()->Unit){
    Box(
        modifier = Modifier
            .height(MovieAppTheme.viewDimension.v11)
            .width(MovieAppTheme.viewDimension.v13)
            .background(color = if(isSelected) Color.Red.copy(alpha =MovieAppTheme.alpha.a8)else BlackGray,
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .selectable(selected = isSelected, onClick = onClick, role = Role.RadioButton)
    ){
        Text(text = title,
           style = MovieAppTheme.appTypoTheme.t8,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ApplyButton(title:String,color: Color,onClick:()->Unit){
    Box(
        modifier = Modifier
            .height(MovieAppTheme.viewDimension.v14)
            .width(MovieAppTheme.viewDimension.v13)
            .background(color = color,
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
    ){
        Text(text = title,
            style = MovieAppTheme.appTypoTheme.t8,

            modifier = Modifier.align(Alignment.Center).clickable { onClick() })
    }
}
@Composable
fun Genre(title:String,listGenreSelected:List<String>,onClick:(Boolean,String)->Unit){
    var isSelected by remember {
        mutableStateOf(listGenreSelected.contains(title))
    }
    Box(
        modifier = Modifier
            .background(color = if(isSelected) Color.Red.copy(alpha = MovieAppTheme.alpha.a8)else Color.Transparent,
                shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .border(BorderStroke(width = MovieAppTheme.thinDimension.t2, color = BlackGray), shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20) )
            .clip(shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .selectable(
                selected = isSelected,
                onClick = {
                    isSelected=!isSelected
                    onClick(isSelected,title)
                },
                role = Role.Checkbox)
    ){
        Text(text = title,
            style = MovieAppTheme.appTypoTheme.t25,
            modifier = Modifier.padding(vertical =MovieAppTheme.paddingDimension.padding2, horizontal = MovieAppTheme.paddingDimension.padding3))
    }
}

@Composable
fun Filter(content:String,source:Int,data:String,onClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MovieAppTheme.viewDimension.v14)
            .clickable { onClick() }
            .padding(horizontal = MovieAppTheme.paddingDimension.padding3)
            .background(color = BlackGray, shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .clip(shape =RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20) )
    ){
       Row(
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.align(Alignment.TopStart).fillMaxHeight().padding(start = MovieAppTheme.paddingDimension.padding3)
       ){
           Icon(
               painter = painterResource(source),
               contentDescription = null,
               tint = Color.Red.copy(alpha = MovieAppTheme.alpha.a8),
               modifier = Modifier.size(MovieAppTheme.paddingDimension.padding5)
           )
           Spacer(modifier = Modifier.width(MovieAppTheme.spacerDimension.spacer3))
           Text(
               text = content,
             style = MovieAppTheme.appTypoTheme.t11
           )
       }
        Text(
            text = data,
            style = MovieAppTheme.appTypoTheme.t7,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = MovieAppTheme.paddingDimension.padding3)
        )
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionBottomSheet(
    onHideBottomSheet:(String,String)->Unit,
    sheetState: SheetState,
    content:String,
    list: List<Country>
){
    var output by remember {
        mutableStateOf("--/--")
    }
    ModalBottomSheet(
        dragHandle = {
            Box(
                modifier = Modifier.fillMaxWidth().padding(MovieAppTheme.paddingDimension.padding5)
            ){
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.size(MovieAppTheme.iconDimension.i3).align(Alignment.CenterEnd),
                    tint = Color.Red
                )
            }
        },
        sheetState = sheetState,
        modifier = Modifier.height(MovieAppTheme.blockDimension.b30),
        onDismissRequest = {onHideBottomSheet(content,output)},
        containerColor = colorResource(R.color.blackBackground)

    ) {
       when(content){
           "Release"-> ReleaseFilter {
               output=it
           }
           "Country"-> CountryFilter(list) {
               output=it
           }
       }

    }
}

@Composable
fun ReleaseFilter(onClick: (String) -> Unit){
    var numberSelected by remember {
        mutableIntStateOf(-1)
    }
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        repeat(6){
            Year(2020+it,numberSelected) {
                    it->
                numberSelected= it
                onClick(it.toString())
            }
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer1))
        }
    }
}

@Composable
fun CountryFilter(list:List<Country>,onClick: (String) -> Unit){
    FlagsInRow(list){
        onClick(it)
    }


}


@Composable
fun Year(
    number:Int,
    numberSelected:Int,
    onClick: (Int) -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(MovieAppTheme.viewDimension.v11)
            .clickable { onClick(number) }
            .padding(horizontal = MovieAppTheme.paddingDimension.padding3)
            .background(color = if(number == numberSelected) Color.Red.copy(alpha = MovieAppTheme.alpha.a8)else BlackGray
                , shape = RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20))
            .clip(shape =RoundedCornerShape(MovieAppTheme.roundedCornerDimension.r20) )
    ){
        Text(text  = "$number",
            modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding3, vertical = MovieAppTheme.paddingDimension.padding3),
            style = MovieAppTheme.appTypoTheme.t25
            )
    }
}


@Composable
fun FlagsInRow(list: List<Country>,onClick: (String) -> Unit){
    var numberSelected by remember {
        mutableIntStateOf(-1)
    }
    Column {
        list.CustomForEach(4) {
            list->
            Row(
                horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding5),
                modifier = Modifier.padding(horizontal = MovieAppTheme.paddingDimension.padding3)
            ){
                repeat(list.size){
                    Flag(list[it],numberSelected) {
                        numberSelected=it.id
                        onClick(it.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(MovieAppTheme.spacerDimension.spacer3))
        }
    }


}

@Composable
fun Flag(country:Country,isSelected: Int,onClick: (Country) -> Unit){
    Box(
        modifier = Modifier
            .height(MovieAppTheme.viewDimension.v11).width(MovieAppTheme.viewDimension.v15).border(
            BorderStroke(width = MovieAppTheme.thinDimension.t2, color = if(country.id==isSelected) Color.White else Color.Transparent)
        )){
        Image(
            painter = painterResource(country.id),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clickable { onClick(country) }
        )
    }

}