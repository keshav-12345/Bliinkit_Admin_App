package com.example.admin_blinkit_clone

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.admin_blinkit_clone.Adapter.AdapterProduct
import com.example.admin_blinkit_clone.Adapter.CategoriesAdapter
import com.example.admin_blinkit_clone.databinding.FragmentHomeBinding
import com.example.admin_blinkit_clone.model.Categories
import com.example.admin_blinkit_clone.viewmodels.AdminViewModel
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    val viewModel: AdminViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        setStatusBarColor()
        setCategories()
        getAllTheProducts("All")
        return binding.root
    }


    private fun getAllTheProducts(category: String) {
        lifecycleScope.launch {
            viewModel.fetchAllTheProducts(category).collect{
                if(it.isEmpty()){
                    binding.rvProducts.visibility = View.GONE
                    binding.tvText.visibility = View.VISIBLE
                }else{
                    binding.rvProducts.visibility = View.VISIBLE
                    binding.tvText.visibility = View.GONE

                }

                val adapterProduct = AdapterProduct()
                binding.rvProducts.adapter = adapterProduct
                adapterProduct.differ.submitList(it)
            }

        }
    }

    private fun setCategories() {
        val categoryList = ArrayList<Categories>()

        for(i in 0 until Constants.allProductCategoryIcon.size){
            categoryList.add(Categories(Constants.allProductCategory[i],Constants.allProductCategoryIcon[i]))
        }
        binding.rvCategories.adapter = CategoriesAdapter(categoryList, ::onCategoryClicked)
    }


    private fun onCategoryClicked(categories:Categories) {
        getAllTheProducts(categories.category)
    }



    private fun setStatusBarColor(){
        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(),R.color.yellow)
            statusBarColor = statusBarColors
            if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }
}