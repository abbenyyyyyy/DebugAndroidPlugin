package com.github.abbenyyyyyy.debugplugin.extend

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.github.abbenyyyyyy.debugplugin.base.BaseActivity
import com.github.abbenyyyyyy.debugplugin.base.BaseAndroidViewModel
import java.lang.reflect.ParameterizedType

/**
 * 供 BaseActivity 使用，初始化 viewBinding 的拓展函数
 */
fun <V : ViewBinding, E : BaseAndroidViewModel> BaseActivity<V, E>.getBinding(): V {
    return findClass().getBinding(layoutInflater)
}

internal fun Any.findClass(): Class<*> {
    var javaClass: Class<*> = this.javaClass
    var result: Class<*>? = null
    while (result == null || !result.checkMethod()) {
        result = (javaClass.genericSuperclass as? ParameterizedType)
            ?.actualTypeArguments?.firstOrNull {
                if (it is Class<*>) {
                    it.checkMethod()
                } else {
                    false
                }
            } as? Class<*>
        javaClass = javaClass.superclass
    }
    return result
}

internal fun Class<*>.checkMethod(): Boolean {
    return try {
        getMethod(
            "inflate",
            LayoutInflater::class.java
        )
        true
    } catch (ex: Exception) {
        false
    }
}

internal fun <V : ViewBinding> Class<*>.getBinding(layoutInflater: LayoutInflater): V {
    return try {
        @Suppress("UNCHECKED_CAST")
        getMethod(
            "inflate",
            LayoutInflater::class.java
        ).invoke(null, layoutInflater) as V
    } catch (ex: Exception) {
        throw RuntimeException("The ViewBinding inflate function has been changed.", ex)
    }
}
