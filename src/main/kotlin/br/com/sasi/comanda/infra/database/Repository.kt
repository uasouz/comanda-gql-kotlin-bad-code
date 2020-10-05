package br.com.sasi.comanda.infra.database

import br.com.sasi.comanda.core.entities.models.Event
import br.com.sasi.comanda.core.entities.models.Item
import br.com.sasi.comanda.core.entities.models.Order

interface Repository {
    fun addOrder(): Order
    fun getOrders(): List<Order>
    fun findOrder(search: (Order) -> Boolean): Order
    fun getItems(): List<Item>
    fun addNewItem(item: Item): Item
    fun createNewEvent(orderID: Int, event: Event): Event
}