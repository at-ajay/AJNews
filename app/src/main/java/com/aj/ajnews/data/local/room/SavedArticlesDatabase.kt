package com.aj.ajnews.data.local.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.aj.ajnews.data.local.room.entities.SavedArticlesEntity

@Database(
    entities = [SavedArticlesEntity::class],
    version = 3,
    autoMigrations = [AutoMigration(from = 2, to = 3, spec = SavedArticlesDatabase.MigrationFromTwoToThree::class)]
)
abstract class SavedArticlesDatabase: RoomDatabase() {

    abstract val dao: SavedArticlesDao

    @DeleteTable(tableName = "OfflineArticlesEntity")
    class MigrationFromTwoToThree: AutoMigrationSpec

}