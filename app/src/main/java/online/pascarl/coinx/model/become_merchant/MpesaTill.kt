package online.pasaka.model.user.payment_platforms

import kotlinx.serialization.Serializable

@Serializable
data class MpesaTill( var tillNumber:String? = null)