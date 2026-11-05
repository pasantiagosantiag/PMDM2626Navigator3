package ies.sequeros.navigator3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform