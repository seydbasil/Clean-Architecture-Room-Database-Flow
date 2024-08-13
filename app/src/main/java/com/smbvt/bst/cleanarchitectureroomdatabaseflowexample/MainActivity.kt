package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.db_viewer.DBViewActivity
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.ui.theme.CleanArchitectureRoomDatabaseFlowExampleTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    https://picsum.photos/200/300
//    https://picsum.photos/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val viewModel: MainActivityViewModel = hiltViewModel()

            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(key1 = Unit, block = {

                viewModel.uiEvents.flowWithLifecycle(
                    lifecycleOwner.lifecycle, minActiveState = Lifecycle.State.STARTED
                ).onEach {
                    when (it) {
                        is MainActivityViewModel.UIEvent.FinishActivity -> {
                            finish()
                        }

                        is MainActivityViewModel.UIEvent.ShowToast -> {
                            Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }.launchIn(this)


                viewModel.insertPhoto("test")
                startActivity(Intent(this@MainActivity, DBViewActivity::class.java))
            })

            CleanArchitectureRoomDatabaseFlowExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}
