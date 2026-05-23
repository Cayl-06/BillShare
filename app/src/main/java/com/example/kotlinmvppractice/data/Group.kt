package com.example.kotlinmvppractice.data

data class Group(
    var name: String = "",
    var members: MutableList<String> = mutableListOf()
)