package com.example.affirmations.model

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes

data class Affirmation(
    @StringRes val stringResourceId: Int,
    @IntegerRes val imageResourceId: Int
) {

}