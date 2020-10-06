package br.com.sasi.comanda.infra.graphql

import br.com.sasi.comanda.core.entities.models.*
import br.com.sasi.comanda.infra.database.Repository
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.enterprise.inject.Default
import javax.inject.Inject


@GraphQLApi
class OrderResource {

    @Inject
    @field: Default
    lateinit var repository: Repository


    @Query("getOrderSummary")
    fun getOrderSummary(id: Int): OrderSummary {
        return repository.findOrder {
            it.id == id
        }.toOrderSummary()
    }

    @Query("getOrder")
    fun getOrder(id: Int): Order {
        return repository.findOrder {
            it.id == id
        }
    }

    @Query("getOrders")
    fun getOrders(): List<Order> {
        return repository.getOrders()
    }

    @Query("getItems")
    fun getItems(): List<Item> {
        return repository.getItems()
    }


    @Mutation("createItem")
    fun createItem(name: String, price: Float, description: String): Item {
        return repository.addNewItem(Item(
                0, name, price, description
        ))
    }

    @Mutation("createOrder")
    fun createOrder(): Order {
        return repository.addOrder()
    }


    @Mutation("addEvent")
    fun addEvent(orderID: Int, eventType: OrderEvents, timestamp: String, data: String): Event {
        if (!OrderEvents.values().contains(eventType) || eventType == OrderEvents.ORDER_OPEN) {
            throw InvalidOrderEvent()
        }
        if(getOrderSummary(orderID).status == "closed") {
            throw OrderAlreadyClosed()
        }
        return repository.createNewEvent(orderID, Event(
                "", eventType, LocalDateTime.parse(timestamp, DateTimeFormatter.ISO_DATE_TIME), data
        ))
    }
}