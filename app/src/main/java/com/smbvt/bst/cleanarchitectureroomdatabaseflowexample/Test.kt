package com.smbvt.bst.cleanarchitectureroomdatabaseflowexample

import android.content.Context
import android.widget.Toast
import androidx.activity.ComponentActivity

sealed class UIEvents {
    data class ShowToast(val message: String) : UIEvents()
    data object FinishActivity : UIEvents()
}


fun main(context: ComponentActivity) {
    val uiEvents: UIEvents = UIEvents.FinishActivity
    when (uiEvents) {
        is UIEvents.FinishActivity -> {
            context.finish()
        }

        is UIEvents.ShowToast -> {
            Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
        }
    }
}