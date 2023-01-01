package com.example.luck_rent_android.swiper

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.example.luck_rent_android.ui.main.contractormodule.MyButtonClickListener

class MyButton(private val context:Context  ,

private val imageResId : Int ,
private val color:Int ,
private val listener: MyButtonClickListener
) {
    private var  post: Int = 0
    private var clickregion: RectF? =  null
    private val resources:Resources

    init {
        resources = context.resources
    }

    fun onClick(x:Float , y:Float) : Boolean{
        if (clickregion!=null && clickregion!!.contains(x,y))
        {
            listener.onClick(post)
            return  true
        }
        return false
    }

    fun onDraw( c : Canvas , rectF: RectF , post:Int){

        val p = Paint()
        p.color = color
        c.drawRect(rectF,p)

        p.color = Color.WHITE


        val r = Rect()
        val cHeight = rectF.height()
        val cWidth = rectF.width()
        p.textAlign = Paint.Align.LEFT

        var x = 0f
        var y = 0f
        if (imageResId == 0){

        }else{

            val d = ContextCompat.getDrawable(context , imageResId)
            val bitmap = drawabletoBitmap(d);
            x= (cWidth  - bitmap.width) /2

            y = (cHeight - bitmap.height) /2
            c.drawBitmap(bitmap , rectF.left+x +9 , rectF.top+y,p);
        }
        clickregion = rectF
        this.post = post

    }

    fun drawabletoBitmap(d: Drawable?) : Bitmap{
        if (d is BitmapDrawable)
            return d.bitmap
        val bitmap = Bitmap.createBitmap(d!!.intrinsicWidth , d.intrinsicHeight , Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.setBounds(0,0,canvas.width, canvas.height)
        d.draw(canvas)

        return bitmap

    }
}