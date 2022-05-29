package com.achaka.intervals.interval.ui.adapterdelegate

class DelegateAdapterNotFoundException: Exception() {

    override val message: String
        get() = "Delegate has not been found"
}