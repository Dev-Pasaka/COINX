package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model.dto

import kotlinx.serialization.Serializable

@Serializable
enum class OrderStatusDto {
    STARTED,
    PENDING,
    BUYER_HAS_TRANSFERRED_FUNDS,
    MERCHANT_HAS_TRANSFERRED_FUNDS,
    COMPLETED,
    EXPIRED,
    CANCELLED
}