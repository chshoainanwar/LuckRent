package com.example.luck_rent_android.ui.main.contractormodule

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.GestureDetector
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.ActivityContractorInvoiceBinding
import com.example.luck_rent_android.ui.main.contractormodule.adapter.ContractorInvoiceAdapter
import com.example.luck_rent_android.ui.main.contractormodule.model.ContractorInvoiceModel
import com.example.luck_rent_android.ui.main.dashboard.Dashboard
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.rentermodule.RenterDashboard
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.google.android.material.navigation.NavigationView
import com.kodextech.project.kodexlib.base.BaseActivity
import com.kodextech.project.kodexlib.utils.gone
import java.lang.IllegalStateException
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.luck_rent_android.ui.main.auth.Login
import com.kodextech.project.kodexlib.utils.visible
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.log
import kotlin.math.roundToInt


class ContractorInvoice : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var binding: ActivityContractorInvoiceBinding? = null
    var renterDashboard: String? = null

    var adapter: ContractorInvoiceAdapter? = null
    var list = ArrayList<ContractorInvoiceModel>()
    var layoutManager: RecyclerView.LayoutManager? = null
    var mAdapter: RecyclerView.Adapter<ContractorInvoiceAdapter.ViewHolderCI>? = null

    private var mDataSideItemCon = ArrayList<SideItems>()
    private var mTopDataSideItemCon = ArrayList<SideItems>()

    private var mDataSideItemRenter = ArrayList<SideItems>()
    private var mTopDataSideItemRenter = ArrayList<SideItems>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_contractor_invoice)
        super.onCreate(savedInstanceState)
        statusBarColor(getColor(R.color.darkBlue))

        setTopBar()
        sideMenuClicks()
        setRecycler()
        SetDummyData()

        renterDashboard = intent.getStringExtra("RenterDashboardPaidInvoice")
        if (renterDashboard == "PaidInvoice") {
            binding?.topBar?.tvText?.text = "Paid Invoices"
        }

        binding?.topBar?.ivAdd?.setOnClickListener {
            onBackPressed()
        }

        if (roleProfile == "Contractor") {
            val swipeHelper = object : MySwipeHelper(
                this, binding?.recycler!!, TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, 105f, resources.displayMetrics
                ).roundToInt()
            ) {
                override fun instantiateMyButton(
                    viewHolder: RecyclerView.ViewHolder,
                    buffer: MutableList<MyButton>
                ) {
                    buffer.add(MyButton(this@ContractorInvoice, R.drawable.transparent_bg,
                        Color.parseColor("#0000ffff"),
                        object :
                            MyButtonClickListener {
                            override fun onClick(pos: Int) {

                                try {
                                    if (list[pos].status == "Unpaid") {
                                        val view = View.inflate(context, R.layout.dialog_mark, null)
                                        val builder = AlertDialog.Builder(context)
                                        builder.setView(view)
                                        builder.setCancelable(true)
                                        val dialog = builder.create()
                                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)



                                        //Register Done Button & Cancel Icon from Dialog
                                        var tvMark =
                                            view?.findViewById<AppCompatTextView>(R.id.tvMark)
                                        var tvDelete =
                                            view?.findViewById<AppCompatTextView>(R.id.tvDelete)
                                        var tvLateNotice =
                                            view?.findViewById<AppCompatTextView>(R.id.tvLateNotice)
                                        var ivMark =
                                            view?.findViewById<AppCompatImageView>(R.id.ivMark)
                                        var ivDelete =
                                            view?.findViewById<AppCompatImageView>(R.id.ivDelete)
                                        var ivLateNotice =
                                            view?.findViewById<AppCompatImageView>(R.id.ivLateNotice)

                                        var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
                                        var rlDelete =
                                            view?.findViewById<RelativeLayout>(R.id.rlDelete)
                                        var rlLateNotice =
                                            view?.findViewById<RelativeLayout>(R.id.rlLateNotice)?.visible()
                                        var view1 =
                                            view?.findViewById<View>(R.id.view1)?.visible()


                                        //Dialog Done Button
                                        rlMark?.setOnClickListener {
                                                list[pos].status = "Paid"
                                                mAdapter?.notifyDataSetChanged()
                                                showToast("Status Updated")
                                                dialog.dismiss()
                                        }
                                        //Dialog Cancel Icon
                                        rlDelete?.setOnClickListener {
                                            list.removeAt(pos)
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Invoice Deleted")
                                            dialog.dismiss()
                                        }
                                        dialog.show()
                                        mAdapter?.notifyDataSetChanged()
                                    }else if (list[pos].status == "Paid"){

                                        val view = View.inflate(context, R.layout.dialog_mark, null)
                                        val builder = AlertDialog.Builder(context)
                                        builder.setView(view)
                                        builder.setCancelable(true)
                                        val dialog = builder.create()
                                        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                                        dialog.show()


                                        //Register Done Button & Cancel Icon from Dialog
                                        var tvMark =
                                            view?.findViewById<AppCompatTextView>(R.id.tvMark)
                                        var tvDelete =
                                            view?.findViewById<AppCompatTextView>(R.id.tvDelete)
                                        var ivMark =
                                            view?.findViewById<AppCompatImageView>(R.id.ivMark)
                                        var ivDelete =
                                            view?.findViewById<AppCompatImageView>(R.id.ivDelete)
                                        var rlMark = view?.findViewById<RelativeLayout>(R.id.rlMark)
                                        var rlDelete =
                                            view?.findViewById<RelativeLayout>(R.id.rlDelete)
                                        var rlLateNotice =
                                            view?.findViewById<RelativeLayout>(R.id.rlLateNotice)?.visible()
                                        var view1 =
                                            view?.findViewById<View>(R.id.view1)?.visible()

                                        tvMark?.text = "Mark As Unpaid"
                                        ivMark?.setImageResource(R.drawable.ic_unpaid2)

                                        //Dialog Done Button
                                        rlMark?.setOnClickListener {
                                            list[pos].status = "Unpaid"
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Status Updated")
                                            dialog.dismiss()
                                        }
                                        //Dialog Cancel Icon
                                        rlDelete?.setOnClickListener {
                                            list.removeAt(pos)
                                            mAdapter?.notifyDataSetChanged()
                                            showToast("Invoice Deleted")
                                            dialog.dismiss()
                                        }
                                        mAdapter?.notifyDataSetChanged()
                                    }
                                } catch (e: Exception) {
                                }


                            }


                        }

                    ))

                }


            }
        }

    }

    private fun setTopBar() {
        binding?.topBar?.rlSearch?.gone()
        binding?.topBar?.rlCounter?.gone()
        binding?.topBar?.ivAdd?.setImageResource(R.drawable.ic_back)
        binding?.topBar?.tvText?.text = "Invoices"
    }

    private fun sideMenuClicks(){
        binding?.dashboardNavMenuLayoutForContractor?.ivCross?.setOnClickListener {
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }
        }
        binding?.dashboardNavMenuLayoutForContractor?.clickaBleNav?.setOnClickListener {
            when (roleProfile) {
                "Landlord" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Property Manager" -> {
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }
                "Contractor" -> {
                    val intent = Intent(this, ContractorDashboard::class.java)
                    startActivity(intent)
                }
                "Renter" -> {
                    val intent = Intent(this, RenterDashboard::class.java)
                    startActivity(intent)
                }
            }

        }
        binding?.dashboardNavMenuLayoutForContractor?.rlProfilePic?.setOnClickListener {

        }
        binding?.dashboardNavMenuLayoutForContractor?.tvName?.setOnClickListener {

        }
        binding?.topBar?.ivMenu?.setOnClickListener {

            when (roleProfile) {
                "Renter" -> {

                    sideMenuDrawerForRenterDas(
                        binding?.dashboardDrawerLayoutContractor,
                        mDataSideItemRenter,
                        binding?.dashboardNavMenuLayoutForContractor,
                        binding?.dashboardNavViewForContractor,
                        this@ContractorInvoice,
                        mTopDataSideItemRenter
                    )

                }
                "Contractor" -> {

                    sideMenuDrawerForContractorDas(
                        binding?.dashboardDrawerLayoutContractor,
                        mDataSideItemCon,
                        binding?.dashboardNavMenuLayoutForContractor,
                        binding?.dashboardNavViewForContractor,
                        this@ContractorInvoice,
                        mTopDataSideItemCon
                    )

                }
            }

        }
        binding?.content?.setOnClickListener {
            try {
                if (binding?.dashboardDrawerLayoutContractor?.isDrawerOpen(GravityCompat.END) == true) {
                    binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.END)
                }
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }
        binding?.dashboardDrawerLayoutContractor?.addDrawerListener(object :
            DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
            }

            override fun onDrawerOpened(drawerView: View) {

                when (roleProfile) {
                    "Renter" -> {

                        sideMenuDrawerForRenterDas(
                            binding?.dashboardDrawerLayoutContractor,
                            mDataSideItemRenter,
                            binding?.dashboardNavMenuLayoutForContractor,
                            binding?.dashboardNavViewForContractor,
                            this@ContractorInvoice,
                            mTopDataSideItemRenter,
                            true
                        )

                    }
                    "Contractor" -> {

                        sideMenuDrawerForContractorDas(
                            binding?.dashboardDrawerLayoutContractor,
                            mDataSideItemCon,
                            binding?.dashboardNavMenuLayoutForContractor,
                            binding?.dashboardNavViewForContractor,
                            this@ContractorInvoice,
                            mTopDataSideItemCon,
                            true
                        )

                    }
                }

            }

            override fun onDrawerClosed(drawerView: View) {
            }

            override fun onDrawerStateChanged(newState: Int) {

                Log.d("getState", "onDrawerStateChanged: " + newState)
            }

        })
    }

    private fun setRecycler() {
        mAdapter = ContractorInvoiceAdapter(this, list)
        binding?.recycler?.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding?.recycler?.adapter = mAdapter
        mAdapter?.notifyDataSetChanged()


    }

    private fun SetDummyData() {
        when(roleProfile){
            "Contractor" ->{
                list.add(
                    ContractorInvoiceModel(
                        "1",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "2",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "3",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "4",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Unpaid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "5",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "6",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Unpaid",
                        "$325.00"
                    )
                )
            }
            "Renter" ->{
                list.add(
                    ContractorInvoiceModel(
                        "1",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "2",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "3",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "4",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "5",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
                list.add(
                    ContractorInvoiceModel(
                        "6",
                        "Thomas Burk",
                        "Jun 10, 2021",
                        "Paid",
                        "$325.00"
                    )
                )
            }
        }

    }

    override fun onSetupViewGroup() {
        mViewGroup = binding?.content
    }

    override fun setupContentViewWithBinding() {

    }

    override fun onRecycleBeforeDestroy() {

    }

    override fun onBecameInvisibleToUser() {

    }

    override fun onBecameVisibleToUser() {

    }

    override fun setupLoader() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding?.dashboardDrawerLayoutContractor?.closeDrawer(GravityCompat.START)
        return true
    }
}


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

    private val gestureListener = object : GestureDetector.OnGestureListener {
        override fun onDown(p0: MotionEvent?): Boolean {
            return true
        }

        override fun onShowPress(p0: MotionEvent?) {
        }


        override fun onSingleTapUp(e: MotionEvent?): Boolean {


            return true
        }

        override fun onScroll(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {


            return true
        }

        override fun onLongPress(p0: MotionEvent?) {
        }

        override fun onFling(p0: MotionEvent?, p1: MotionEvent?, p2: Float, p3: Float): Boolean {
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


                    } else {

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
            var swiper = 0
            if (swiper == 0) {
                for (button in buttonList!!)
                    if (button.onClick(pos))
                        break
                swiper = 1
            } else {
                swiper = 0
            }
            Log.d("getValue", "onSwiped: " + swipeThreshold)


        } else {
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

        var right = itemView.right.toFloat() - 45
        val buttonwidth = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 55f, context.resources.displayMetrics
        ).roundToInt()
        for (button in buffer) {
            val left = right - buttonwidth - 7
            button.onDraw(
                c,
                RectF(
                    left, itemView.top.toFloat() + TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 15f, context.resources.displayMetrics
                    ).roundToInt(), right, itemView.bottom.toFloat() - TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, 15f, context.resources.displayMetrics
                    ).roundToInt()
                ),
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

class MyButton(
    private val context: Context,
    private val imageResId: Int,
    private val color: Int,
    private val listener: MyButtonClickListener
) {
    private var post: Int = 0
    private var clickregion: RectF? = null
    private val resources: Resources

    init {
        resources = context.resources
    }

    fun onClick(pos: Int): Boolean {

        listener.onClick(pos)
        return true

    }

    fun onDraw(c: Canvas, rectF: RectF, post: Int) {

        val p = Paint()
        p.color = color
        c.drawRect(rectF, p)

        p.color = Color.WHITE


        val r = Rect()
        val cHeight = rectF.height()
        val cWidth = rectF.width()
        p.textAlign = Paint.Align.LEFT

        var x = 0f
        var y = 0f
        if (imageResId == 0) {

        } else {

            val d = ContextCompat.getDrawable(context, imageResId)
            val bitmap = drawabletoBitmap(d);
            x = (cWidth - bitmap.width) / 2

            y = (cHeight - bitmap.height) / 2
            c.drawBitmap(bitmap, rectF.left + x + 9, rectF.top + y, p);
        }
        clickregion = rectF
        this.post = post

    }

    fun drawabletoBitmap(d: Drawable?): Bitmap {
        if (d is BitmapDrawable)
            return d.bitmap
        val bitmap =
            Bitmap.createBitmap(d!!.intrinsicWidth, d.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        d.setBounds(0, 0, canvas.width, canvas.height)
        d.draw(canvas)

        return bitmap

    }
}

interface MyButtonClickListener {
    fun onClick(pos: Int) {
        Log.d("getPos", "onClick: " + pos)
    }
}

