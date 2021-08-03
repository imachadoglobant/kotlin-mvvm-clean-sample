package com.globant.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.globant.domain.entities.MarvelCharacter
import com.globant.utils.Data
import com.globant.utils.Status
import com.globant.viewmodels.CharacterViewModel
import com.globant.myapplication.R
import com.globant.myapplication.databinding.ActivityMainBinding
import com.globant.utils.Event
import com.globant.utils.MINUS_ONE
import com.globant.viewmodels.AppViewModelProvider

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy {
        AppViewModelProvider(this).get(CharacterViewModel::class.java)
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)

        binding.buttonSearchRemote.setOnClickListener { onSearchRemoteClicked() }
        binding.buttonSearchLocal.setOnClickListener { onSearchLocalClicked() }
    }

    private fun updateUI(characterData: Event<Data<MarvelCharacter>>) {
        // in this case, we need to use peekContent because we use this several times to update the UI
        // in case that we will only use the characterData one time we have to use getContentIfNotHandled
        when (characterData.peekContent().responseType) {
            Status.ERROR -> {
                hideProgress()
                characterData.peekContent().error?.message?.let { showMessage(it) }
                binding.textViewDetails.text = getString(R.string.no_character)
            }
            Status.LOADING -> {
                showProgress()
            }
            Status.SUCCESSFUL -> {
                hideProgress()
                characterData.peekContent().data?.let { setCharacter(it) }
            }
        }
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
        binding.textViewDetails.visibility = View.INVISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.INVISIBLE
        binding.textViewDetails.visibility = View.VISIBLE
    }

    private fun setCharacter(character: MarvelCharacter) {
        binding.textViewDetails.text = character.description
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun onSearchRemoteClicked() {
        val id = if (binding.characterID.text.toString().isNotEmpty()) {
            binding.characterID.text.toString().toInt()
        } else {
            MINUS_ONE
        }
        viewModel.onSearchRemoteClicked(id)
    }
    private fun onSearchLocalClicked() {
        val id = if (binding.characterID.text.toString().isNotEmpty()) {
            binding.characterID.text.toString().toInt()
        } else {
            MINUS_ONE
        }
        viewModel.onSearchLocalClicked(id)
    }
}
