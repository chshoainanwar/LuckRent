package com.example.luck_rent_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luck_rent_android.R

class PagerAdapter(
//    private var title1: List<String>,
//    private var title2: List<String>,
//    private var images: List<Int>
) : RecyclerView.Adapter<PagerAdapter.ViewHolder>() {
var line1 : String? ="The rental management app"
var line2 : String? ="that goes beyond simply"
var line3 : String? ="keeping track of properties"
var line4 : String? ="Take and send photos when you"
var line5 : String? ="communicate with your tenants"
var line6 : String? ="and contractors by text, email or phone"
var line7 : String? ="You can use our app offline to manage"
var line8 : String? ="your rentals like a professional,"
var line9 : String? ="giving you more time back for your loved ones"
var line10 : String? ="The solutions we provide address"
var line11 : String? ="a broad range of rental issues, including"
var line12 : String? ="estimating rental rates on a rental property"
    //array of text1
    var title1 = arrayListOf<String>("Welcome to LuckRent App","Chat Within App","Manage With Ease","Do More Simply")

    //array of text2
    var title2 = arrayListOf<String>(line1 + "\n" + line2 + "\n" + line3,
        line4 + "\n" + line5 + "\n" + line6,
        line7 + "\n" + line8 + "\n" + line9,
        line10 + "\n" + line11 + "\n" + line12)

    //    var title1 = arrayOf(R.string.pager_text,R.string.pager_texttwo,R.string.pager_textthree,R.string.pager_textfour)
    //    var title2 = arrayOf(R.string.pager_textfive,R.string.pager_textsix,R.string.pager_textseven,R.string.pager_texteight)

    //array of images
    var images = intArrayOf(
        R.drawable.ic_pagerpic1, R.drawable.ic_pagerpic2, R.drawable.ic_pagerpic3,
        R.drawable.ic_pagerpic4
    )
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerAdapter.ViewHolder {

        //layout inflate
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.paegr_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return title1.size
    }

    override fun onBindViewHolder(holder: PagerAdapter.ViewHolder, position: Int) {

        holder.pagertext1.text = title1[position]
        holder.pagertext2.text = title2[position].toString()
        holder.pagerpic.setImageResource(images[position])

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pagerpic: ImageView
        var pagertext1: TextView
        var pagertext2: TextView

        init {
            pagerpic = itemView.findViewById(R.id.pagerpic)
            pagertext1 = itemView.findViewById(R.id.pagertext1)
            pagertext2 = itemView.findViewById(R.id.pagertext2)
        }
    }
}
