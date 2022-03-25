package com.github.abbenyyyyyy.debugplugin

import com.github.abbenyyyyyy.debugplugin.base.BaseActivity
import com.github.abbenyyyyyy.debugplugin.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun subscribeUi(viewModel: MainViewModel) {
    }

    override fun initView() {
        binding.centerText.text = getString(R.string.main_activity_text)
    }
}