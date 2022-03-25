package com.github.abbenyyyyyy.debugplugin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.github.abbenyyyyyy.debugplugin.extend.getBinding
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

abstract class BaseActivity<V : ViewBinding, E : BaseAndroidViewModel> : AppCompatActivity() {
    protected lateinit var binding: V

    @Suppress("UNCHECKED_CAST")
    protected val mViewModel: E by lazy {
        // 这里获得到的是类的泛型的类型
        val type: Type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        ViewModelProvider(this)[type as Class<E>]
    }

    /**
     * 绑定ViewModel与View,
     * 当数据改变时通知View
     *
     * @param viewModel viewModel
     */
    protected abstract fun subscribeUi(viewModel: E)

    protected abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getBinding()
        setContentView(binding.root)
        subscribeUi(mViewModel)
        initView()
    }
}