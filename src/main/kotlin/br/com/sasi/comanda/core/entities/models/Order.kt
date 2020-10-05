package br.com.sasi.comanda.core.entities.models

import io.smallrye.graphql.api.ErrorCode
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

enum class OrderEvents {
    ORDER_OPEN,
    ITEM_ADD,
    ITEM_REMOVE,
    ORDER_CLOSED
}

@ErrorCode("invalid-order-event-type")
class InvalidOrderEvent: RuntimeException("The Informed Event Type is Invalid")

data class Order(val id: Int, val events: ArrayList<Event> = arrayListOf())

fun Order.toOrderSummary(): OrderSummary {
    return OrderSummary(
            id = this.id,
            openedAt = this.events.first().timestamp,
            closedAt = this.events.last().timestamp,
            status = when (this.events.last().eventType) {
                OrderEvents.ORDER_CLOSED -> "closed"
                else -> "open"
            },
            totalPrice = this.events.fold(0f, { acc, event ->
                when (event.eventType) {
                    OrderEvents.ITEM_ADD -> {
                        acc + (event.data as AddItemEvent).item.price
                    }
                    OrderEvents.ITEM_REMOVE -> {
                        acc - (event.data as RemoveItemEvent).item.price
                    }
                    else -> acc
                }
            })
    )
}

data class OrderSummary(val id: Int,
                        val totalPrice: Float,
                        val openedAt: LocalDateTime,
                        val closedAt: LocalDateTime,
                        val status: String)