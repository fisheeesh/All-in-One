package com.swamyiphyo.allinone

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView
import com.swamyiphyo.allinone.Fragments.AboutFragment
import com.swamyiphyo.allinone.Fragments.HomeFragment
import com.swamyiphyo.allinone.Fragments.ProfileFragment
import com.swamyiphyo.allinone.Fragments.ReelsFragment
import com.swamyiphyo.allinone.Fragments.SearchFragment
import com.swamyiphyo.allinone.Fragments.SettingsFragment
import com.swamyiphyo.allinone.Fragments.ShareFragment
import com.swamyiphyo.allinone.Fragments.SubscriptionFragment
import com.swamyiphyo.allinone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding : ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigationView()

        bottomNavigation()

        if(savedInstanceState == null){
            replaceFragment(HomeFragment())
            binding.navMenu.setCheckedItem(R.id.nav_home)
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(binding.frameLayout.id, fragment).commit()
    }

    private fun navigationView(){
        drawerLayout = binding.drawerLayout

        setSupportActionBar(binding.toolBar)

        binding.navMenu.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, binding.toolBar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun bottomNavigation(){
        binding.btnNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.btn_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.btn_search -> {
                    replaceFragment(SearchFragment())
                    true
                }
                R.id.btn_add ->{
                    showBottomDialog()
                    true
                }
                R.id.btn_reels ->{
                    replaceFragment(ReelsFragment())
                    true
                }
                R.id.btn_profile ->{
                    replaceFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(R.layout.bottom_sheet)
            show()
        }
        val videoLayout = dialog.findViewById<LinearLayout>(R.id.vdo_layout)
        val shortsLayout = dialog.findViewById<LinearLayout>(R.id.short_layout)
        val liveLayout = dialog.findViewById<LinearLayout>(R.id.live_layout)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancel_button)

        videoLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Upload a Video is clicked", Toast.LENGTH_SHORT).show()
        }

        shortsLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Create a short is Clicked", Toast.LENGTH_SHORT).show()
        }

        liveLayout.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this@MainActivity, "Go live is Clicked", Toast.LENGTH_SHORT).show()
        }

        cancelButton.setOnClickListener { dialog.dismiss() }

        dialog.window!!.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.windowAnimations = R.style.DialogAnimation
            setGravity(Gravity.BOTTOM)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_sub -> replaceFragment(SubscriptionFragment())
            R.id.nav_share -> replaceFragment(ShareFragment())
            R.id.nav_about -> replaceFragment(AboutFragment())
            R.id.nav_settings -> replaceFragment(SettingsFragment())
            R.id.log_out -> Toast.makeText(this, "Log Out", Toast.LENGTH_SHORT).show()
        }
        drawerLayout.closeDrawers()
        return true
    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
}