package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.db_viewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.RoomDatabase
import com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.data.data_source.local.db.PhotosDatabase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

sealed class VisibleScreen {
    data object TableListView : VisibleScreen()
    data object TableView : VisibleScreen()
}

@AndroidEntryPoint
class DBViewActivity : ComponentActivity() {

    @Inject
    lateinit var database: PhotosDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tableList = getTableList(database)
        var columns = listOf<String>()
        var tableData: List<TableRow> = listOf()


        setContent {

            var visibleScreen: VisibleScreen by remember {
                mutableStateOf(VisibleScreen.TableListView)
            }

            DBViewScreen(tableList = tableList,
                columns = columns,
                tableData = tableData,
                updateColumnList = {
                    columns = getColumnList(database, it)
                },
                updateTableData = {
                    tableData = getTableData(database, it)
                },
                visibleScreen = visibleScreen,
                updateVisibleScreen = {
                    visibleScreen = it
                })
        }
    }
}

@Composable
fun DBViewScreen(
    tableList: List<String> = listOf(),
    columns: List<String> = listOf(),
    tableData: List<TableRow> = listOf(),
    updateColumnList: (name: String) -> Unit = {},
    updateTableData: (name: String) -> Unit = {},
    visibleScreen: VisibleScreen = VisibleScreen.TableListView,
    updateVisibleScreen: (visibleScreen: VisibleScreen) -> Unit = {},
) {

    when (visibleScreen) {
        is VisibleScreen.TableListView -> {
            ListView(modifier = Modifier.fillMaxSize(), tableList = tableList, onClickItem = {
                updateColumnList(it)
                updateTableData(it)
                updateVisibleScreen(VisibleScreen.TableView)
            })
        }

        is VisibleScreen.TableView -> {
            TableDataGrid(columns, tableData, onBackPress = {
                updateVisibleScreen(VisibleScreen.TableListView)
            })
        }
    }
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewDBViewScreenTables() {
    DBViewScreen(
        tableList = listOf(
            "table 1",
            "table 2",
            "table 3",
            "table 4",
            "table 5",
            "table 6",
        )
    )
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewDBViewScreenData() {
    DBViewScreen(
        tableList = listOf(
            "table 1",
            "table 2",
            "table 3",
            "table 4",
            "table 5",
            "table 6",
        ),
        visibleScreen = VisibleScreen.TableView,
        columns = listOf("Column 1", "Column 2", "Column 3", "Column 4", "Column 5"),
        tableData = listOf(
            TableRow(
                columns = listOf(
                    "item 1",
                    "item 2",
                    "item 3",
                    "item 4",
                    "item 5",
                )
            ),
            TableRow(
                columns = listOf(
                    "item 1",
                    "item 2",
                    "item 3",
                    "item 4",
                    "item 5",
                )
            ),
            TableRow(
                columns = listOf(
                    "item 1",
                    "item 2",
                    "item 3",
                    "item 4",
                    "item 5",
                )
            ),
            TableRow(
                columns = listOf(
                    "item 1",
                    "item 2",
                    "item 3",
                    "item 4",
                    "item 5",
                )
            ),
            TableRow(
                columns = listOf(
                    "item 1",
                    "item 2",
                    "item 3",
                    "item 4",
                    "item 5",
                )
            ),
        )
    )
}

@Composable
fun ListView(
    modifier: Modifier = Modifier, tableList: List<String>, onClickItem: (tableName: String) -> Unit
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .padding(5.dp),
            text = "Tables",
            style = TextStyle(color = Color.White)
        )
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .weight(1f), content = {
            items(tableList.size, itemContent = {
                Text(modifier = Modifier
                    .clickable {
                        onClickItem(tableList[it])
                    }
                    .padding(10.dp), text = tableList[it])
            })
        })
    }
}


@Composable
fun TableDataGrid(
    columns: List<String>, tableData: List<TableRow>, onBackPress: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .padding(2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier.clickable(onClick = onBackPress)
            )
            Spacer(modifier = Modifier.size(20.dp))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                text = "Table content (columns and rows)",
                style = TextStyle(color = Color.White)
            )
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0x30000000))
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(columns.size) { columnIndex ->
                Text(
                    text = columns[columnIndex], fontSize = 16.sp, modifier = Modifier.padding(8.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            items(tableData.size) { rowIndex ->
                TableRowComposable(row = tableData[rowIndex])
            }
        }
    }

}

@Composable
fun TableRowComposable(row: TableRow) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(row.columns.size) { columnIndex ->
            Text(
                text = row.columns[columnIndex], fontSize = 16.sp, modifier = Modifier.padding(8.dp)
            )
        }
    }
}

fun getTableList(db: RoomDatabase): List<String> {
    val tableList = mutableListOf<String>()
    val readableDB = db.openHelper.readableDatabase

    val cursor =
        readableDB.query("SELECT name FROM sqlite_master WHERE type='table'", arrayOf<Any>())
    cursor.use {
        if (it.moveToFirst()) {
            do {
                tableList.add(it.getString(0))
            } while (it.moveToNext())
        }
    }

    readableDB.close()
    return tableList
}

fun getColumnList(db: RoomDatabase, tableName: String): List<String> {
    val columnList = mutableListOf<String>()
    val readableDB = db.openHelper.readableDatabase


    val cursor = readableDB.query("PRAGMA table_info($tableName)", arrayOf())
    cursor.use {
        if (it.moveToFirst()) {
            val nameIndex = it.getColumnIndex("name")
            do {
                columnList.add(it.getString(nameIndex))
            } while (it.moveToNext())
        }
    }

    db.close()
    return columnList
}

fun getTableData(db: RoomDatabase, tableName: String): List<TableRow> {
    val readableDB = db.openHelper.readableDatabase
    val tableData = mutableListOf<TableRow>()

    val cursor = readableDB.query("SELECT * FROM $tableName", arrayOf())
    cursor.use {
        if (it.moveToFirst()) {
            val columnNames = it.columnNames
            do {
                val columns =
                    columnNames.map { columnName -> it.getString(it.getColumnIndexOrThrow(columnName)) }
                tableData.add(TableRow(columns))
            } while (it.moveToNext())
        }
    }

    db.close()
    return tableData
}

data class TableRow(val columns: List<String>)
