package com.example.hotelbooking.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.LinearLayout.LayoutParams
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.example.hotelbooking.R
import com.example.hotelbooking.adapter.IntroSliderAdapter
import com.example.hotelbooking.models.IntroSlide
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var imgview: ImageView
    private lateinit var btn : Button

    private val introSliderAdapter = IntroSliderAdapter(
        listOf(
            IntroSlide(
            "Let's discover the world  with us",
            R.drawable.img_2 ),
            IntroSlide(
                "Let's have the best vacation with us",
                R.drawable.img_3 ),
            IntroSlide(
                "Find the best hotels for your vacation",
                R.drawable.img_4 ),
        )
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        introSliderViewPager.adapter = introSliderAdapter
        setupIndicators()
        setCurrentIndicator(0)
        introSliderViewPager.registerOnPageChangeCallback(object :
        ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

        BtnNext.setOnClickListener{
            if (introSliderViewPager.currentItem + 1 < introSliderAdapter.itemCount){
                introSliderViewPager.currentItem += 1
            }else{
                Intent(applicationContext,HotelMainActivity::class.java).also {
                    startActivity(it)
                    finish()
                }

            }
        }

        BtnSkip.setOnClickListener{
            Intent(applicationContext,HotelMainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        }







      /*  btn = findViewById(R.id.BtnNext)
        imgview = findViewById(R.id.imageView);
        imgview.setImageResource(R.drawable.img_2);

        btn.setOnClickListener{
            startActivity(
                Intent(this, MainActivity2::class.java))

        }

      */


    }

    private fun setupIndicators(){
        val indicators = arrayOfNulls<ImageView>(introSliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(WRAP_CONTENT,WRAP_CONTENT)
        layoutParams.setMargins(8,0,8,0)
        for(i in indicators.indices){
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicatorsContainer.addView(indicators[i])
        }
    }

    private fun setCurrentIndicator(index:Int){
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount){
            val imageView = indicatorsContainer.get(i) as ImageView
            if (i == index ){
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            }else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
            }
        }
    }


}