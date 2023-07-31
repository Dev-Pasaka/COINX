package online.pascarl.coinx

import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.url
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import online.pascarl.coinx.KtorClient.KtorClient
import online.pascarl.coinx.model.Cryptocurrency



suspend fun main() {
    //println(getCryptoPrices())
    val input = "{BTC=https://s2.coinmarketcap.com/static/img/coins/64x64/1.png" +
            ", ETH=https://s2.coinmarketcap.com/static/img/coins/64x64/1027.png," +
            " USDT=https://s2.coinmarketcap.com/static/img/coins/64x64/825.png," +
            " XRP=https://s2.coinmarketcap.com/static/img/coins/64x64/52.png," +
            " BNB=https://s2.coinmarketcap.com/static/img/coins/64x64/1839.png," +
            " USDC=https://s2.coinmarketcap.com/static/img/coins/64x64/3408.png" +
            ", DOGE=https://s2.coinmarketcap.com/static/img/coins/64x64/27301.png," +
            " ADA=https://s2.coinmarketcap.com/static/img/coins/64x64/2010.png," +
            " SOL=https://s2.coinmarketcap.com/static/img/coins/64x64/12003.png," +
            " TRX=https://s2.coinmarketcap.com/static/img/coins/64x64/1958.png, " +
            "MATIC=https://s2.coinmarketcap.com/static/img/coins/64x64/3890.png, " +
            "LTC=https://s2.coinmarketcap.com/static/img/coins/64x64/2.png," +
            " DOT=https://s2.coinmarketcap.com/static/img/coins/64x64/6636.png," +
            " TON=https://s2.coinmarketcap.com/static/img/coins/64x64/6890.png," +
            " WBTC=https://s2.coinmarketcap.com/static/img/coins/64x64/4666.png," +
            " BCH=https://s2.coinmarketcap.com/static/img/coins/64x64/1831.png, " +
            "SHIB=https://s2.coinmarketcap.com/static/img/coins/64x64/5994.png," +
            " AVAX=https://s2.coinmarketcap.com/static/img/coins/64x64/5805.png," +
            " DAI=https://s2.coinmarketcap.com/static/img/coins/64x64/23859.png," +
            " LINK=https://s2.coinmarketcap.com/static/img/coins/64x64/1975.png," +
            " XLM=https://s2.coinmarketcap.com/static/img/coins/64x64/512.png," +
            " BUSD=https://s2.coinmarketcap.com/static/img/coins/64x64/4687.png," +
            " LEO=https://s2.coinmarketcap.com/static/img/coins/64x64/19469.png," +
            " UNI=https://s2.coinmarketcap.com/static/img/coins/64x64/4113.png," +
            " ATOM=https://s2.coinmarketcap.com/static/img/coins/64x64/1420.png," +
            " XMR=https://s2.coinmarketcap.com/static/img/coins/64x64/328.png," +
            " TUSD=https://s2.coinmarketcap.com/static/img/coins/64x64/2563.png," +
            " OKB=https://s2.coinmarketcap.com/static/img/coins/64x64/3897.png," +
            " ETC=https://s2.coinmarketcap.com/static/img/coins/64x64/646.png," +
            " FIL=https://s2.coinmarketcap.com/static/img/coins/64x64/2280.png," +
            " ICP=https://s2.coinmarketcap.com/static/img/coins/64x64/8916.png," +
            " MNT=https://s2.coinmarketcap.com/static/img/coins/64x64/5615.png," +
            " LDO=https://s2.coinmarketcap.com/static/img/coins/64x64/8000.png," +
            " HBAR=https://s2.coinmarketcap.com/static/img/coins/64x64/4642.png," +
            " APT=https://s2.coinmarketcap.com/static/img/coins/64x64/13473.png," +
            " ARB=https://s2.coinmarketcap.com/static/img/coins/64x64/23979.png," +
            " CRO=https://s2.coinmarketcap.com/static/img/coins/64x64/5547.png," +
            " VET=https://s2.coinmarketcap.com/static/img/coins/64x64/3077.png," +
            " NEAR=https://s2.coinmarketcap.com/static/img/coins/64x64/6535.png," +
            " QNT=https://s2.coinmarketcap.com/static/img/coins/64x64/3155.png," +
            " MKR=https://s2.coinmarketcap.com/static/img/coins/64x64/1518.png," +
            " AAVE=https://s2.coinmarketcap.com/static/img/coins/64x64/7278.png," +
            " OP=https://s2.coinmarketcap.com/static/img/coins/64x64/18804.png," +
            " GRT=https://s2.coinmarketcap.com/static/img/coins/64x64/22086.png," +
            " ALGO=https://s2.coinmarketcap.com/static/img/coins/64x64/4030.png," +
            " AXS=https://s2.coinmarketcap.com/static/img/coins/64x64/6783.png," +
            " STX=https://s2.coinmarketcap.com/static/img/coins/64x64/20859.png," +
            " EGLD=https://s2.coinmarketcap.com/static/img/coins/64x64/6892.png," +
            " THETA=https://s2.coinmarketcap.com/static/img/coins/64x64/2416.png," +
            " SAND=https://s2.coinmarketcap.com/static/img/coins/64x64/6210.png," +
            " EOS=https://s2.coinmarketcap.com/static/img/coins/64x64/1765.png," +
            " IMX=https://s2.coinmarketcap.com/static/img/coins/64x64/9532.png," +
            " XDC=https://s2.coinmarketcap.com/static/img/coins/64x64/2634.png," +
            " XTZ=https://s2.coinmarketcap.com/static/img/coins/64x64/2011.png," +
            " APE=https://s2.coinmarketcap.com/static/img/coins/64x64/16764.png," +
            " MANA=https://s2.coinmarketcap.com/static/img/coins/64x64/12712.png" +
            ", USDD=https://s2.coinmarketcap.com/static/img/coins/64x64/19891.png," +
            " SNX=https://s2.coinmarketcap.com/static/img/coins/64x64/24157.png," +
            " BSV=https://s2.coinmarketcap.com/static/img/coins/64x64/25160.png," +
            " FTM=https://s2.coinmarketcap.com/static/img/coins/64x64/9990.png, " +
            "RNDR=https://s2.coinmarketcap.com/static/img/coins/64x64/5690.png," +
            " INJ=https://s2.coinmarketcap.com/static/img/coins/64x64/7226.png," +
            " CRV=https://s2.coinmarketcap.com/static/img/coins/64x64/3675.png," +
            " NEO=https://s2.coinmarketcap.com/static/img/coins/64x64/24253.png," +
            " FLOW=https://s2.coinmarketcap.com/static/img/coins/64x64/27520.png," +
            " RPL=https://s2.coinmarketcap.com/static/img/coins/64x64/2943.png," +
            " XEC=https://s2.coinmarketcap.com/static/img/coins/64x64/10791.png," +
            " KAVA=https://s2.coinmarketcap.com/static/img/coins/64x64/4846.png," +
            " KCS=https://s2.coinmarketcap.com/static/img/coins/64x64/2087.png," +
            " USDP=https://s2.coinmarketcap.com/static/img/coins/64x64/8886.png," +
            " CHZ=https://s2.coinmarketcap.com/static/img/coins/64x64/4066.png," +
            " COMP=https://s2.coinmarketcap.com/static/img/coins/64x64/3180.png," +
            " CFX=https://s2.coinmarketcap.com/static/img/coins/64x64/5854.png," +
            " GALA=https://s2.coinmarketcap.com/static/img/coins/64x64/7080.png," +
            " KLAY=https://s2.coinmarketcap.com/static/img/coins/64x64/4256.png," +
            " PEPE=https://s2.coinmarketcap.com/static/img/coins/64x64/24764.png," +
            " PAXG=https://s2.coinmarketcap.com/static/img/coins/64x64/4705.png," +
            " XAUT=https://s2.coinmarketcap.com/static/img/coins/64x64/5176.png," +
            " ZEC=https://s2.coinmarketcap.com/static/img/coins/64x64/1437.png, " +
            "GMX=https://s2.coinmarketcap.com/static/img/coins/64x64/1213.png," +
            " MIOTA=https://s2.coinmarketcap.com/static/img/coins/64x64/1720.png," +
            " LUNC=https://s2.coinmarketcap.com/static/img/coins/64x64/4172.png, " +
            "BTT=https://s2.coinmarketcap.com/static/img/coins/64x64/22912.png, " +
            "FXS=https://s2.coinmarketcap.com/static/img/coins/64x64/6953.png, " +
            "HT=https://s2.coinmarketcap.com/static/img/coins/64x64/2502.png," +
            " CSPR=https://s2.coinmarketcap.com/static/img/coins/64x64/5899.png, " +
            "GT=https://s2.coinmarketcap.com/static/img/coins/64x64/4269.png," +
            " SUI=https://s2.coinmarketcap.com/static/img/coins/64x64/25169.png," +
            " MINA=https://s2.coinmarketcap.com/static/img/coins/64x64/8646.png," +
            " GUSD=https://s2.coinmarketcap.com/static/img/coins/64x64/3306.png," +
            " TWT=https://s2.coinmarketcap.com/static/img/coins/64x64/23251.png," +
            " DASH=https://s2.coinmarketcap.com/static/img/coins/64x64/131.png," +
            " NEXO=https://s2.coinmarketcap.com/static/img/coins/64x64/2694.png," +
            " WOO=https://s2.coinmarketcap.com/static/img/coins/64x64/7501.png," +
            " ZIL=https://s2.coinmarketcap.com/static/img/coins/64x64/2469.png, " +
            "DYDX=https://s2.coinmarketcap.com/static/img/coins/64x64/11156.png," +
            " RUNE=https://s2.coinmarketcap.com/static/img/coins/64x64/10110.png," +
            " CAKE=https://s2.coinmarketcap.com/static/img/coins/64x64/16149.png," +
            " 1INCH=https://s2.coinmarketcap.com/static/img/coins/64x64/8104.png, " +
            "ENJ=https://s2.coinmarketcap.com/static/img/coins/64x64/2130.png, " +
            "GNO=https://s2.coinmarketcap.com/static/img/coins/64x64/1659.png," +
            " MASK=https://s2.coinmarketcap.com/static/img/coins/64x64/24458.png," +
            " FLR=https://s2.coinmarketcap.com/static/img/coins/64x64/7950.png," +
            " BAT=https://s2.coinmarketcap.com/static/img/coins/64x64/10028.png," +
            " ROSE=https://s2.coinmarketcap.com/static/img/coins/64x64/16661.png," +
            " LRC=https://s2.coinmarketcap.com/static/img/coins/64x64/1934.png," +
            " CVX=https://s2.coinmarketcap.com/static/img/coins/64x64/9903.png," +
            " BONE=https://s2.coinmarketcap.com/static/img/coins/64x64/19086.png," +
            " TFUEL=https://s2.coinmarketcap.com/static/img/coins/64x64/3822.png," +
            " MX=https://s2.coinmarketcap.com/static/img/coins/64x64/13696.png," +
            " AGIX=https://s2.coinmarketcap.com/static/img/coins/64x64/2424.png," +
            " QTUM=https://s2.coinmarketcap.com/static/img/coins/64x64/1684.png," +
            " ENS=https://s2.coinmarketcap.com/static/img/coins/64x64/13855.png," +
            " XEM=https://s2.coinmarketcap.com/static/img/coins/64x64/873.png," +
            " XCH=https://s2.coinmarketcap.com/static/img/coins/64x64/652.png," +
            " ANKR=https://s2.coinmarketcap.com/static/img/coins/64x64/3783.png," +
            " WLD=https://s2.coinmarketcap.com/static/img/coins/64x64/20756.png," +
            " CELO=https://s2.coinmarketcap.com/static/img/coins/64x64/5567.png," +
            " OSMO=https://s2.coinmarketcap.com/static/img/coins/64x64/12220.png," +
            " BLUR=https://s2.coinmarketcap.com/static/img/coins/64x64/4530.png," +
            " BTG=https://s2.coinmarketcap.com/static/img/coins/64x64/34.png," +
            " GMT=https://s2.coinmarketcap.com/static/img/coins/64x64/18487.png," +
            " BAL=https://s2.coinmarketcap.com/static/img/coins/64x64/5728.png," +
            " RVN=https://s2.coinmarketcap.com/static/img/coins/64x64/2577.png, " +
            "DCR=https://s2.coinmarketcap.com/static/img/coins/64x64/1168.png, " +
            "YFI=https://s2.coinmarketcap.com/static/img/coins/64x64/5864.png, " +
            "HNT=https://s2.coinmarketcap.com/static/img/coins/64x64/5422.png, " +
            "HOT=https://s2.coinmarketcap.com/static/img/coins/64x64/3836.png, " +
            "T=https://s2.coinmarketcap.com/static/img/coins/64x64/23209.png, " +
            "OCEAN=https://s2.coinmarketcap.com/static/img/coins/64x64/1571.png," +
            " WAVES=https://s2.coinmarketcap.com/static/img/coins/64x64/18905.png," +
            " SFP=https://s2.coinmarketcap.com/static/img/coins/64x64/8119.png," +
            " FLOKI=https://s2.coinmarketcap.com/static/img/coins/64x64/16703.png," +
            " JST=https://s2.coinmarketcap.com/static/img/coins/64x64/19810.png," +
            " ICX=https://s2.coinmarketcap.com/static/img/coins/64x64/2302.png, " +
            "ASTR=https://s2.coinmarketcap.com/static/img/coins/64x64/12885.png, " +
            "LUNA=https://s2.coinmarketcap.com/static/img/coins/64x64/20314.png," +
            " GLM=https://s2.coinmarketcap.com/static/img/coins/64x64/1455.png, " +
            "SXP=https://s2.coinmarketcap.com/static/img/coins/64x64/4279.png," +
            " KSM=https://s2.coinmarketcap.com/static/img/coins/64x64/5034.png," +
            " AUDIO=https://s2.coinmarketcap.com/static/img/coins/64x64/22378.png," +
            " AR=https://s2.coinmarketcap.com/static/img/coins/64x64/471.png," +
            " ETHW=https://s2.coinmarketcap.com/static/img/coins/64x64/21296.png," +
            " JASMY=https://s2.coinmarketcap.com/static/img/coins/64x64/8425.png," +
            " SC=https://s2.coinmarketcap.com/static/img/coins/64x64/21855.png," +
            " IOTX=https://s2.coinmarketcap.com/static/img/coins/64x64/2777.png," +
            " HIVE=https://s2.coinmarketcap.com/static/img/coins/64x64/5370.png," +
            " ZRX=https://s2.coinmarketcap.com/static/img/coins/64x64/1896.png, " +
            "WAXP=https://s2.coinmarketcap.com/static/img/coins/64x64/2300.png, " +
            "ELF=https://s2.coinmarketcap.com/static/img/coins/64x64/14936.png," +
            " FET=https://s2.coinmarketcap.com/static/img/coins/64x64/3773.png," +
            " SSV=https://s2.coinmarketcap.com/static/img/coins/64x64/590.png, " +
            "ONT=https://s2.coinmarketcap.com/static/img/coins/64x64/2566.png," +
            " IOST=https://s2.coinmarketcap.com/static/img/coins/64x64/2405.png, " +
            "ANT=https://s2.coinmarketcap.com/static/img/coins/64x64/16473.png," +
            " USTC=https://s2.coinmarketcap.com/static/img/coins/64x64/15610.png," +
            " GLMR=https://s2.coinmarketcap.com/static/img/coins/64x64/6836.png, " +
            "ONE=https://s2.coinmarketcap.com/static/img/coins/64x64/10509.png," +
            " MAGIC=https://s2.coinmarketcap.com/static/img/coins/64x64/19708.png," +
            " BAND=https://s2.coinmarketcap.com/static/img/coins/64x64/4679.png," +
            " SUSHI=https://s2.coinmarketcap.com/static/img/coins/64x64/6758.png, " +
            "BORA=https://s2.coinmarketcap.com/static/img/coins/64x64/3801.png," +
            " ILV=https://s2.coinmarketcap.com/static/img/coins/64x64/8719.png," +
            " TOMO=https://s2.coinmarketcap.com/static/img/coins/64x64/2570.png," +
            " KDA=https://s2.coinmarketcap.com/static/img/coins/64x64/5647.png," +
            " AXL=https://s2.coinmarketcap.com/static/img/coins/64x64/5065.png, " +
            "BICO=https://s2.coinmarketcap.com/static/img/coins/64x64/9543.png, " +
            "MOB=https://s2.coinmarketcap.com/static/img/coins/64x64/14832.png," +
            " BRISE=https://s2.coinmarketcap.com/static/img/coins/64x64/11079.png," +
            " DGB=https://s2.coinmarketcap.com/static/img/coins/64x64/109.png, " +
            "FLUX=https://s2.coinmarketcap.com/static/img/coins/64x64/5876.png, " +
            "ZEN=https://s2.coinmarketcap.com/static/img/coins/64x64/1698.png, " +
            "KNC=https://s2.coinmarketcap.com/static/img/coins/64x64/9444.png, " +
            "CORE=https://s2.coinmarketcap.com/static/img/coins/64x64/963.png, " +
            "SKL=https://s2.coinmarketcap.com/static/img/coins/64x64/5691.png, " +
            "STG=https://s2.coinmarketcap.com/static/img/coins/64x64/16572.png," +
            " DAO=https://s2.coinmarketcap.com/static/img/coins/64x64/27110.png," +
            " ACH=https://s2.coinmarketcap.com/static/img/coins/64x64/655.png, " +
            "LPT=https://s2.coinmarketcap.com/static/img/coins/64x64/3640.png," +
            " CKB=https://s2.coinmarketcap.com/static/img/coins/64x64/4948.png," +
            " UMA=https://s2.coinmarketcap.com/static/img/coins/64x64/5617.png," +
            " JOE=https://s2.coinmarketcap.com/static/img/coins/64x64/11396.png," +
            " STORJ=https://s2.coinmarketcap.com/static/img/coins/64x64/1772.png," +
            " EDU=https://s2.coinmarketcap.com/static/img/coins/64x64/2951.png," +
            " CELR=https://s2.coinmarketcap.com/static/img/coins/64x64/3814.png," +
            " LSK=https://s2.coinmarketcap.com/static/img/coins/64x64/1214.png," +
            " AMP=https://s2.coinmarketcap.com/static/img/coins/64x64/1125.png, " +
            "CTSI=https://s2.coinmarketcap.com/static/img/coins/64x64/5444.png," +
            " RBN=https://s2.coinmarketcap.com/static/img/coins/64x64/12456.png, " +
            "EVER=https://s2.coinmarketcap.com/static/img/coins/64x64/7505.png," +
            " PUNDIX=https://s2.coinmarketcap.com/static/img/coins/64x64/9040.png," +
            " MLK=https://s2.coinmarketcap.com/static/img/coins/64x64/5266.png," +
            " CFG=https://s2.coinmarketcap.com/static/img/coins/64x64/6748.png," +
            " LQTY=https://s2.coinmarketcap.com/static/img/coins/64x64/7429.png," +
            " RSR=https://s2.coinmarketcap.com/static/img/coins/64x64/3964.png," +
            " NYM=https://s2.coinmarketcap.com/static/img/coins/64x64/17591.png, " +
            "SNT=https://s2.coinmarketcap.com/static/img/coins/64x64/19868.png," +
            " NFT=https://s2.coinmarketcap.com/static/img/coins/64x64/27064.png," +
            " PLA=https://s2.coinmarketcap.com/static/img/coins/64x64/3711.png, " +
            "POLYX=https://s2.coinmarketcap.com/static/img/coins/64x64/20362.png}\n"
    parseToMap(input)

}

suspend fun getCryptoInformation(symbol:String = "eth"):String? {
    var logoUrl:String = ""
    val client = KtorClient.httpClient
    val request = try {
        client.get<String>("https://pro-api.coinmarketcap.com/v2/cryptocurrency/info?symbol=$symbol") {
            header("X-CMC_PRO_API_KEY", "0f42271f-74af-4b7d-b946-fa9933b9a1f6")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

    val jsonResponseString: String? = request
    var cryptoData: Any
    val jsonResponseObj = jsonResponseString?.let { Json.parseToJsonElement(it) } as JsonObject
    cryptoData = jsonResponseObj["data"] as Any
    val coinObj = cryptoData.toString().let { Json.parseToJsonElement(it) } as JsonObject
    val coinData = coinObj[symbol.uppercase()] as List<*>
    coinData.forEach {obj ->
        val logoObj = obj.toString().let { Json.parseToJsonElement(it) } as JsonObject
        val logo = logoObj["logo"] as Any
       // println(logo.toString().replace("\"", ""))
        logoUrl =logo.toString().replace("\"", "")

    }

    return logoUrl
}

suspend fun getCryptoPrices():MutableMap<String, String> {
    var cryptoSymbol:MutableMap<String, String> = mutableMapOf()
    val client = KtorClient.httpClient
    val request = try {
        client.get<String>("https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest") {
            header("X-CMC_PRO_API_KEY", "0f42271f-74af-4b7d-b946-fa9933b9a1f6")
            //contentType(ContentType.Application.Json)
            url {
                parameter("start", "1")
                parameter("limit", "200")
                //parameter("coins", "Bitcoin")
                parameter("convert", "KES")
            }
        }
    } catch (_: Exception) {
        null
    }
    request?.let {
        val jsonResponseString: String = request
        var cryptoData: List<*>
        val jsonResponseObj = Json.parseToJsonElement(jsonResponseString) as JsonObject
        cryptoData = jsonResponseObj["data"] as List<*>

        val cryptoList = mutableListOf<Cryptocurrency>()
        cryptoData.forEach {
            val dataObj = it as Map<*, *>
            val id = dataObj["id"]
            val name = dataObj["name"]
            val symbol = dataObj["symbol"].toString().replace("\"", "")
            val priceQuote = dataObj["quote"]
            val currencyObj = Json.parseToJsonElement(priceQuote.toString()) as JsonObject
            val currency = currencyObj["KES"] as Map<String, Double>
           try{
               getCryptoInformation(symbol = symbol)?.let { it1 -> cryptoSymbol.put(symbol, it1) }
               delay(3000)
               println(cryptoSymbol)
           }catch (e:Exception){
               delay(60000)
           }
        }
    }
    return cryptoSymbol
}



fun parseToMap(input: String, symbol: String = "eth"):String?{
    val map = mutableMapOf<String, String>()
    // Remove the curly braces at the beginning and end of the input string
    val trimmedInput = input.trimStart('{').trimEnd('}')
    // Split the input into individual key-value pairs using ', ' as a delimiter
    val keyValuePairs = trimmedInput.split(", ")
    // Iterate through each key-value pair and extract the key and value
    keyValuePairs.forEach {
        val (key, value) = it.split("=")
        val trimmedKey = key.trim()
        val trimmedValue = value.trim()
        map[trimmedKey] = trimmedValue
    }
    // Print the entire map
    println("Resulting Map: ${map[symbol.uppercase()]}")
    return map[symbol.uppercase()]
}