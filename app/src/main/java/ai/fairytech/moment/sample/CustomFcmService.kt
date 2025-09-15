package ai.fairytech.moment.sample

import ai.fairytech.moment.MomentSDK
import ai.fairytech.moment.exception.MomentException
import ai.fairytech.moment.handleMessage
import ai.fairytech.moment.isFairyMessage
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFcmService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val moment = MomentSDK.getInstance(applicationContext)
        moment.addDeviceToken(token, object: MomentSDK.ResultCallback {
            override fun onFailure(exception: MomentException) {
                Log.d("CustomFcmService", "Failed to add device token: ${exception.getErrorCode()}, ${exception.message}")
            }

            override fun onSuccess() {
            }
        })
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val moment = MomentSDK.getInstance(applicationContext)
        if (moment.isFairyMessage(message)) {
            moment.handleMessage(message)
        }
    }
}