package ai.fairytech.moment.sample.ui.cashback

import ai.fairytech.moment.MomentSDK
import ai.fairytech.moment.exception.MomentException
import ai.fairytech.moment.sample.MyApplication
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 캐시백 웹 화면을 로드하는 액티비티.
 */
class CashbackActivity: AppCompatActivity() {

    protected lateinit var moment: MomentSDK

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        moment = MomentSDK.getInstance(applicationContext)

        val redirectTo = intent?.data?.getQueryParameter("redirect_to") ?: ""
        loadCashbackWeb(redirectTo)
    }

    private fun loadCashbackWeb(redirectTo: String) {
        applicationContext.let {
            val momentSdk = (it.applicationContext as MyApplication).momentSdk
            val momentSdkConfig = (it.applicationContext as MyApplication).momentSdkConfig
            momentSdk.setUserId("test-user-id", object : MomentSDK.ResultCallback {
                override fun onSuccess() {
                    momentSdk.launchUI(
                        momentSdkConfig,
                        redirectTo,
                        object : MomentSDK.ResultCallback {
                            override fun onSuccess() {
                                // pass
                            }

                            override fun onFailure(exception: MomentException) {
                                Toast.makeText(
                                    it,
                                    "Cashback web 로드에 실패했습니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                                Log.e(
                                    "MomentSDK",
                                    "loadCashbackWeb onFailure(${exception.errorCode.name}): ${exception.message}"
                                )
                            }
                        }
                    )
                }

                override fun onFailure(exception: MomentException) {
                    Toast.makeText(it, "userId 설정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.e(
                        "MomentSDK",
                        "loadCashbackWeb onFailure(${exception.errorCode.name}): ${exception.message}"
                    )
                }
            })
        }
    }
}