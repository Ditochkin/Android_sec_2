/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.inventory.ui.settings.Settings
import javax.crypto.KeyGenerator

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Item::class], version = 3, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var Instance: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, InventoryDatabase::class.java, "item_database")
                    /**
                     * Setting this option in your app's database builder means that Room
                     * permanently deletes all data from the tables in your database when it
                     * attempts to perform a migration with no defined migration path.
                     */
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }

    private fun createDB(context: Context): InventoryDatabase {
        val passphrase = getPassphrase() ?: initializePassphrase(context)
        val factory = InventoryDatabase(passphrase)
        return Room.databaseBuilder(
            context.applicationContext,
            InventoryDatabase::class.java,
            "stock.db"
        )
            .openHelperFactory(factory)
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun getPassphrase(): ByteArray? {
        val passphraseString = Settings.passphraseString
        return passphraseString?.toByteArray(Charsets.ISO_8859_1)
    }

    private fun generatePassphrase(): ByteArray {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256)
        return keyGenerator.generateKey().encoded
    }

    private fun initializePassphrase(context: Context): ByteArray {
        val passphrase = generatePassphrase()
        Settings.passphraseString = passphrase.toString(Charsets.ISO_8859_1)

        return passphrase
    }
}
