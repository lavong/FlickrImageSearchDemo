package com.ingloriousmind.android.flickrimagesearchdemo.presentation

interface BaseContract {

    interface View

    interface Presenter<in View> {
        fun bindView(view: View)
        fun unbindView()
    }

}