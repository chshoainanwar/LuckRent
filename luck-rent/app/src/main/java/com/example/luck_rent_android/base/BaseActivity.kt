@file:Suppress("unused")

package com.kodextech.project.kodexlib.base


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.*
import android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
import android.view.animation.RotateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R
import com.example.luck_rent_android.databinding.SideMenuBinding
import com.example.luck_rent_android.ui.main.adds.MakeAdd
import com.example.luck_rent_android.ui.main.adds.MyAds
import com.example.luck_rent_android.ui.main.adds.analytics.AnalyticsScreen
import com.example.luck_rent_android.ui.main.auth.Login
import com.example.luck_rent_android.ui.main.chat.Chat
import com.example.luck_rent_android.ui.main.chat.Messages
import com.example.luck_rent_android.ui.main.contractormodule.ContractorInvoice
import com.example.luck_rent_android.ui.main.contractormodule.ContractorOverview
import com.example.luck_rent_android.ui.main.contractormodule.ContractorRentProperty
import com.example.luck_rent_android.ui.main.faqs.FaqsActivity
import com.example.luck_rent_android.ui.main.findtradesperson.FIndTradesPerson

import com.example.luck_rent_android.ui.main.myreport.Reports
import com.example.luck_rent_android.ui.main.order.OrderActivity
import com.example.luck_rent_android.ui.main.profile.Profile
import com.example.luck_rent_android.ui.main.profile.Profile.Companion.roleProfile
import com.example.luck_rent_android.ui.main.property.PropertiesActivity
import com.example.luck_rent_android.ui.main.renter.Renter
import com.example.luck_rent_android.ui.main.rentermodule.BuyTenantInsurance
import com.example.luck_rent_android.ui.main.rentermodule.RenterNewProperties
import com.example.luck_rent_android.ui.main.sideMenu.SideItems
import com.example.luck_rent_android.ui.main.sideMenu.SideMenuAdapter
import com.example.luck_rent_android.ui.main.transaction.Transaction
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.kodextech.glitterupsapp.utils.loader.ProgressDialogue
import com.kodextech.project.kodexlib.utils.HideUtil
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@Suppress("unused")
abstract class BaseActivity : AppCompatActivity() {
    //    var progressItem:ProgressDialog
    var mViewGroup: ViewGroup? = null
    private val pd = ProgressDialogue()

    /*For Facebook KeyHash GettingPermissionUtils*/
    @Suppress("DEPRECATION")
    @SuppressLint("PackageManagerGetSignatures")
    fun printHashKey(pContext: Context) {
        try {
            val info: PackageInfo = pContext.packageManager
                .getPackageInfo(pContext.packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
//                Timber.i("printHashKey() Hash Key: $hashKey")
                Log.e("printHashKey()", "printHashKey() Hash Key: $hashKey")
            }
        } catch (e: NoSuchAlgorithmException) {
            Log.e("printHashKey()", e.localizedMessage, e)
        } catch (e: java.lang.Exception) {
            Log.e("printHashKey()", e.localizedMessage, e)
        }
    }


    fun View.snack(message: String, duration: Int = Snackbar.LENGTH_LONG) {
        Snackbar.make(this, message, duration).show()
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
//
//    fun Context.showBarToast(message: String) {
//        val toast = Toast(this)
//        val contex = this
//        toast.apply {
//            val layout = LayoutInflater.from(contex).inflate(R.layout.custom_toast, null, false)
//            layout?.findViewById<TextView>(R.id.tvMessage)?.text = message
//            setGravity(Gravity.BOTTOM, 0, 0)
//            duration = Toast.LENGTH_SHORT
//            view = layout
//            show()
//        }
//    }

    fun statusBarColor(color: Int) {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    //
    fun makeTopBottomTransparent() {
        val w: Window = window // in Activity's onCreate() for instance
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,// or
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )// For Display Image View On Status Bar Background
    }

    fun makeLightContentStatusBar(value: Boolean = true) {
        setSystemUiLightStatusBar(value)
    }

    @Suppress("DEPRECATION")
    private fun setSystemUiLightStatusBar(isLightStatusBar: Boolean) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                val systemUiAppearance = if (isLightStatusBar) {
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                } else {
                    0
                }
                window.insetsController?.setSystemBarsAppearance(
                    systemUiAppearance,
                    WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                )
            } else {
                val systemUiVisibilityFlags = if (isLightStatusBar) {
                    window.decorView.systemUiVisibility or SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                } else {
                    window.decorView.systemUiVisibility and SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
                }
                window.decorView.systemUiVisibility = systemUiVisibilityFlags
            }
        }
    }

/* For Getting Screen Size From Window On Context
    @Suppress("DEPRECATION")
    fun setupScreenWidthHeight():Point {

        val displayMetrics = DisplayMetrics()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display?.getRealMetrics(displayMetrics)
        } else {
            this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        val display = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            display
        } else {
            windowManager.defaultDisplay
        }
        val size = Point()
        display?.getRealSize(size)
        return size
        //        DataManager.setDeviceHeight(size.y)
//        DataManager.setDeviceWidth(size.x)
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onSetupViewGroup()
        setupContentViewWithBinding()
        setupLoader()
        if (mViewGroup != null) {
            HideUtil.init(this, mViewGroup)
        }

    }

    abstract fun onSetupViewGroup()
    abstract fun setupContentViewWithBinding()
    abstract fun onRecycleBeforeDestroy()
    abstract fun onBecameInvisibleToUser()
    abstract fun onBecameVisibleToUser()
    abstract fun setupLoader()


    override fun onDestroy() {
        onRecycleBeforeDestroy()
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        onBecameVisibleToUser()

    }

    override fun onPause() {
        onBecameInvisibleToUser()
        super.onPause()
    }


    fun showLoading(text: String = "Please wait...") {
        runOnUiThread {
            try {
                if (!pd.isVisible && !pd.isAdded) {
                    pd.show(supportFragmentManager, "pd")
                }
                android.os.Handler(Looper.getMainLooper()).postDelayed({
                    pd.txtProgress?.text = text
                }, 200)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }


    fun hideLoading() {
        try {
            if (pd.isAdded || pd.isVisible) pd.dismiss()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    fun String.getFormated(input: String = "yyyy-MM-dd'T'HH:mm:ss"): Date {
        val utcFormat = SimpleDateFormat(input, Locale.getDefault())
//    utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.parse(this.split(".").first()) ?: Date()
    }

    fun Date.getFormated(
        output: String = "yyyy-MM-dd'T'HH:mm:ss"
    ): String {
        val utcFormat = SimpleDateFormat(output, Locale.getDefault())
        // utcFormat.timeZone = timeZone
        return utcFormat.format(this)
    }

    fun sideMenuDrawer(
        dashboardDrawerLayout: DrawerLayout?,
        mDataSideItem: ArrayList<SideItems>,
        dashboardNavMenuLayout: SideMenuBinding?,
        dashboardNavView: NavigationView?,
        dashboard: NavigationView.OnNavigationItemSelectedListener,
        mTopDataSideItem: ArrayList<SideItems>,
        toOpen: Boolean? = false
    ) {
        dashboardDrawerLayout?.drawerElevation = 0F
        if (toOpen == false) {
            if (dashboardDrawerLayout?.isDrawerOpen(GravityCompat.END) == true) {
                dashboardDrawerLayout.closeDrawer(GravityCompat.END)
            } else {
                dashboardDrawerLayout?.openDrawer(GravityCompat.END)
            }
        }




        mTopDataSideItem.clear()
        mDataSideItem.clear()

        mTopDataSideItem.add(SideItems("My Profile", R.drawable.ic_profile))
        mTopDataSideItem.add(SideItems("My Properties", R.drawable.ic_property))
        mTopDataSideItem.add(SideItems("Transaction Overview", R.drawable.ic_transaction))
        mTopDataSideItem.add(SideItems("Chat", R.drawable.ic_chat))
        mTopDataSideItem.add(SideItems("Order", R.drawable.ic_order))
        mTopDataSideItem.add(SideItems("My Ads", R.drawable.ic_add))
        mTopDataSideItem.add(SideItems("My Reports", R.drawable.ic_report_menu))
//        mTopDataSideItem.add(SideItems("Add Renter", R.drawable.ic_add_rental))

        dashboardNavMenuLayout?.rvTop?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayout?.rvTop?.adapter =
            SideMenuAdapter(this, mTopDataSideItem, "top") { position ->
                dashboardDrawerLayout?.closeDrawer(GravityCompat.END)
                dashboardNavView?.setNavigationItemSelectedListener(dashboard)
                when (position) {
                    0 -> {
                        val intent = Intent(this, Profile::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this, PropertiesActivity::class.java)
                        intent.putExtra("isFor", "1")
                        startActivity(intent)
                    }

                    2 -> {
                        val intent = Intent(this, Transaction::class.java)
                        startActivity(intent)
                    }

                    3 -> {


                        if (roleProfile == "Landlord") {
                            val intent = Intent(this, Messages::class.java)
                            startActivity(intent)


                        } else if (roleProfile == "Property Manager") {
                            val intent = Intent(this, Messages::class.java)
                            startActivity(intent)

                        }
                    }

                    4 -> {


                        if (roleProfile == "Landlord") {
                            val intent = Intent(this, OrderActivity::class.java)
                            startActivity(intent)
                        } else if (roleProfile == "Property Manager") {
                            val intent = Intent(this, OrderActivity::class.java)
                            startActivity(intent)
                        }
                    }
                    5 -> {
                        val intent = Intent(this, MyAds::class.java)
                        startActivity(intent)
                    }
                    6 -> {
                        val intent = Intent(this, Reports::class.java)
                        startActivity(intent)
                    }
//                    7 -> {
//                        val intent = Intent(this, Renter::class.java)
//                        startActivity(intent)
//                    }
                }

            }



        mDataSideItem.add(SideItems("FAQS", R.drawable.ic_faqs))
        mDataSideItem.add(SideItems("Contact Support", R.drawable.ic_contact))
        mDataSideItem.add(SideItems("Find Tradesperson", R.drawable.ic_trans_person))
        mDataSideItem.add(SideItems("Rate LuckRent", R.drawable.ic_star))
        mDataSideItem.add(SideItems("Share LuckRent", R.drawable.ic_share))
        mDataSideItem.add(SideItems("Privacy", R.drawable.ic_privacy))


        dashboardNavMenuLayout?.rvBelow?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayout?.rvBelow?.adapter =
            SideMenuAdapter(this, mDataSideItem, "bottom") { position ->
                dashboardDrawerLayout?.closeDrawer(GravityCompat.END)
                dashboardNavView?.setNavigationItemSelectedListener(dashboard)
                when (position) {
                    0 -> {
                        val intent = Intent(this, FaqsActivity::class.java)
                        startActivity(intent)
                    }
                    1 -> {

                    }
                    2 -> {
                        val intent = Intent(this, FIndTradesPerson::class.java)
                        startActivity(intent)
                    }

                    3 -> {
                        openDialogForRate()
                    }
                    4 -> {
                        val sendIntent = Intent()
                        sendIntent.action = Intent.ACTION_SEND
                        sendIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "\n" +
                                    "LuckRent"
                        )
                        sendIntent.type = "text/plain"
                        startActivity(sendIntent)
                    }
                }
            }

    }


    fun sideMenuDrawerForContractorDas(
        dashboardDrawerLayoutCon: DrawerLayout?,
        mDataSideItemCon: ArrayList<SideItems>,
        dashboardNavMenuLayoutCon: SideMenuBinding?,
        dashboardNavViewCon: NavigationView?,
        dashboardCon: NavigationView.OnNavigationItemSelectedListener,
        mTopDataSideItemCon: ArrayList<SideItems>,
        toOpen: Boolean? = false
    ) {
        dashboardDrawerLayoutCon?.drawerElevation = 0F
        if (toOpen == false) {
            if (dashboardDrawerLayoutCon?.isDrawerOpen(GravityCompat.END) == true) {
                dashboardDrawerLayoutCon.closeDrawer(GravityCompat.END)
            } else {
                dashboardDrawerLayoutCon?.openDrawer(GravityCompat.END)
            }
        }
        mTopDataSideItemCon.clear()
        mDataSideItemCon.clear()

        mTopDataSideItemCon.add(SideItems("My Profile", R.drawable.ic_profile))
        mTopDataSideItemCon.add(SideItems("Property Details", R.drawable.ic_invoices))
        mTopDataSideItemCon.add(SideItems("Chat", R.drawable.ic_chat))
        mTopDataSideItemCon.add(SideItems("Invoices", R.drawable.ic_invoices))
        mTopDataSideItemCon.add(SideItems("Advertise On LuckRent", R.drawable.ic_dollar))
        mTopDataSideItemCon.add(SideItems("Buy Trades Insurance", R.drawable.ic_dollar))

        mDataSideItemCon.add(SideItems("FAQS", R.drawable.ic_faqs))
        mDataSideItemCon.add(SideItems("Contact Support", R.drawable.ic_contact))
        mDataSideItemCon.add(SideItems("Find Tradesperson", R.drawable.ic_trans_person))
        mDataSideItemCon.add(SideItems("Rate LuckRent", R.drawable.ic_star))
        mDataSideItemCon.add(SideItems("Share LuckRent", R.drawable.ic_share))
        mDataSideItemCon.add(SideItems("Privacy Settings", R.drawable.ic_privacy))


        dashboardNavMenuLayoutCon?.rvBelow?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayoutCon?.rvBelow?.adapter =
            SideMenuAdapter(this, mDataSideItemCon, "bottom") { position ->
                dashboardDrawerLayoutCon?.closeDrawer(GravityCompat.END)
                dashboardNavViewCon?.setNavigationItemSelectedListener(dashboardCon)
                when (position) {
                    0 -> {
//                      val intent = Intent(this, FaqsActivity::class.java)
//                      startActivity(intent)
                    }
                    1 -> {

                    }
                    2 -> {
                        val intent = Intent(this, FIndTradesPerson::class.java)
                        startActivity(intent)
                    }

                    3 -> {
                        openDialogForRate()
                    }
                    4 -> {
//                      val sendIntent = Intent()
//                      sendIntent.action = Intent.ACTION_SEND
//                      sendIntent.putExtra(
//                          Intent.EXTRA_TEXT,
//                          "\n" +
//                                  "LuckRent"
//                      )
//                      sendIntent.type = "text/plain"
//                      startActivity(sendIntent)
                    }
                }
            }


        dashboardNavMenuLayoutCon?.rvTop?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayoutCon?.rvTop?.adapter =
            SideMenuAdapter(this, mTopDataSideItemCon, "top") { position ->
                dashboardDrawerLayoutCon?.closeDrawer(GravityCompat.END)
                dashboardNavViewCon?.setNavigationItemSelectedListener(dashboardCon)
                when (position) {
                    0 ->{
                        val intent = Intent(this, Profile::class.java)
                        startActivity(intent)
                    }
                    1 ->{
                        val intent = Intent(this, ContractorOverview::class.java)
                        startActivity(intent)

                    }
                    2 -> {
                        val intent = Intent(this, Messages::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this, ContractorInvoice::class.java)
                        startActivity(intent)
                    }
                    4 -> {
                        openAdvertiseDialog()
                    }


                }


            }
    }


    fun sideMenuDrawerForRenterDas(
        dashboardDrawerLayoutRenter: DrawerLayout?,
        mDataSideItemRenter: ArrayList<SideItems>,
        dashboardNavMenuLayoutRenter: SideMenuBinding?,
        dashboardNavViewRenter: NavigationView?,
        dashboardRenter: NavigationView.OnNavigationItemSelectedListener,
        mTopDataSideItemRenter: ArrayList<SideItems>,
        toOpen: Boolean? = false
    ) {
        dashboardDrawerLayoutRenter?.drawerElevation = 0F
        if (toOpen == false) {
            if (dashboardDrawerLayoutRenter?.isDrawerOpen(GravityCompat.END) == true) {
                dashboardDrawerLayoutRenter.closeDrawer(GravityCompat.END)
            } else {
                dashboardDrawerLayoutRenter?.openDrawer(GravityCompat.END)
            }
        }
        mTopDataSideItemRenter.clear()
        mDataSideItemRenter.clear()

        mTopDataSideItemRenter.add(SideItems("My Profile", R.drawable.ic_profile))
        mTopDataSideItemRenter.add(SideItems("My Rental Home", R.drawable.ic_property))
        mTopDataSideItemRenter.add(SideItems("Chat", R.drawable.ic_chat))
        mTopDataSideItemRenter.add(SideItems("Rental Ads", R.drawable.ic_add))
        mTopDataSideItemRenter.add(SideItems("Invoices", R.drawable.ic_invoices))
        mTopDataSideItemRenter.add(SideItems("Buy Tenant Insurance", R.drawable.ic_dollar))

        mDataSideItemRenter.add(SideItems("FAQS", R.drawable.ic_faqs))
        mDataSideItemRenter.add(SideItems("Contact Support", R.drawable.ic_contact))
        mDataSideItemRenter.add(SideItems("Find Tradesperson", R.drawable.ic_trans_person))
        mDataSideItemRenter.add(SideItems("Rate LuckRent", R.drawable.ic_star))
        mDataSideItemRenter.add(SideItems("Share LuckRent", R.drawable.ic_share))
        mDataSideItemRenter.add(SideItems("Privacy Settings", R.drawable.ic_privacy))


        dashboardNavMenuLayoutRenter?.rvBelow?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayoutRenter?.rvBelow?.adapter =
            SideMenuAdapter(this, mDataSideItemRenter, "bottom") { position ->
                dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.END)
                dashboardNavViewRenter?.setNavigationItemSelectedListener(dashboardRenter)
                when (position) {
                    0 -> {
                      val intent = Intent(this, FaqsActivity::class.java)
                      startActivity(intent)
                    }
                    1 -> {

                    }
                    2 -> {
                        val intent = Intent(this, FIndTradesPerson::class.java)
                        startActivity(intent)
                    }

                    3 -> {
                        openDialogForRate()
                    }
                    4 -> {
                      val sendIntent = Intent()
                      sendIntent.action = Intent.ACTION_SEND
                      sendIntent.putExtra(
                          Intent.EXTRA_TEXT,
                          "\n" +
                                  "LuckRent"
                      )
                      sendIntent.type = "text/plain"
                      startActivity(sendIntent)
                    }
                }
            }


        dashboardNavMenuLayoutRenter?.rvTop?.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        dashboardNavMenuLayoutRenter?.rvTop?.adapter =
            SideMenuAdapter(this, mTopDataSideItemRenter, "top") { position ->
                dashboardDrawerLayoutRenter?.closeDrawer(GravityCompat.END)
                dashboardNavViewRenter?.setNavigationItemSelectedListener(dashboardRenter)
                when (position) {
                    0 -> {
                        val intent = Intent(this, Profile::class.java)
                        startActivity(intent)
                    }
                    1 -> {
                        val intent = Intent(this,ContractorRentProperty::class.java)
                        intent.putExtra("FromDashboard","ToProperties")
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this, Messages::class.java)
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this, MyAds::class.java)
                        startActivity(intent)
                    }
                    4 -> {
                        val intent = Intent(this, ContractorInvoice::class.java)
                        intent.putExtra("RenterDashboardPaidInvoice","PaidInvoice")
                        startActivity(intent)
                    }
                    5 -> {
                        val intent = Intent(this, BuyTenantInsurance::class.java)
//                        intent.putExtra("FromBaseActivity","ToContractorProperty")
                        startActivity(intent)
                    }


                }


            }
    }


    fun openDialogForRate() {
        val view = View.inflate(this, R.layout.dialogue_rate_us, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        var cancelIcon = view.findViewById<ImageView>(R.id.cancelIcon)
        cancelIcon.setOnClickListener {
            dialog.dismiss()
        }

    }

    fun checkAnimState(image: ImageView, animState: Boolean) {
        var animation: RotateAnimation? = null

        if (animState) {
            animation = RotateAnimation(
                0f,
                180f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
            )
        } else {
            animation = RotateAnimation(
                180f,
                0f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f,
                RotateAnimation.RELATIVE_TO_SELF,
                0.5f
            )
        }
        animation.duration = 200
        animation.fillAfter = true
        image.startAnimation(animation)
    }



    private fun openAdvertiseDialog() {
        val view = View.inflate(this, R.layout.dialog_advertise, null)
        val builder = AlertDialog.Builder(this)
        builder.setView(view)
        builder.setCancelable(false)
        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()


        //Register Done Button & Cancel Icon from Dialog
        var cancelIcon = view?.findViewById<ImageView>(R.id.cancelIcon)


        //Dialog Cancel Icon
        cancelIcon?.setOnClickListener {
            dialog.dismiss()
        }

    }


}





