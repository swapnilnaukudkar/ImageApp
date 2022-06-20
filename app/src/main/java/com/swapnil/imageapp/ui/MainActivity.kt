package com.swapnil.imageapp.ui

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.swapnil.imageapp.R
import com.swapnil.imageapp.databinding.ActivityMainBinding
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.viewmodel.MainActivityViewModel
import com.swapnil.imageapp.utils.utils

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    private var isFavorite = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        setSupportActionBar(binding.toolbar)

        viewModel.showProgressBar.observe(this) {
            if (it) {
                binding.content.progressBar.visibility = VISIBLE
                binding.content.progressBar.bringToFront()
            } else {
                binding.content.progressBar.visibility = GONE
            }

        }

        binding.content.fab.setOnClickListener { view ->
            if (!isFavorite) {
                isFavorite = true
                binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
            } else {
                isFavorite = false
                binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_outline_favorite_border))
            }
        }

        viewModel.imageOfTheDay.observe(this) {
            Log.e(TAG, "onCreate: " + it)
            updateUI(it)
        }
        binding.content.tvDd.doAfterTextChanged {
            updateDateUI(it)
        }

        binding.content.btnSearch.setOnClickListener {

            if (binding.content.tvDd.text.isEmpty()) {
                showDateErrorToast()
            } else {
                validateDate(binding.content.tvDd.text.toString())
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun showDateErrorToast() {
        Toast.makeText(this, "Please enter valid date !!!", Toast.LENGTH_SHORT).show()
    }

    private fun updateDateUI(it: Editable?) {
        if (it?.length == 2) {

            if (it.toString().toInt() > 31) {
                showDateErrorToast()
            } else {
                binding.content.tvDd.text = it.append("-")
                binding.content.tvDd.setSelection(it.length)
            }
        } else if (it?.length == 5) {
            if (it.toString().split("-")[1].toInt() > 12) {
                showDateErrorToast()
            } else {
                binding.content.tvDd.text = it.append("-")
                binding.content.tvDd.setSelection(it.length)
            }
        }
    }

    private fun validateDate(dateData: String) {
        if (utils.validate(dateData)) {
            viewModel.getImageOfTheDay(utils.dateConverter(dateData))
        } else {
            showDateErrorToast()
        }
    }

    private fun updateUI(image: ImageData?) {
        Glide.with(this)
            .load(GlideUrl(image?.url))
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .transition(DrawableTransitionOptions.withCrossFade())
            .fitCenter()
            .into(binding.content.ivPotd);

        binding.content.tvTitle.text = image?.title
        binding.content.tvDescription.text = image?.explanation
        binding.content.tvDate.text = "Image of the Day : " + image?.date
        binding.content.fab.visibility = VISIBLE
        if (image?.isFavorite == true) {
            isFavorite = true
            binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
        } else {
            isFavorite = false
            binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_outline_favorite_border))
        }
    }
}