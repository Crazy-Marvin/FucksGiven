package rocks.poopjournal.fucksgiven.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import rocks.poopjournal.fucksgiven.data.ThemeSettingImpl
import rocks.poopjournal.fucksgiven.presentation.ui.utils.ThemeSetting
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingModule {
    @Binds
    @Singleton
    abstract fun bindThemeSetting(
        themeSettingImpl: ThemeSettingImpl
    ) : ThemeSetting
}