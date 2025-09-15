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

package ai.fairytech.moment.sample.ui.main

import ai.fairytech.moment.MomentSDK
import ai.fairytech.moment.exception.MomentException
import ai.fairytech.moment.sample.MyApplication
import ai.fairytech.moment.sample.databinding.FragmentMainBinding
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * 앱 시작시 구동되는 첫 Fragment.
 * 기능:
 *  - Cashback web 로드 버튼 제공
 */
open class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    protected val binding get() = _binding!!
    private lateinit var moment: MomentSDK

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        moment = MomentSDK.getInstance(requireContext())
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCashbackWeb.setOnClickListener {
            loadCashbackWeb("")
        }
    }

    private fun loadCashbackWeb(redirectTo: String) {
        requireContext()?.let {
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
                                    "loadCashbackWeb onFailure(${exception.getErrorCode().name}): ${exception.message}"
                                )
                            }
                        }
                    )
                }

                override fun onFailure(exception: MomentException) {
                    Toast.makeText(it, "userId 설정에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.e(
                        "MomentSDK",
                        "loadCashbackWeb onFailure(${exception.getErrorCode().name}): ${exception.message}"
                    )
                }
            })
        }
    }
}