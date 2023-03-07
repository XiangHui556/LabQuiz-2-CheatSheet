package edu.singaporetech.madata

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.IOException

/**
 * This data class holds the preference settings that are saved in the DataStore. It is exposed
 * via the Flow interface.
 */
//TODO add at least one constructor parameter and remove this comment block
data class UserPreferences(
        val gridView: Boolean,
)

/**
 * Class that handles saving and retrieving user preferences, utilizing Preferences DataStore. This
 * class may be utilized in either the ViewModel or an Activity, depending on what preferences are
 * being saved.
 */
class UserPreferencesRepository(context: Context) {
    // TODO Create the Preferences DataStore
    private val dataStore = context.dataStore
    // TODO Create a private object for defining PreferencesKeys
    private object PreferencesKeys {
        val grid_View = booleanPreferencesKey("gridView")
    }
    // TODO Add code to get the user preferences flow
    @SuppressLint("LongLogTag")
    fun userPreferencesFlow(): Flow<Boolean>{
        return dataStore.data
                .catch { exception ->
                    if (exception is IOException) {
                        Log.e(TAG, "Error reading preferences.", exception)
                        emit(emptyPreferences())
                    } else {
                        throw exception
                    }
                }
                .map { preferences ->
                    // Get the user preferences from the Preferences object
                    val updatingView = preferences[PreferencesKeys.grid_View] ?: false
                    updatingView
                }
    }
    // TODO link this class somehow with the rest of your classes
    suspend fun fetchInitialPreferences() =
        mapUserPreferences(dataStore.data.first().toPreferences())
    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val updatingView = preferences[PreferencesKeys.grid_View] ?: false
        return UserPreferences(updatingView)
    }
    // TODO Add public 'update' function(s)
    //      To be called (from an Activity) whenever a preference setting is changed.
    //      This function should call DataStore.edit() to persist the new setting.
    suspend fun updateView(updatingView: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.grid_View] = updatingView
        }
    }

    companion object {
        // Constant for naming our DataStore - you can change this if you want
        private const val USER_PREFERENCES_NAME = "user_preferences"
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

        // The usual for debugging
        private val TAG: String = "UserPreferencesRepository"

        // Boilerplate-y code for singleton: the private reference to this self
        @Volatile
        private var INSTANCE: UserPreferencesRepository? = null

        /**
         * Boilerplate-y code for singleton: to ensure only a single copy is ever present
         * @param context to init the datastore
         */
        fun getInstance(context: Context): UserPreferencesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE?.let {
                    return it
                }

                val instance = UserPreferencesRepository(context)
                INSTANCE = instance
                instance
            }
        }
    }
}