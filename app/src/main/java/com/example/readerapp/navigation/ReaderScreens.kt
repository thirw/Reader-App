package com.example.readerapp.navigation

enum class ReaderScreens {
    SplasScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    DetailScreen,
    UpdateScreen,
    ReaderStatsScreen;


    companion object {
        fun fromRoute(route: String): ReaderScreens = when(route?.substringBefore("/")) {
            SplasScreen.name -> SplasScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            DetailScreen.name -> DetailScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw  IllegalAccessException("Route $route is not recognized")
        }
    }
}