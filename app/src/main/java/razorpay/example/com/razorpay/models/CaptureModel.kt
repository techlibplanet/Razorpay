package razorpay.example.com.razorpay.models

class PaymentModel (
    val id: String, val entity: String, val amount: Int, val currency: String,
    val order_id: String,
    val invoice_id: String,
    val international: Boolean,
    val method: String,
    val amount_refunded: Int,
    val refund_status: String, val captured: Boolean,
    val description: String,
    val card_id: String,
    val bank: String,
    val wallet: String,
    val vpa: String,
    val email: String,
    val contact: String,
    val fee: String,
    val tax: String,
    val error_code: String,
    val error_description: String,
    val created_at: Long
) : CommonResult()

class OrderModel(val id : String, val entity : String, val amount : Int, val currency : String,
                 val receipt : String, val status : String, val attempts : Int, val created_at: Long)

class OrderDetails{
    var amount : String? = null
    var currency : String? = null
    var receipt : String? = null
    var payment_capture : String? = null

}

open class CommonResult{
    val isSuccess : Boolean =false
    val message : String = ""
}


class Payment{
    var paymentId : String? = null
}


