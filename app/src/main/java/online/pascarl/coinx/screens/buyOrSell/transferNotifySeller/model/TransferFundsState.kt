package online.pascarl.coinx.screens.buyOrSell.transferNotifySeller.model

data class TransferFundsState(
    val fundsTrasfered:Boolean,
    val fundsTransferring:Boolean,
    val message:String = ""
)
