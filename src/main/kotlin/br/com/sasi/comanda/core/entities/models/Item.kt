package br.com.sasi.comanda.core.entities.models

import com.fasterxml.jackson.annotation.JsonProperty

data class Item(@JsonProperty("id") val id: Int,
                @JsonProperty("name") val name: String,
                @JsonProperty("price") val price: Float,
                @JsonProperty("description") val description: String)