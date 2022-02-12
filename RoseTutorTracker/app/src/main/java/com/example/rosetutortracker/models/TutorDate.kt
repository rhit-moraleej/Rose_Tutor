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
        MONDAY(0),
        TUESDAY(1),
        WEDNESDAY(2),
        THURSDAY(3),
        FRIDAY(4),
        SATURDAY(5),
        SUNDAY(6);

        var dayOfWeek: Int? = null

        constructor()

        constructor(
            dayOfWeek: Int
        ) {
            this.dayOfWeek = dayOfWeek
        }
    }

}