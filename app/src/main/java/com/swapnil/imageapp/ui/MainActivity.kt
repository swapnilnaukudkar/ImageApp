package com.swapnil.imageapp.ui

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.swapnil.imageapp.R
import com.swapnil.imageapp.databinding.ActivityMainBinding
import com.swapnil.imageapp.model.ImageData
import com.swapnil.imageapp.utils.PreferenceManager
import com.swapnil.imageapp.utils.utils
import com.swapnil.imageapp.viewmodel.MainActivityViewModel
import java.util.*

/**
 * [MainActivity]: Home Screen to get data from user and show Image of the day
 */
class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainActivityViewModel
    private var isFavorite = false;
    private lateinit var preferenceManager: PreferenceManager
    private var date: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferenceManager = PreferenceManager.getInstance(this)
        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
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
                if (date != null) {
                    preferenceManager.setAsFavorite(date!!)
                }
                binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
            } else {
                isFavorite = false
                binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_outline_favorite_border))
                if (date != null) {
                    preferenceManager.removeFromFavorite(date!!)
                }
            }
        }

        viewModel.imageOfTheDay.observe(this) {
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

    // update changes to date UI
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

    // Validate entered date and fetch data from viewModel
    private fun validateDate(dateData: String) {
        if (utils.validate(dateData)) {
            viewModel.getImageOfTheDay(utils.dateConverter(dateData))
        } else {
            showDateErrorToast()
        }
    }

    // Update changes to UI
    private fun updateUI(image: ImageData?) {
        date = image?.date
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
        if (image?.date != null) {
            image.isFavorite = preferenceManager.isFavorite(image.date)
        }
        if (image?.isFavorite == true) {
            isFavorite = true
            binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
        } else {
            isFavorite = false
            binding.content.fab.setImageDrawable(getDrawable(R.drawable.ic_outline_favorite_border))
        }
    }

    // View Model Factory
    var factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(
                application,
            ) as T
        }
    }
}