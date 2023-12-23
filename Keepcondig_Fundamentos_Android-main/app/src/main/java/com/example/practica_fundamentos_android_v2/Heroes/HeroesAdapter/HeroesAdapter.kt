package com.example.practica_fundamentos_android_v2.Heroes.HeroesAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.practica_fundamentos_android_v2.Heroes.HeroesActivitytInterface
import com.example.practica_fundamentos_android_v2.Models.Hero
import com.example.practica_fundamentos_android_v2.R
import com.example.practica_fundamentos_android_v2.databinding.CellHeroesBinding

class HeroesAdapter(val heroesActivitytInterface: HeroesActivitytInterface) : RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    private var heroList: List<Hero> = emptyList()

    class HeroesViewHolder(private val binding: CellHeroesBinding, val heroesActivitytInterface: HeroesActivitytInterface):RecyclerView.ViewHolder(binding.root){

        fun overlayDead(overlay: Boolean) {
            binding.overlayView.isVisible = overlay
        }

        fun showPosition(position: Int){
            //binding.tvPosition.text = position.toString()
        }
        fun showHero(hero: Hero){
            binding.tvNameHero.text = hero.name
            Glide.
            with(binding.root)
                .load(hero.photo)
                .centerInside()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.tvImagenHero)

            binding.root.setOnClickListener {
                heroesActivitytInterface.showFragment(hero)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        return HeroesViewHolder(
            CellHeroesBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            heroesActivitytInterface = heroesActivitytInterface

        )
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        var hero = heroList[position]
        var heroName = hero.name
        var isAliveHero =  heroesActivitytInterface.loadHero(hero)

        if (isAliveHero) {
            Log.w("HEROE", "$heroName esta $isAliveHero vivo")
            holder.showHero(heroList[position])
            holder.showPosition(position)
            holder.overlayDead(false)
        }else{
            holder.showHero(heroList[position])
            holder.showPosition(position)
            Log.w("HEROE", "$heroName esta $isAliveHero muerto")
            holder.overlayDead(true)

        }
    }

    override fun getItemCount(): Int {
        return heroList.count()
    }

    fun updateListHeroes(heroList: List<Hero>){
        this.heroList = heroList
        notifyDataSetChanged()
    }

}