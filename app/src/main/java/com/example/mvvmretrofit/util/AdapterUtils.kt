package com.example.mvvmretrofit.util

import android.content.Context
import android.content.res.Resources.NotFoundException
import android.os.Looper
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

object AdapterUtils {
    private const val TAG = "BCAdapters"

    /*
     * Helper to throw an exception when {@link android.databinding.ViewDataBinding#setVariable(int,
     * Object)} returns false.
     */
    @JvmStatic
    fun throwMissingVariable(
        binding: ViewDataBinding,
        bindingVariable: Int,
        @LayoutRes layoutRes: Int
    ) {
        val context = binding.root.context
        val resources = context.resources
        val layoutName = resources.getResourceName(layoutRes)
        // Yeah reflection is slow, but this only happens when there is a programmer error.
        val bindingVariableName: String
        bindingVariableName = try {
            getBindingVariableName(context, bindingVariable)
        } catch (e: NotFoundException) {
            // Fall back to int
            "" + bindingVariable
        }
        throw IllegalStateException("Could not bind variable '$bindingVariableName' in layout '$layoutName'")
    }

    /**
     * Returns the name for the given binding variable int. Warning! This uses reflection so it
     * should *only* be used for debugging.
     *
     * @throws Resources.NotFoundException if the name cannot be found.
     */
    @Throws(NotFoundException::class)
    fun getBindingVariableName(context: Context, bindingVariable: Int): String {
        return try {
            getBindingVariableByDataBinderMapper(bindingVariable)
        } catch (e1: Exception) {
            try {
                getBindingVariableByBR(context, bindingVariable)
            } catch (e2: Exception) {
                throw NotFoundException("" + bindingVariable)
            }
        }
    }

    /**
     * Attempt to get the name from a non-public method on the generated DataBinderMapper class.
     * This method does exactly what we want, but who knows if it will be there in future versions.
     */
    @Throws(Exception::class)
    private fun getBindingVariableByDataBinderMapper(bindingVariable: Int): String {
        val dataBinderMapper = Class.forName("android.databinding.DataBinderMapper")
        val convertIdMethod =
            dataBinderMapper.getDeclaredMethod("convertBrIdToString", Int::class.javaPrimitiveType)
        convertIdMethod.isAccessible = true
        val constructor = dataBinderMapper.getDeclaredConstructor()
        constructor.isAccessible = true
        val instance = constructor.newInstance()
        val result = convertIdMethod.invoke(instance, bindingVariable)
        return result as String
    }

    /**
     * Attempt to get the name by using reflection on the generated BR class. Unfortunately, we
     * don't know BR's package name so this may fail if it's not the same as the apps package name.
     */
    @Throws(Exception::class)
    private fun getBindingVariableByBR(context: Context, bindingVariable: Int): String {
        val packageName = context.packageName
        val BRClass = Class.forName("$packageName.BR")
        val fields = BRClass.fields
        for (field in fields) {
            val value = field.getInt(null)
            if (value == bindingVariable) {
                return field.name
            }
        }
        throw Exception("not found")
    }

    /**
     * Ensures the call was made on the main thread. This is enforced for all ObservableList change
     * operations.
     */
    @JvmStatic
    fun ensureChangeOnMainThread() {
        check(!(Thread.currentThread() !== Looper.getMainLooper().thread)) { "You must only modify the ObservableList on the main thread." }
    }
}