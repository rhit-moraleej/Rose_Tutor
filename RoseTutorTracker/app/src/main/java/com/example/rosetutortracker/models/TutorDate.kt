package com.example.rosetutortracker.models

data class TutorDate(var working: Boolean = false,
                     var day: String = "",
                     var startHour: Int = 0,
                     var startMin: Int = 0,
                     var endHour: Int = 0,
                     var endMin: Int = 0
) {
    companion object{
        val days = arrayOf(
            "MONDAY",
            "TUESDAY",
            "WEDNESDAY",
            "THURSDAY",
            "FRIDAY",
            "SATURDAY",
            "SUNDAY"
        )
    }
}