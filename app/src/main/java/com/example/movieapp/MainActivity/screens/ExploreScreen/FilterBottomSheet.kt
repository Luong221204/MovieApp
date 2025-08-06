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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterBottomSheet(
    onHideBottomSheet:()->Unit,
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
    ModalBottomSheet(
        modifier = Modifier.height(650.dp).fillMaxWidth(),
        onDismissRequest = onHideBottomSheet ,
        sheetState = sheetState,
        dragHandle = { BottomSheetDefaults.DragHandle(color = Color.Red.copy(alpha = 0.8f)) },
        containerColor = colorResource(R.color.blackBackground)
    ) {
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryOption("Movies",movieOption) {
                   viewModel.changeOption("Movies")
                }
                CategoryOption("Cartoons",cartoonOption) {
                    viewModel.changeOption("Cartoons")
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                CategoryOption("Tv",tvOption) {
                    viewModel.changeOption("Tv")
                }
                CategoryOption("Series",seriesOption) {
                    viewModel.changeOption("Series")
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            SectionTitle("Genre",false) { }
            ListGenre(listGenre)

            Spacer(modifier = Modifier.height(32.dp))
            Filter("Release",R.drawable.calendar,yearSelected) { showBottomSheet("Release") }

            Spacer(modifier = Modifier.height(24.dp))
            Filter("Country",R.drawable.world,countryState) {  showBottomSheet("Country") }

            Spacer(modifier = Modifier.height(24.dp))
            Filter("Sort by",R.drawable.sort,sortState) {  }

            Spacer(modifier = Modifier.height(24.dp))
            Box(
                modifier = Modifier.height(1.dp).fillMaxWidth().padding(horizontal = 16.dp).background(color = BlackGray)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                ApplyButton("Reset", BlackGray) { }
                ApplyButton("Apply", Color.Red.copy(alpha = 0.8f)) { }

            }
        }
    }
}

@Composable
fun ListGenre(list: List<String>){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(list){
            Genre(it) { }
        }
    }
}

@Composable
fun CategoryOption(title:String,isSelected:Boolean,onClick:()->Unit){
    Box(
        modifier = Modifier
            .height(48.dp)
            .width(174.dp)
            .background(color = if(isSelected) Color.Red.copy(alpha = 0.8f)else BlackGray,
                shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .selectable(selected = isSelected, onClick = onClick, role = Role.RadioButton)
    ){
        Text(text = title,
            color = Color.White, fontSize = 14.sp, fontFamily = FontFamily.SansSerif,
            modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
fun ApplyButton(title:String,color: Color,onClick:()->Unit){
    Box(
        modifier = Modifier
            .height(56.dp)
            .width(174.dp)
            .background(color = color,
                shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
    ){
        Text(text = title,
            color = Color.White, fontSize = 14.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold,
            modifier = Modifier.align(Alignment.Center).clickable { onClick() })
    }
}
@Composable
fun Genre(title:String,isSelected:Boolean=false,onClick:()->Unit){
    Box(
        modifier = Modifier
            .background(color = if(isSelected) Color.Red.copy(alpha = 0.8f)else Color.Transparent,
                shape = RoundedCornerShape(20.dp))
            .border(BorderStroke(width = 1.dp, color = BlackGray), shape = RoundedCornerShape(20.dp) )
            .clip(shape = RoundedCornerShape(20.dp))
            .selectable(selected = isSelected, onClick = onClick, role = Role.Checkbox)
    ){
        Text(text = title,
            color = Color.White, fontSize = 13.sp, fontFamily = FontFamily.SansSerif,
            modifier = Modifier.padding(vertical =12.dp, horizontal = 16.dp))
    }
}

@Composable
fun Filter(content:String,source:Int,data:String,onClick:()->Unit){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clickable { onClick() }
            .padding(horizontal = 16.dp)
            .background(color = BlackGray, shape = RoundedCornerShape(20.dp))
            .clip(shape =RoundedCornerShape(20.dp) )
    ){
       Row(
           verticalAlignment = Alignment.CenterVertically,
           modifier = Modifier.align(Alignment.TopStart).fillMaxHeight().padding(start = 16.dp)
       ){
           Icon(
               painter = painterResource(source),
               contentDescription = null,
               tint = Color.Red.copy(alpha = 0.8f),
               modifier = Modifier.size(24.dp)
           )
           Spacer(modifier = Modifier.width(16.dp))
           Text(
               text = content,
               color = Color.White,
               fontSize = 12.sp,
               fontWeight = FontWeight.SemiBold
           )
       }
        Text(
            text = data,
            color = Color.White,
            fontSize = 12.sp,
            modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp)
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
                modifier = Modifier.fillMaxWidth().padding(24.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp).align(Alignment.CenterEnd),
                    tint = Color.Red
                )
            }
        },
        sheetState = sheetState,
        modifier = Modifier.height(300.dp),
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
            Spacer(modifier = Modifier.height(8.dp))
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
            .height(48.dp)
            .clickable { onClick(number) }
            .padding(horizontal = 16.dp)
            .background(color = if(number == numberSelected) Color.Red.copy(alpha = 0.8f)else BlackGray
                , shape = RoundedCornerShape(20.dp))
            .clip(shape =RoundedCornerShape(20.dp) )
    ){
        Text(text  = "$number", color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold)
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
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ){
                repeat(list.size){
                    Flag(list[it],numberSelected) {
                        numberSelected=it.id
                        onClick(it.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }


}

@Composable
fun Flag(country:Country,isSelected: Int,onClick: (Country) -> Unit){
    Box(
        modifier = Modifier
            .height(48.dp).width(72.dp).border(
            BorderStroke(width = 1.5.dp, color = if(country.id==isSelected) Color.White else Color.Transparent)
        )){
        Image(
            painter = painterResource(country.id),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clickable { onClick(country) }
        )
    }

}