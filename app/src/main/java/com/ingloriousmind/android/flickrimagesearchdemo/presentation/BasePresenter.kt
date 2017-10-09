package com.ingloriousmind.android.flickrimagesearchdemo.presentation

import android.support.annotation.CallSuper

abstract class BasePresenter<V : BaseContract.View> : BaseContract.Presenter<V> {

    protected var view: V? = null

    @CallSuper
    override fun bindView(view: V) {
        this.view = view
    }

    @CallSuper
    override fun unbindView() {
        view = null
    }

    fun isBound() = view != null

}