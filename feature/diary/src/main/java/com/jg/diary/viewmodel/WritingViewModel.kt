package com.jg.diary.viewmodel

import androidx.lifecycle.ViewModel
import com.jg.composeplayground.core.model.enums.DiaryWritingType
import com.jg.composeplayground.core.model.navigation.DiaryWritingArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject



@HiltViewModel
class WritingViewModel @Inject constructor(

): ViewModel()  {

    fun initDiary(data: DiaryWritingArgs) {
        when (data.diaryWritingType) {
            DiaryWritingType.WRITING -> {}
            DiaryWritingType.EDIT_POST -> {
                getPostContentToEdit(data.diaryId)
            }
        }
    }

    private fun getPostContentToEdit(diaryId: Int) {

    }

    fun saveDiary() {

    }

    fun updateDiary() {

    }
}