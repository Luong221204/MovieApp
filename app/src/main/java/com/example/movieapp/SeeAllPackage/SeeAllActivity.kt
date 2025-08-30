package com.example.movieapp.SeeAllPackage

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.example.movieapp.BaseActivity
import com.example.movieapp.DetailFimActivity.DetailMovieActivity
import com.example.movieapp.DetailFimActivity.MoreLikeThis
import com.example.movieapp.MainActivity.BottomNavBar.BottomNavigationBar
import com.example.movieapp.MainActivity.SectionTitle
import com.example.movieapp.R
import com.example.movieapp.domain.FilmItemModel.FilmItemModel
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.launch

class SeeAllActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data= intent.getStringExtra("title")
        if(data != null){
            val viewmodel :SeeAllViewmodel = ViewModelProvider(
                this,
                SeeAllFactory(data)
            )[SeeAllViewmodel::class.java]
            setContent {
                MovieAppTheme{
                    SeeAllScreen(data,viewmodel,::onFilmClick) {
                        finish()
                    }
                }

            }
        }

    }
    private fun onFilmClick(filmItemModel: FilmItemModel){
        val intent= Intent(this, DetailMovieActivity::class.java)
        intent.putExtra("object",filmItemModel)
        startActivity(intent)
    }
}

@Composable
fun SeeAllScreen(title:String,viewmodel: SeeAllViewmodel,onFilmClick:(FilmItemModel)->Unit,onBackClick:()->Unit){
    val films by viewmodel.films.collectAsState()
    Column(
        modifier = Modifier.background(color = colorResource(R.color.blackBackground)).fillMaxSize()
    ){
        TopBarTitle(title,onBackClick)
        MoreLikeThis(films,onFilmClick)
    }
}

@Composable
fun TopBarTitle(title:String,onBackClick:()->Unit){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MovieAppTheme.paddingDimension.padding7 ),
        modifier = Modifier.padding(top =MovieAppTheme.paddingDimension.padding11, start = MovieAppTheme.paddingDimension.padding3, bottom = MovieAppTheme.paddingDimension.padding0)
    ){
        Image(
            painter = painterResource(R.drawable.back),
            contentScale = ContentScale.Crop,
            contentDescription = null,
            modifier = Modifier.size(MovieAppTheme.viewDimension.v7).clickable { onBackClick() }
        )
        Text(
            text = title,
            style = MovieAppTheme.appTypoTheme.t13
        )
    }
}
