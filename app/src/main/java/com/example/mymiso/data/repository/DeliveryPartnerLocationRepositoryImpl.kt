package com.example.mymiso.data.repository

import com.example.mymiso.domain.model.Location
import com.example.mymiso.domain.repository.DeliveryPartnerLocationRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.sse.EventSource
import okhttp3.sse.EventSourceListener
import okhttp3.sse.EventSources
import org.json.JSONObject

class DeliveryPartnerLocationRepositoryImpl : DeliveryPartnerLocationRepository {
    private val client = OkHttpClient()


    override fun getDeliveryPartnerLocation(): Flow<Location> = flow {
        while (true) {
            // Emit hardcoded location data for testing
            emit(Location(37.7749, -122.4194)) // Example coordinates (San Francisco)
            delay(5000) // Emit every 5 seconds
        }
    }
//    fun getDeliveryPartnerLocation(): Flow<Location> = callbackFlow {
//        val request = Request.Builder()
//            .url("https://api.example.com/delivery-partner-location")
//            .build()
//
//        val listener = object : EventSourceListener() {
//
//            override fun onOpen(eventSource: EventSource, response: okhttp3.Response) {
//                super.onOpen(eventSource, response)
//            }
//
//            override fun onEvent(
//                eventSource: EventSource,
//                id: String?,
//                type: String?,
//                data: String
//            ) {
//                super.onEvent(eventSource, id, type, data)
//                try {
//                    val location = parseLocationData(data)
//                    trySend(location).isSuccess
//                } catch (e: Exception) {
//                    println("Error parsing location: ${e.message}")
//                }
//            }
//
//            override fun onClosed(eventSource: EventSource) {
//                super.onClosed(eventSource)
//            }
//
//            override fun onFailure(
//                eventSource: EventSource,
//                t: Throwable?,
//                response: okhttp3.Response?
//            ) {
//                super.onFailure(eventSource, t, response)
//            }
//
//        }
//
//        val eventSource = EventSources.createFactory(client)
//            .newEventSource(request, listener)
//
//        awaitClose {
//            eventSource.cancel()
//        }
//    }

    private fun parseLocationData(data: String): Location {
        val jsonObject = JSONObject(data)
        val latitude = jsonObject.getDouble("latitude")
        val longitude = jsonObject.getDouble("longitude")
        return Location(latitude, longitude)
    }
}