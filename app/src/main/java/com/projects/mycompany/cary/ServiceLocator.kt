package com.projects.mycompany.cary


import android.content.Context
import com.projects.mycompany.cary.data.sources.giversDataSource.GiverDataSourceImp
import com.projects.mycompany.cary.data.sources.reviewsDataSource.ReviewsDataSourceImp
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepositoryImp
import com.projects.mycompany.cary.data.repositories.giversDataRepository.GiverDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepository
import com.projects.mycompany.cary.data.repositories.inboxDataRepository.InboxDataRepositoryImp
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepository
import com.projects.mycompany.cary.data.repositories.reviewsDataRepository.ReviewsDataRepositoryImp
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepository
import com.projects.mycompany.cary.data.repositories.seekerDataRepository.SeekerDataRepositoryImp
import com.projects.mycompany.cary.data.sources.inboxDataSource.InboxDataSourceImp
import com.projects.mycompany.cary.data.sources.seekerDataSource.SeekerDataSourceImp


object ServiceLocator {

    @Volatile
    var giverDataRepository: GiverDataRepository? = null

    @Volatile
    var seekerDataRepository: SeekerDataRepository? = null

    @Volatile
    var reviewsDataRepository: ReviewsDataRepository? = null

    @Volatile
    var inboxDataRepository: InboxDataRepository? = null


    fun provideGiverDataRepo(context: Context): GiverDataRepository {
        synchronized(this) {
            return giverDataRepository ?: getGiverDataRepo(context)
        }
    }

    fun provideReviewsRepository(): ReviewsDataRepository {
        synchronized(this){
            return reviewsDataRepository ?: getReviewsRepo()
        }
    }

    fun provideSeekerRepository(context: Context): SeekerDataRepository {
        synchronized(this){
            return seekerDataRepository ?: getSeekerDataRepo(context)
        }
    }
    
    fun provideInboxRepository(): InboxDataRepository {
        synchronized(this){
            return inboxDataRepository ?: getInboxDataRepository()
        }
    }

    private fun getInboxDataRepository(): InboxDataRepositoryImp {
        return InboxDataRepositoryImp(InboxDataSourceImp())
    }


    private fun getGiverDataRepo(context: Context): GiverDataRepositoryImp {
        return GiverDataRepositoryImp(
            GiverDataSourceImp(context)
        )
    }

    private fun getSeekerDataRepo(context: Context): SeekerDataRepository {
        return SeekerDataRepositoryImp(SeekerDataSourceImp(context))
    }

    private fun getReviewsRepo(): ReviewsDataRepositoryImp {
        return ReviewsDataRepositoryImp(
            ReviewsDataSourceImp()
        )
    }
}