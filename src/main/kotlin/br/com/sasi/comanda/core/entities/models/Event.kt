package br.com.sasi.comanda.core.entities.models

import java.time.LocalDateTime

//interface EventData

data class AddItemEvent(val item: Item)

data class RemoveItemEvent(val item: Item)

data class Event(val id: String,
                 val eventType: OrderEvents,
                 val timestamp: LocalDateTime,
                 val data: String)