package com.example.rosetutortracker.models

data class TutorDate(
    var working: Boolean = false,
    var day: String = "",
    var startHour: Int = 0,
    var startMin: Int = 0,
    var endHour: Int = 0,
    var endMin: Int = 0
) {
    enum class Day {
        MONDAY(0, "Monday"),
        TUESDAY(1, "Tuesday"),
        WEDNESDAY(2, "Wednesday"),
        THURSDAY(3, "Thursday"),
        FRIDAY(4, "Friday"),
        SATURDAY(5, "Saturday"),
        SUNDAY(6, "Sunday");

        var dayOfWeek: Int? = null
        var dayPrint: String? = null

        constructor()

        constructor(
            dayOfWeek: Int,
            dayPrint: String
        ) {
            this.dayOfWeek = dayOfWeek
            this.dayPrint = dayPrint
        }
    }

}