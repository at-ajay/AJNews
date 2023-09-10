package com.aj.ajnews.util

import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView

enum class Keyboard {
    OPENED, CLOSED
}

@Composable
fun rememberKeyBoardAsState(): State<Keyboard> {
    val keyBoardState = remember { mutableStateOf(Keyboard.CLOSED) }
    val view = LocalView.current

    DisposableEffect(view) {
        val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keyboardHeight = screenHeight - rect.bottom

            keyBoardState.value = if (keyboardHeight > screenHeight * 0.15) Keyboard.OPENED else Keyboard.CLOSED
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
    }

    return keyBoardState
}