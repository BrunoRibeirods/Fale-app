package dev.brunoribeiro.fale.entities

data class Contact(
        val name: String?= null,
        val img: String?= null,
        val messages: List<Message>? = null
)
