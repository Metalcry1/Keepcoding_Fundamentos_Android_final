package com.example.practica_fundamentos_android_v2.UI.Heroes.HeroesFragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.practica_fundamentos_android_v2.Data.Database.PreferenceAplication.Companion.prefRepository
import com.example.practica_fundamentos_android_v2.UI.Heroes.HeroesActivity
import com.example.practica_fundamentos_android_v2.Data.Models.Hero
import com.example.practica_fundamentos_android_v2.R
import com.example.practica_fundamentos_android_v2.databinding.FragmentHeroesDetailHeroBinding
import kotlin.random.Random

class HeroesDetailHeroFragment(private val hero: Hero) : Fragment() {

    private lateinit var binding: FragmentHeroesDetailHeroBinding
    private val maxLife: Int = hero.maxLife
    var currentLife: Int = hero.currentLife

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHeroesDetailHeroBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btDamage.isClickable = true
        binding.btHealth.isClickable = true

        binding.tvText.text = hero.name
        Glide.with(binding.root)
            .load(hero.photo)
            .centerInside()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(binding.iwHero)

        binding.tvCurrentLife.text = "$currentLife"
        binding.pbHealh.progress = currentLife

        binding.btDamage.setOnClickListener {
            damageHero(hero)
            binding.pbHealh.max = maxLife
            binding.pbHealh.progress = currentLife
            binding.tvCurrentLife.text = currentLife.toString()
        }
        binding.btHealth.setOnClickListener {
            healthHero()
            binding.tvCurrentLife.text = currentLife.toString()
            binding.pbHealh.max = maxLife
            binding.pbHealh.progress = currentLife
            binding.tvCurrentLife.text = currentLife.toString()
        }
    }

    private fun damageHero(hero: Hero): Int {
        var damage: Int = Random.nextInt(10, 60)

        if (currentLife > 0) {
            when {
                damage <= currentLife -> {
                    Toast.makeText(
                        binding.root.context,
                        "Has recibido un daño de: $damage puntos de vida",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentLife = currentLife - damage
                    binding.btHealth.isClickable = true
                }

                damage > currentLife -> {

                    Toast.makeText(
                        binding.root.context,
                        "Has recibido un daño mortal de: $damage puntos de vida, estas muerto",
                        Toast.LENGTH_SHORT
                    ).show()
                    currentLife = 0
                    binding.btDamage.isClickable = false
                    prefRepository.saveHeroAlivePreferences(hero,false)
                    goToHeroes()
                }
            }
        }

        return currentLife

    }

    fun goToHeroes(){
        //Thread.sleep(5000)
        val intent = Intent(binding.root.context, HeroesActivity::class.java)
        startActivity(intent)
    }
    private fun healthHero(): Int {

        var health: Int = 20

        if (currentLife > 0 && currentLife <= 100) {

            when  {

                currentLife == 100 -> {
                    Toast.makeText(
                        binding.root.context,
                        "Estas a maximo de curacion $currentLife puntos de vida, no te puedes curar mas",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                health <= currentLife -> {

                    if ((currentLife + health) > 100){
                        currentLife = 100
                        Toast.makeText(
                            binding.root.context,
                            " $currentLife puntos de vida, te has curado al maximo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            binding.root.context,
                            " Te has curado $health puntos de vida",
                            Toast.LENGTH_SHORT
                        ).show()
                        currentLife += health

                    }
                }
            }

        }
        return currentLife
    }

}