/*
 * Fairy Technologies CONFIDENTIAL
 * __________________
 *
 * Copyright (C) Fairy Technologies, Inc - All Rights Reserved
 *
 * NOTICE:  All information contained herein is, and remains the property of Fairy
 * Technologies Incorporated and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Fairy Technologies Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents, patents in
 * process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information,or reproduction or modification of this material
 * is strictly forbidden unless prior written permission is obtained from Fairy
 * Technologies Incorporated.
 *
 */

package ai.fairytech.moment.sample

import ai.fairytech.moment.MomentSDK
import ai.fairytech.moment.exception.MomentException
import ai.fairytech.moment.sample.notification.NotificationController
import android.app.Application

class MyApplication : Application() {
    lateinit var momentSdk: MomentSDK
        private set
    lateinit var momentSdkConfig: MomentSDK.Config
        private set

    override fun onCreate() {
        super.onCreate()
        NotificationController.getInstance()?.init(applicationContext)
        momentSdk = MomentSDK.getInstance(applicationContext)
        momentSdkConfig = getConfig()
        momentSdk.restartIfNeeded(
            momentSdkConfig,
            object : MomentSDK.RestartResultCallback {
                override fun onSuccess(resultCode: MomentSDK.RestartResultCode) {
                    if (resultCode == MomentSDK.RestartResultCode.SERVICE_RESTARTED) {
                        // restart에 성공했습니다.
                    }
                }

                override fun onFailure(exception: MomentException) {
                    // restart에 실패했습니다.
                }
            }
        )
    }

    private fun getConfig(): MomentSDK.Config {
        return MomentSDK.Config(applicationContext)
            .notificationChannelId(NotificationController.NOTIFICATION_CHANNEL_ID) // 알림 채널 아이디
            .notificationId(NotificationController.NOTIFICATION_ID) // 알림 아이디
            .notificationIconResId(R.drawable.baseline_person_24)
            .serviceNotificationChannelId(NotificationController.SERVICE_NOTIFICATION_CHANNEL_ID) // 서비스를 위해 필요한 채널아이디
            .serviceNotificationId(NotificationController.SERVICE_NOTIFICATION_ID)
            .serviceNotificationIconResId(R.drawable.baseline_person_24)
            .serviceNotificationTitle("인식 서비스")
            .serviceNotificationText("인식 서비스가 동작 중입니다")
    }
}


