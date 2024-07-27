package rocks.poopjournal.fucksgiven.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rocks.poopjournal.fucksgiven.data.DatabaseBackupManager
import rocks.poopjournal.fucksgiven.data.FuckDao
import rocks.poopjournal.fucksgiven.data.FuckDatabase
import rocks.poopjournal.fucksgiven.data.ThemeSettingImpl
import rocks.poopjournal.fucksgiven.presentation.ui.utils.THEDATABASE_DATABASE_NAME
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FuckAppModule {
    @Singleton
    @Provides
    fun provideFuckDao(fuckDatabase: FuckDatabase ): FuckDao = fuckDatabase.fuckDao()

    @Singleton
    @Provides
    fun provideFuckDatabase(@ApplicationContext context: Context): FuckDatabase =
        Room.databaseBuilder(
            context,
            FuckDatabase::class.java,
            THEDATABASE_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideDatabaseBackupManager(
        @ApplicationContext context: Context,
        fuckDatabase: FuckDatabase
    ): DatabaseBackupManager = DatabaseBackupManager(context, fuckDatabase)

}