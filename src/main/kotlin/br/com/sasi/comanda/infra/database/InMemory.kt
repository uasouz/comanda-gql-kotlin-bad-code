package br.com.sasi.comanda.infra.database

import br.com.sasi.comanda.core.entities.models.Event
import br.com.sasi.comanda.core.entities.models.Item
import br.com.sasi.comanda.core.entities.models.Order
import br.com.sasi.comanda.core.entities.models.OrderEvents
import java.time.LocalDateTime
import java.util.*
import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class InMemoryRepository : Repository {
    val orders = arrayListOf<Order>()
    var items = arrayListOf<Item>()

    init {
        items.add(Item(
                0,
                "Adaptador Type-C",
                400.5f,
                "dedonocool"
        ))
        items.add(Item(
                1,
                "Coxinha de Cachorro",
                4.5f,
                "vira-lata caramelo (mais a cox.inha eh saugada ta.?)"
        ))
        items.add(Item(
                2,
                "Bala de prata",
                40.9f,
                "Para resultados imediatos contra Lobisomem e vizinhos chatos"
        ))
        items.add(Item(
                3,
                "Easter egg",
                50f,
                "Coelhinho da páscoa"
        ))
        items.add(Item(
                4,
                "Baré",
                10f,
                "Selo de qualidade ifood"
        ))

    }

    override fun addOrder(): Order {
        val newOrder = Order(
                orders.size + 1
        )
        orders.add(newOrder)
        createNewEvent(newOrder.id,Event("",OrderEvents.ORDER_OPEN, LocalDateTime.now(),"{}"))
        return newOrder
    }

    override fun getOrders(): List<Order> {
        return orders
    }

    override fun findOrder(search: (Order) -> Boolean): Order {
        return orders.first(search)
    }

    override fun getItems(): List<Item> {
        return items
    }

    override fun addNewItem(item: Item): Item {
        val citem = item.copy(id = items.size + 1)
        items.add(citem)
        return citem
    }

    override fun createNewEvent(orderID: Int, event: Event): Event {
        val cevent = event.copy(id = UUID.randomUUID().toString())
        orders.find {
            it.id == orderID
        }?.events?.add(cevent)
        return cevent
    }

}