package online.pascarl.coinx.model

import kotlinx.serialization.Serializable
import online.pascarl.coinx.model.become_merchant.PaymentMethod

@Serializable
data class MerchantDataDto(
    val username: String = "",
    val phoneNumber:String = "",
    val email: String = "",
    val ordersMade:Long = 0,
    val ordersCompleted:Long = 0,
    val ordersCompletedByPercentage:Double = 0.0,
    var paymentMethod: PaymentMethod? = null,
    val lastSeen:String = ""
)