package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample.db_viewer

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
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

val Padding2 = 2.dp
val Padding5 = 5.dp
val Width3 = 3.dp
val Height3 = 3.dp
val Height1 = 1.dp
val Width1 = 1.dp
val Padding10 = 10.dp
val Spacer20 = 20.dp


@AndroidEntryPoint
class DBViewActivity : ComponentActivity() {

    @Inject
    lateinit var database: PhotosDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val tableList = getTableList(database)
        var tableData: List<List<String>> = listOf()

        setContent {

            var visibleScreen: VisibleScreen by remember {
                mutableStateOf(VisibleScreen.TableListView)
            }

            DBViewScreen(tableList = tableList,
                tableData = tableData,
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
    tableData: List<List<String>> = listOf(),
    updateTableData: (name: String) -> Unit = {},
    visibleScreen: VisibleScreen = VisibleScreen.TableListView,
    updateVisibleScreen: (visibleScreen: VisibleScreen) -> Unit = {},
) {

    when (visibleScreen) {
        is VisibleScreen.TableListView -> {
            ListView(modifier = Modifier.fillMaxSize(), tableList = tableList, onClickItem = {
                updateTableData(it)
                updateVisibleScreen(VisibleScreen.TableView)
            })
        }

        is VisibleScreen.TableView -> {
            TableDataGrid(tableData, onBackPress = {
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
            "table 1sdsadasdsadsdsadsdadsa,md,fsdfdsm,fm,dsmf,dsdfdsfdfsfdfsfdfsfdsvddsdsd",
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
        visibleScreen = VisibleScreen.TableView,
        tableData = listOf(
        ),
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
                .padding(Padding5),
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
                    .padding(Padding10), text = tableList[it])
            })
        })
    }
}


@Composable
fun TableDataGrid(
    tableData: List<List<String>>, onBackPress: () -> Unit
) {
    /* val list =
         listOf(
             listOf("www","wwwwwwww","wwwwwwww","WWWWWWWWWWWWW","wwww","ww", "1"),
             listOf("dsds","dsdsfdfsdsdfsdfdsfds","dsdsfsdfdsf","dsds","dsds","dsds", "dsdsd"),
             listOf("dsdxc","ds dfsdfdfdsdxc","dsdxfsd df dsfdsc","dsdxc","fsfdfdsfds df sdsdxc", "dsdxc", "dsds dsds")
         )*/
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.DarkGray)
                .padding(Padding2),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier.clickable(onClick = onBackPress)
            )
            Spacer(modifier = Modifier.size(Spacer20))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Padding5),
                text = "Table content (columns and rows)",
                style = TextStyle(color = Color.White)
            )
        }

        Log.e("____________", "tableData : $tableData")

        if (tableData.isNotEmpty()) {
            TableView(modifier = Modifier.fillMaxSize(), tableData = tableData)
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Database is empty")
            }
        }

    }

}

@Composable
fun TableView(modifier: Modifier = Modifier, tableData: List<List<String>> = listOf()) {
    LazyRow(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .background(color = Color(0x30000000)),
        verticalAlignment = Alignment.CenterVertically,
    ) {
//        for (rowIndex in tableData[0].indices) {
        items(tableData[0].size) { rowIndex ->
            Column(
                modifier = Modifier.height(800.dp)
            ) {
//                items(tableData.size) { columnIndex ->
                for (columnIndex in tableData.indices){
                    val biggestLength =
                        tableData[columnIndex].maxOfOrNull { it.length } ?: 0
                    val biggestText =
                        String(CharArray(biggestLength) { 'W'  /*most widest char is capital W*/ })
                    Column(
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(color = Color.Gray),
                    ) {
                        MeasureUnconstrainedViewWidth(
                            viewToMeasure = {
                                CellText(text = biggestText, columnIndex = columnIndex)
                            }
                        ) { measuredWidth ->
                            Row(
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .background(color = Color.LightGray)
                            ) {
                                CellText(
                                    modifier = Modifier.width(measuredWidth),
                                    text = tableData[columnIndex][rowIndex],
                                    columnIndex = columnIndex
                                )
                                Spacer(modifier = Modifier.width(Width3))
                                Box(
                                    modifier = Modifier
                                        .width(Width1)
                                        .fillMaxHeight()
                                        .background(color = Color.Gray)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(Height1))
                    }
                }
            }
        }
    }
}


@Composable
fun CellText(modifier: Modifier = Modifier, text: String, columnIndex: Int) {
    Text(
        modifier = modifier
            .padding(Padding5), text = text, style = if (columnIndex == 0) {
            TextStyle(fontWeight = FontWeight.Bold)
        } else TextStyle.Default
    )
}

@Composable
fun MeasureUnconstrainedViewWidth(
    viewToMeasure: @Composable () -> Unit,
    content: @Composable (measuredWidth: Dp) -> Unit,
) {
    SubcomposeLayout { constraints ->
        val measuredWidth = subcompose("viewToMeasure", viewToMeasure)[0]
            .measure(Constraints()/*constraints.copy(maxHeight = Constraints.Infinity) if we need to handle height as well*/).width.toDp()

        val contentPlaceable = subcompose("content") {
            content(measuredWidth)
        }[0].measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.place(0, 0)
        }
    }
}


@Composable
fun TableRow(row: TableRow, biggestText: String) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(row.columns.size) { columnIndex ->
            MeasureUnconstrainedViewWidth(
                viewToMeasure = {
                    Text(biggestText)
                }
            ) { measuredWidth ->
                Text(
                    text = row.columns[columnIndex],
                    fontSize = 16.sp,
                    modifier = Modifier.width(measuredWidth)
                )
            }
        }
    }
}

/*@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun TableScreen() {
    // Just a fake data... a Pair of Int and String
    val tableData = (1..100).mapIndexed { index, item ->
        index to "Item $index"
    }
    // Each cell of a column must have the same weight.
    val column1Weight = .3f // 30%
    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        // Here is the header
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "Column 1", weight = column1Weight)
                TableCell(text = "Column 2", weight = column2Weight)
            }
        }
        // Here are all the lines of your table.
        items(tableData) {
            val (id, text) = it
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = id.toString(), weight = column1Weight)
                TableCell(text = text, weight = column2Weight)
            }
        }
    }
}*/


/*LazyVerticalGrid*/


/*val rows = 3
val columns = 3
FlowRow(
modifier = Modifier.padding(4.dp),
horizontalArrangement = Arrangement.spacedBy(4.dp),
maxItemsInEachRow = rows
) {
    val itemModifier = Modifier
        .padding(4.dp)
        .height(80.dp)
        .weight(1f)
        .clip(RoundedCornerShape(8.dp))
        .background(MaterialColors.Blue200)
    repeat(rows * columns) {
        Spacer(modifier = itemModifier)
    }
}*/


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

fun getTableData(db: RoomDatabase, tableName: String): List<List<String>> {
    val readableDB = db.openHelper.readableDatabase
    val tableData = mutableListOf<List<String>>()

    val cursor = readableDB.query("SELECT * FROM $tableName", arrayOf())
    cursor.use {
        if (it.moveToFirst()) {
            val columnNames = it.columnNames
            tableData.add(columnNames.toList())
            do {

                val columns =
                    columnNames.map { columnName -> it.getString(it.getColumnIndexOrThrow(columnName)) }

                tableData.add(columns)
            } while (it.moveToNext())
        }
    }

    db.close()
    return tableData
}

data class TableRow(val columns: List<String>)
