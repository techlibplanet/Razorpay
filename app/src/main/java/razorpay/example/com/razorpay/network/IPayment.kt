package razorpay.example.com.razorpay.network

import io.reactivex.Observable
import razorpay.example.com.razorpay.models.*
import retrofit2.http.*

interface IPayment {

    @POST("GetOrderDetails.php")
    fun getOrderId(@Body orderDetails: OrderDetails) : Observable<OrderModel>

    @POST("getTransactionById.php")
    fun getTransactionByPaymentId(@Body payment : Payment) : Observable<PaymentModel>

}