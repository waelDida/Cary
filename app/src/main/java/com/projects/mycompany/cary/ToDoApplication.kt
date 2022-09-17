package com.projects.mycompany.cary

import android.app.Application
import androidx.lifecycle.LifecycleObserver
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import timber.log.Timber


class ToDoApplication: Application(), LifecycleObserver {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }

    val giverDataRepository: GiverDataRepository
        get() = ServiceLocator.provideGiverDataRepo(this)

    val seekerDataRepository: SeekerDataRepository
        get() = ServiceLocator.provideSeekerRepository(this)

    val reviewsDataRepository: ReviewsDataRepository
        get() = ServiceLocator.provideReviewsRepository()

    val inboxDataRepository: InboxDataRepository
        get() = ServiceLocator.provideInboxRepository()
}