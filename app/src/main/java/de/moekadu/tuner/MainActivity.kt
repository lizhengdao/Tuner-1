/*
 * Copyright 2020 Michael Moessner
 *
 * This file is part of Tuner.
 *
 * Tuner is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Tuner is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Tuner.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.moekadu.tuner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.FragmentManager
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)

      val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
      val nightMode = when(sharedPreferences.getString("appearance", "auto")) {
        "dark" -> AppCompatDelegate.MODE_NIGHT_YES
        "light" -> AppCompatDelegate.MODE_NIGHT_NO
        else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
      }
      AppCompatDelegate.setDefaultNightMode(nightMode)

      setContentView(R.layout.activity_main)
      setSupportActionBar(findViewById(R.id.toolbar))

      if(savedInstanceState == null)
        supportFragmentManager.beginTransaction().replace(R.id.main_content, TunerFragment()).commit()

      setDisplayHomeButton()
      supportFragmentManager.addOnBackStackChangedListener { setDisplayHomeButton() }

    }

  override fun onSupportNavigateUp(): Boolean {
    supportFragmentManager.popBackStack()
    return super.onSupportNavigateUp()
  }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            // User chose the "Settings" item, show the app settings UI...
          supportFragmentManager.beginTransaction()
            .replace(R.id.main_content, SettingsFragment())
                    .addToBackStack("blub")
                    .commit();
            true
        }
        else -> {
            // If we got here, the user's action was not recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

      private fun setDisplayHomeButton() {
        val showDisplayHomeButton = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(showDisplayHomeButton)
    }

}
