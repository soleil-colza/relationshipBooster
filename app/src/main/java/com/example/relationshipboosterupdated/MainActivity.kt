@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.relationshipboosterupdated
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.foundation.layout.*
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.example.relationshipboosterupdated.ui.theme.RelationshipBoosterUpdatedTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RelationshipBoosterUpdatedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                ) {
                    RelationshipBoosterApp()
                }
            }
        }
    }
}

@Composable
fun RelationshipBoosterApp() {
    NavGraph()
}

//Screens
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController?) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController?.navigate("message")},
            modifier = Modifier
                    .size(65.dp),
                shape = MaterialTheme.shapes.large, // ボタンの形状を設定
                contentColor = Color.White, // テキストとアイコンの色を設定
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add"
                )
            }
        }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            ) {
            TopBar()
            Tabs()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageComposeScreen(navController: NavController?) {

    var message by remember { mutableStateOf("") }

    Column {
        CenterAlignedTopAppBar(
            title = { Text("Relationship Booster")},
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = colorScheme.primaryContainer,
                titleContentColor = colorScheme.primary,
            ),
            modifier = Modifier
                .fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = { navController?.navigateUp() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )
        MessageInputField()
    }
}


//Screen components
@Composable
fun Tabs(
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    val tabs = listOf("あなた", "パートナー")
    var selectedTab by remember { mutableStateOf(0) }

    TabRow(
        selectedTabIndex = selectedTab,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(50.dp),
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { selectedTab = index }
            ) {
                Text(title, fontSize = 20.sp)
            }
        }
    }

    when (selectedTab){
        0 -> PostsList() // "あなた" tab
        1 -> PostsList() // "パートナー" tab
    }
}

@Composable
fun PostsList(
    align: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = align,
    ){
        items(30){ //あとで実際のPostに変更
            PostCard()
        }
    }
}
@Composable
fun PostCard() {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp,
        ),
        modifier = Modifier
            .size(width = 360.dp, height = 160.dp)
            .padding(10.dp),
    ) {
        Text(
            text = "パートナーからのメッセージを表示",
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colorScheme.primaryContainer,
                    titleContentColor = colorScheme.primary,
                ),
                title = {
                    Text(
                        "Relationship Booster",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "ハンバーガーメニュー"
                        )
                    }
                },
            )
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageInputField() {
    val inputValue = rememberSaveable { mutableStateOf("") }
    OutlinedTextField(
        value = inputValue.value,
        onValueChange = { inputValue.value = it },// ラムダ式の引数はitで受け取れる
        label = { Text(text ="パートナーに今日のありがとうを伝えましょう❤️") },
        modifier = Modifier
            .padding(16.dp)
            .width(400.dp)
            .height(100.dp),
    )
}

@Composable
fun NavGraph(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home"){

        composable("home"){
            HomeScreen(navController)
        }

        composable("message"){
            MessageComposeScreen(navController)
        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RelationshipBoosterUpdatedTheme {
        RelationshipBoosterApp()
    }
}