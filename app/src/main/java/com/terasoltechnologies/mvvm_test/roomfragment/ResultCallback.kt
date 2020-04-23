package com.terasoltechnologies.mvvm_test.roomfragment

import com.terasoltechnologies.mvvm_test.database.Notes
import java.io.Serializable

interface ResultCallback{
    fun setResult(notes: Notes)
}