package com.example.practica_fundamentos_android_v2.Heroes

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practica_fundamentos_android_v2.databinding.ActivityHeroesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica_fundamentos_android_v2.Data.Local.PreferenceAplication
import com.example.practica_fundamentos_android_v2.Data.Local.PreferenceAplication.Companion.prefRepository
import com.example.practica_fundamentos_android_v2.Heroes.HeroesAdapter.HeroesAdapter
import com.example.practica_fundamentos_android_v2.Heroes.HeroesFragment.HeroesDetailHeroFragment
import com.example.practica_fundamentos_android_v2.Models.Hero

interface HeroesActivitytInterface{
    fun showFragment(hero: Hero)
}

class HeroesActivity: AppCompatActivity(), HeroesActivitytInterface {

    private val viewModel : HeroesActivityViewModel by viewModels()
    private lateinit var binding: ActivityHeroesBinding
    private val heroesAdapter = HeroesAdapter(this)
    private var heroList: List<Hero> = emptyList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.lauchGetHeroes()
        setObservers()
        configurationRecyclerView()
        listeners()
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HeroesActivityViewModel.State.Idle -> idle()
                    is HeroesActivityViewModel.State.Error -> showError(state.message)
                    is HeroesActivityViewModel.State.Loading -> showLoading()
                    is HeroesActivityViewModel.State.SucessGetHeroes -> showSuccessGetHeroes(state.heroList)
                }
            }
        }
    }

    fun listeners(){
        binding.btResurrect.setOnClickListener {
            resurrectAllHeros()
        }
    }
    override fun showFragment(hero: Hero) {

        var alive = prefRepository.loadHeroAlivePreferences(hero)
        if (alive){
            Log.w("HEROE SHOW VIVO", alive.toString())
            binding.fFragment.visibility = View.VISIBLE
            supportFragmentManager
                .beginTransaction()
                //.add(binding.fFragment.id, HeroesDetailHeroFragment(hero))
                .replace(binding.fFragment.id, HeroesDetailHeroFragment(hero))
                .addToBackStack(null)
                .commit()
        }else{
            Log.w("HEROE SHOW MUERTO", alive.toString())
        }
    }


    fun showSuccessGetHeroes(heroList: List<Hero>) {
        this.heroList = heroList
        fillList()
        binding.layoutLoadingInclude.root.visibility = View.GONE
    }

    private fun showLoading() {
        binding.layoutLoadingInclude.root.visibility = View.VISIBLE
    }

    private fun showError(message: String) {

        Toast.makeText(binding.root.context, message, Toast.LENGTH_SHORT).show()
        Log.w("HEROES ERROR", message)
    }

    private fun idle() {
        Log.w("HEROES ACTIVITY", "ESPERANDO INSTRUCCIONES")
    }

    fun configurationRecyclerView(){
        binding.rvHeroes.layoutManager = LinearLayoutManager(this)
        binding.rvHeroes.adapter = heroesAdapter
    }
    fun fillList(){
        heroesAdapter.updateListHeroes(heroList)
    }

    fun resurrectAllHeros(){
        for (hero in heroList){
            prefRepository.saveHeroAlivePreferences(hero,true)
        }
        Toast.makeText(this, "Has resucitado a todos los Heroes", Toast.LENGTH_SHORT).show()
        heroesAdapter.updateListHeroes(heroList)

    }






}