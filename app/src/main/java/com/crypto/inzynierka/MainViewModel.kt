package com.crypto.inzynierka

import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {

    var CurrentChapter: Int = 1

    companion object {
        const val DB_VERSION = 20
    }

}