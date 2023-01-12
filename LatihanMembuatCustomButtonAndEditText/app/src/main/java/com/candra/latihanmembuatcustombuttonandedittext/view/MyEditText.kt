package com.candra.latihanmembuatcustombuttonandedittext.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.candra.latihanmembuatcustombuttonandedittext.R

class MyEditText: AppCompatEditText,View.OnTouchListener
{
    internal lateinit var mClearButtonImage: Drawable

    constructor(context:Context): super(context){
        init()
    }

    constructor(context: Context,attr: AttributeSet): super(context,attr){
        init()
    }

    constructor(context: Context,attr: AttributeSet,defStyleAttr: Int): super(context,attr,defStyleAttr){
        init()
    }



    private fun init(){
        mClearButtonImage = ContextCompat.getDrawable(context, R.drawable.ic_baseline_close_24) as Drawable
        setOnTouchListener(this)

        addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Do Nothong In Here
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
               if (s.toString().isNotEmpty()) showClearButton() else hideClearButton()
            }

            override fun afterTextChanged(p0: Editable?) {
                // Di Nothing In Here
            }

        })
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        hint = resources.getString(R.string.hint)
        textAlignment = View.TEXT_ALIGNMENT_VIEW_START
    }

    private fun showClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(null,null,mClearButtonImage,null)
    }

    private fun hideClearButton(){
        setCompoundDrawablesWithIntrinsicBounds(null,null,null,null)
    }

    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {

        if (compoundDrawables[2] != null){
            val clearButtonStart: Float
            val clearButtonEnd: Float
            var isClearButtonClicked = false
            when(layoutDirection){
                View.LAYOUT_DIRECTION_RTL -> {
                    clearButtonEnd = (mClearButtonImage.intrinsicWidth + paddingStart).toFloat()
                    if (event != null) {
                        when{
                            event.x < clearButtonEnd -> isClearButtonClicked = true
                        }
                    }
                }
                else -> {
                    clearButtonStart = (width - paddingEnd - mClearButtonImage.intrinsicWidth).toFloat()
                    if (event != null) {
                        when{
                            event.x > clearButtonStart -> isClearButtonClicked = true
                        }
                    }
                }
            }

            when{
                isClearButtonClicked -> if (event != null) {
                    when{
                        event.action == MotionEvent.ACTION_DOWN -> {
                            mClearButtonImage = ContextCompat.getDrawable(context,R.drawable.ic_baseline_close_24) as Drawable
                            showClearButton()
                            return true
                        }
                        event.action == MotionEvent.ACTION_UP -> {
                            mClearButtonImage = ContextCompat.getDrawable(context,R.drawable.ic_baseline_close_24) as Drawable
                            when{
                                text != null -> text?.clear()
                            }
                            hideClearButton()
                            return true
                        }
                        else -> return false
                    }
                }
                else -> return false
            }
        }
        return false
    }
}