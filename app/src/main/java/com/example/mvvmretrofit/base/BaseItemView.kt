package com.example.mvvmretrofit.base

import androidx.annotation.LayoutRes

class BaseItemView<T> {
    private val itemView: ItemView
    private val selector: ItemViewSelector<T>

    private constructor(itemView: ItemView) {
        this.itemView = itemView
        selector = object : ItemViewSelector<T> {
            override fun select(itemView: ItemView?, position: Int, item: T) {}
            override fun viewTypeCount(): Int {
                return 1
            }
        }
    }

    private constructor(selector: ItemViewSelector<T>) {
        itemView = ItemView()
        this.selector = selector
    }

    fun select(position: Int, item: T) {
        selector.select(itemView, position, item)
    }

    fun bindingVariable(): Int {
        return itemView.bindingVariable()
    }

    fun layoutRes(): Int {
        return itemView.layoutRes()
    }

    fun viewTypeCount(): Int {
        return selector.viewTypeCount()
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as BaseItemView<*>
        return if (itemView != that.itemView) false else selector === that.selector
    }

    override fun hashCode(): Int {
        var result = itemView.hashCode()
        result = 31 * result + selector.hashCode()
        return result
    }

    interface ItemViewSelector<T> {
        fun select(itemView: ItemView?, position: Int, item: T)
        fun viewTypeCount(): Int
    }

    class ItemView {
        private var bindingVariable = 0

        @LayoutRes
        private var layoutRes = 0
        operator fun set(bindingVariable: Int, @LayoutRes layoutRes: Int): ItemView {
            this.bindingVariable = bindingVariable
            this.layoutRes = layoutRes
            return this
        }

        fun setBindingVariable(bindingVariable: Int): ItemView {
            this.bindingVariable = bindingVariable
            return this
        }

        fun setLayoutRes(@LayoutRes layoutRes: Int): ItemView {
            this.layoutRes = layoutRes
            return this
        }

        fun bindingVariable(): Int {
            return bindingVariable
        }

        @LayoutRes
        fun layoutRes(): Int {
            return layoutRes
        }

        override fun equals(o: Any?): Boolean {
            if (this === o) return true
            if (o == null || javaClass != o.javaClass) return false
            val itemView = o as ItemView
            return if (bindingVariable != itemView.bindingVariable) false else layoutRes == itemView.layoutRes
        }

        override fun hashCode(): Int {
            var result = bindingVariable
            result = 31 * result + layoutRes
            return result
        }

        companion object {
            const val BINDING_VARIABLE_NONE = 0
            fun of(bindingVariable: Int, @LayoutRes layoutRes: Int): ItemView {
                return ItemView().setBindingVariable(bindingVariable).setLayoutRes(layoutRes)
            }
        }
    }

    companion object {
        fun <T> of(itemView: ItemView): BaseItemView<T> {
            return BaseItemView(itemView)
        }

        fun <T> of(selector: ItemViewSelector<T>): BaseItemView<T> {
            return BaseItemView(selector)
        }
    }
}