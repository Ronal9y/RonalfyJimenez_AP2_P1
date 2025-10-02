package edu.ucne.ronalfyjimenez_ap2_p1.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.database.HuacalesDb
import edu.ucne.ronalfyjimenez_ap2_p1.data.local.dao.HuacalDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDb(@ApplicationContext ctx: Context): HuacalesDb =
        Room.databaseBuilder(ctx, HuacalesDb::class.java, "huacales.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideDao(db: HuacalesDb) = db.huacalDao()
}