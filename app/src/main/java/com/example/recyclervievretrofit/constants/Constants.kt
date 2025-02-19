package com.example.recyclervievretrofit.constants

//Константы для Bundle:
const val KEY_NAME_RU = "KEY_NAME_RU"
const val KEY_GENRES = "KEY_GENRES"
const val KEY_PREMIERE_RU = "KEY_PREMIERE_RU"
const val KEY_COUNTRIES = "KEY_COUNTRIES"
const val KEY_POSTER_URL = "KEY_CPOSTER_URL"

//Константы для пользовательского выбора (Spinner)
val YEAR_ARRAY = listOf<Int>(
    1995,1996,1997,1998,1999,2000,2001,2002,
    2003,2004,2005,2006,2007,2008,2009,2010,
    2011,2012,2013,2014,2015,2016,2017,2018,
    2019,2020,2021,2022,2023,2024,2025,2026,
    2027,2028,2029,2030
).toSet().toList()

val MONTH_ARRAY = listOf<String>(
    "JANUARY",
    "FEBRUARY",
    "MARCH",
    "APRIL",
    "MAY",
    "JUNE",
    "JULY",
    "AUGUST",
    "SEPTEMBER",
    "OCTOBER",
    "NOVEMBER",
    "DECEMBER"
)

val MONTH_ARRAY_TO_USER = listOf<String>(
    "Январь",
    "Февраль",
    "Март",
    "Апрель",
    "Май",
    "Июнь",
    "Июль",
    "Август",
    "Сентябрь",
    "Октябрь",
    "Ноябрь",
    "Декабрь"
)

//Значения, чтобы не загружать данные при возвращении на экран с списком фильмов
var OLD_POSITION_YEAR = 0
var OLD_POSITION_MONTH = 0