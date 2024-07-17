package rocks.poopjournal.fucksgivenwatch.di

import android.content.Context
import androidx.room.Room
import rocks.poopjournal.fucksgivenwatch.data.FuckDao
import rocks.poopjournal.fucksgivenwatch.data.FuckDatabase
import rocks.poopjournal.fucksgivenwatch.utils.THEDATABASE_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object FuckAppModule {
    @Singleton
    @Provides
    fun provideFuckDao(fuckDatabase: FuckDatabase): FuckDao = fuckDatabase.fuckDao()

    @Singleton
    @Provides
    fun provideFuckDatabase(@ApplicationContext context: Context): FuckDatabase =
        Room.databaseBuilder(
            context,
            FuckDatabase::class.java,
            THEDATABASE_DATABASE_NAME
        ).build()

}