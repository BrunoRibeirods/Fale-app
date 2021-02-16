package dev.brunoribeiro.fale.entities

data class Contact(
        val name: String,
        val img: String,
        val messages: List<Message>
)
