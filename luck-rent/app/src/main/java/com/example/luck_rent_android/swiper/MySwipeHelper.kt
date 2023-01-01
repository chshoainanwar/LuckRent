package com.example.luck_rent_android.swiper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.RectF
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


abstract class MySwipeHelper(
    context: Context,
    private val recycleview: RecyclerView,


    internal var buttonwidth: Int
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    internal var buttonList: MutableList<MyButton>? = null
    lateinit var gestureDetector: GestureDetector
    var swipePosition = 1
    var swipeThreshold = 0.5f
    var moving: Boolean = true

    lateinit var context: Context
    val buttonBuffer: MutableMap<Int, MutableList<MyButton>>
    lateinit var removeQueue: LinkedList<Int>
    abstract fun instantiateMyButton(
        viewHolder: RecyclerView.ViewHolder,
        buffer: MutableList<MyButton>
    )

    private val gestureListener = object : GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {

            if (swipeThreshold == 0.5f) {

            } else {
                for (button in buttonList!!)
                    if (button.onClick(e!!.x, e!!.y))
                        break
            }

                return true
        }

    }

    private val onTouchListener = View.OnTouchListener { _, motionEvent ->


        try {
            if (buttonList != null) {
                if (swipePosition < 0)
                    return@OnTouchListener false
                val point = Point(motionEvent.rawX.toInt(), motionEvent.rawY.toInt())
                val swipeViewHolder = recycleview.findViewHolderForAdapterPosition(swipePosition)
                val swipedItem = swipeViewHolder!!.itemView
                val rect = Rect()
                swipedItem.getGlobalVisibleRect(rect)

                if (motionEvent.action == MotionEvent.ACTION_DOWN || motionEvent.action == MotionEvent.ACTION_MOVE ||
                    motionEvent.action == MotionEvent.ACTION_UP
                ) {


                    if (rect.top < point.y && rect.bottom > point.y) {

                        gestureDetector.onTouchEvent(motionEvent)


                    }
                    else {

                        removeQueue.add(swipePosition)
                        swipePosition = -1
                        swipeThreshold = 0.5f


                    }
                }
                false

            }
            false

        } catch (e: Exception) {
            false
        }

    }

    @Synchronized
    private fun recoverSwipeItem() {

        while (!removeQueue.isEmpty()) {
            val pos = removeQueue.poll()!!.toInt()


            if (pos > -1)

                recycleview.adapter!!.notifyItemChanged(pos)

        }

    }

    init {
        this.buttonList = ArrayList()
        this.gestureDetector = GestureDetector(context, gestureListener)

        this.context = context
        this.recycleview.setOnTouchListener(onTouchListener)
        this.buttonBuffer = HashMap()
        this.removeQueue = IntLinkedList()
        swipeAttach()
    }

    private fun swipeAttach() {
        val itemTouchHelper = ItemTouchHelper(this)
        itemTouchHelper.attachToRecyclerView(recycleview)

    }

    class IntLinkedList : LinkedList<Int>() {
        override fun contains(element: Int): Boolean {
            return false
        }

        override fun lastIndexOf(element: Int): Int {
            return element
        }

        override fun remove(element: Int): Boolean {
            return false
        }

        override fun indexOf(element: Int): Int {
            return element
        }

        override fun add(element: Int): Boolean {
            return if (contains(element))
                false
            else super.add(element)
        }

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        recyclerView.adapter?.notifyDataSetChanged()


        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        Log.d("getValue", "onSwiped: " + buttonwidth)
        val pos = viewHolder.adapterPosition

        if (swipePosition != pos) {
            removeQueue.add(swipePosition)
            swipePosition = pos

        }
        if (buttonBuffer.containsKey(swipePosition)) {
            buttonList = buttonBuffer[swipePosition]

                swipeThreshold = 0.5f * buttonList!!.size.toFloat() * buttonwidth.toFloat()

            Log.d("getValue", "onSwiped: " + swipeThreshold)


        }

        else {
            buttonList!!.clear()
            buttonBuffer.clear()

        }

        recoverSwipeItem()



        !moving

    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        swipeThreshold = 0.5f
        Log.d("getValue", "beforeswipe: " + swipeThreshold)

        return swipeThreshold
    }



    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val pos = viewHolder.adapterPosition
        var translationX = dX
        var itemView = viewHolder.itemView

        if (pos < 0) {
            swipePosition = pos


            return
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {


            if (dX < 0) {
                var buffer: MutableList<MyButton> = ArrayList()
                if (!buttonBuffer.containsKey(pos)) {
                    instantiateMyButton(viewHolder, buffer)
                    buttonBuffer[pos] = buffer
                } else {
                    buffer = buttonBuffer[pos]!!
                }
                translationX = dX * buffer.size.toFloat() * buttonwidth.toFloat() / itemView.width



                drawButton(c, itemView, buffer, pos, translationX)
            }
        }
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            translationX,
            dY,
            actionState,
            isCurrentlyActive
        )
    }

    private fun drawButton(
        c: Canvas,
        itemView: View,
        buffer: MutableList<MyButton>,
        pos: Int,
        translationX: Float
    ) {

        var right = itemView.right.toFloat() - 40
        val buttonwidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 55f, context.resources.displayMetrics).roundToInt()
        for (button in buffer) {
            val left = right - buttonwidth - 7
            button.onDraw(
                c,
                RectF(left, itemView.top.toFloat() +  TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 15f, context.resources.displayMetrics).roundToInt(), right, itemView.bottom.toFloat() -  TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 15f, context.resources.displayMetrics).roundToInt()),
                pos
            )
            right = left
        }

    }


//    override fun getMovementFlags(
//        recyclerView: RecyclerView,
//        viewHolder: RecyclerView.ViewHolder
//    ): Int {
//        return if (viewHolder is CustomAdapter.ViewHolder) {
//            val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.END
//
//            ItemTouchHelper.Callback.makeMovementFlags(
//                0,
//                swipeFlags
//            )
//        } else 0
//    }


}

