package com.duodutch

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform