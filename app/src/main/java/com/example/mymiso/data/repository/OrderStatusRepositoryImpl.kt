package com.example.mymiso.data.repository

import com.example.mymiso.domain.model.OrderStatus
import com.example.mymiso.domain.repository.OrderStatusRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStreamReader

class OrderStatusRepositoryImpl(private val client: OkHttpClient) : OrderStatusRepository {
    override fun trackOrderStatus(orderId: String): Flow<OrderStatus> = flow {
        val request = Request.Builder()
            .url("https://api.example.com/orders/$orderId/status")
            .build()


        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IllegalStateException("Failed to fetch order status")
            }

            val reader = BufferedReader(InputStreamReader(response.body?.byteStream()))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                if (line!!.startsWith("data:")) {
                    val statusJson = line!!.substring(5).trim()
                    val orderStatus = parseOrderStatusFromJson(statusJson)
                    emit(orderStatus)
                }
            }

        }
    }

    private fun parseOrderStatusFromJson(json: String): OrderStatus {
        return OrderStatus(status = json)
    }
}