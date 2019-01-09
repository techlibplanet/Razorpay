package razorpay.example.com.razorpay.helpers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectActivityComponent
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import razorpay.example.com.razorpay.R
import razorpay.example.com.razorpay.RazorpayApplication
import razorpay.example.com.razorpay.models.OrderDetails
import razorpay.example.com.razorpay.models.OrderModel
import razorpay.example.com.razorpay.models.Payment
import razorpay.example.com.razorpay.network.IPayment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), PaymentResultListener {

    private lateinit var inputMobileNumber: EditText
    private lateinit var inputAmount: EditText
    private lateinit var inputEmail: EditText
    @Inject
    lateinit var paymentService: IPayment
    lateinit var compositeDisposable: CompositeDisposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        compositeDisposable = CompositeDisposable()
        inputAmount = findViewById(R.id.amount)
        inputMobileNumber = findViewById(R.id.mobileNumber)
        inputEmail = findViewById(R.id.email)

        val depComponent = DaggerInjectActivityComponent.builder()
            .applicationComponent(RazorpayApplication.applicationComponent)
            .build()
        depComponent.injectMainActivity(this)

        buttonPay.setOnClickListener {
            // First get the order id from razor pay to Capture the transaction
            getOrderId()
        }
    }

    // To get the order id from razorpay
    private fun getOrderId() {
        val orderDetails = OrderDetails()
        orderDetails.amount = "100"
        orderDetails.receipt = "K${System.currentTimeMillis()}"
        orderDetails.currency = "INR"
        orderDetails.payment_capture = "1"
        compositeDisposable.add(paymentService
            .getOrderId(orderDetails)
            .processRequest(
                { order ->
                    logD("Order Id - ${order.id}")
                    logD("Created at - ${order.created_at}")
                    logD("Status - ${order.status}")

                    // Go to payment gateway to process the transaction
                    startPayment(order.id)
                },
                { err ->
                    logD(err.toString())
                }
            ))
    }

    // Start payment using Razorpay
    fun startPayment(orderId: String) {
        val checkout = Checkout()
        checkout.setImage(R.drawable.app_logo)
        val activity = this
        try {
            val options = JSONObject()
            options.put("name", "Alchemy Education")
            options.put("description", "Order #123456")
            options.put("currency", "INR")
            options.put("order_id", orderId) // ToDo get rzp_order_id by calling razorpay order api
            /**
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            var amount = inputAmount.text.toString().toFloat()
            amount *= 100
            logD("Amount - $amount")

            val email = inputEmail.text.toString().trim()
            val mobileNumber = inputMobileNumber.text.toString().trim()
            options.put("amount", amount)
            val preFill = JSONObject()
            preFill.put("email", email)
            preFill.put("contact", mobileNumber)

            options.put("prefill", preFill)

            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e("Error", "Error in starting Razorpay Checkout", e)
        }

    }

    override fun onPaymentError(errorCode: Int, response: String?) {
        logD("Error - Code - $errorCode Response -$response")
        when (errorCode) {
            Checkout.NETWORK_ERROR -> logD("Network error occourred")
            Checkout.PAYMENT_CANCELED -> logD("Payment Cancelled")
            Checkout.INVALID_OPTIONS -> logD("Invalid Options")
            Checkout.TLS_ERROR -> logD("If device doesn't have TLS 1.1 or TLS 1.2")
        }
    }

    override fun onPaymentSuccess(razorpayPaymentID: String?) {
        logD("Success - $razorpayPaymentID")

        // On success get the transaction details by using "razorpayPaymentId"
        getTransactionDetails(razorpayPaymentID!!)
    }

    // Get the transaction details using Razorpay
    private fun getTransactionDetails(paymentId : String) {
        logD("Inside get transaction details")
        val payment = Payment()
        payment.paymentId = paymentId
        compositeDisposable.add(paymentService.getTransactionByPaymentId(payment)
            .processRequest(
                { transactions ->
                    logD("Captured - ${transactions.captured}")
                    logD("Email - ${transactions.email}")
                    logD("Contact - ${transactions.contact}")
                },
                { err ->
                    logD("Error - $err")
                }
            )
        )
    }


    private fun logD(message: String) {
        Log.d("Razorpay", message)
    }


}
