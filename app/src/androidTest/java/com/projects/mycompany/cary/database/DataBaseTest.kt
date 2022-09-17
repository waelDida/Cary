package com.projects.mycompany.cary.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.projects.mycompany.cary.getOrAwaitValueAndroid
import com.projects.mycompany.cary.room.SeekerDao
import com.projects.mycompany.cary.room.AppDataBase
import com.projects.mycompany.cary.room.GiverDao
import com.projects.mycompany.cary.test.currentGiver
import com.projects.mycompany.cary.test.currentSeeker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Rule
import org.junit.runner.RunWith
import java.io.IOException


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class DataBaseTest {

    private lateinit var db: AppDataBase
    private lateinit var seekerDao: SeekerDao
    private lateinit var giverDao: GiverDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun setDataBase(){
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context,AppDataBase::class.java)
            .allowMainThreadQueries()
            .build()
        seekerDao = db.seekerDao
        giverDao = db.giverDao
    }

    @After
    @Throws(IOException::class)
    fun closeDataBase(){
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCareSeeker() = runBlockingTest{
        seekerDao.insert(currentSeeker)
        val testSeeker = seekerDao.getSeeker("1")
        assertThat(testSeeker.getOrAwaitValueAndroid().email,`is`("wael@hotmail.fr"))
    }

    @Test
    @Throws(Exception::class)
    fun updateCareSeeker() = runBlockingTest{
        seekerDao.insert(currentSeeker)
        currentSeeker.apply {
            offerTitle = "title2"
            offerDescription = "description2"
        }
        seekerDao.update(currentSeeker)
        val testSeeker = seekerDao.getSeeker("1")
        assertThat(testSeeker.getOrAwaitValueAndroid().offerTitle, `is`("title2"))
        assertThat(testSeeker.getOrAwaitValueAndroid().offerDescription,`is`("description2"))
    }

    @Test
    @Throws(Exception::class)
    fun deleteCareSeeker() = runBlockingTest{
        seekerDao.insert(currentSeeker)
        seekerDao.delete("1")
        val careSeeker = seekerDao.getSeeker("1")
        assertNull(careSeeker.getOrAwaitValueAndroid())
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetCaregiver() = runBlockingTest{
        giverDao.insert(currentGiver)
        val careGiver = giverDao.getGiver("1")
        assertThat(careGiver.getOrAwaitValueAndroid().firstName, `is`("Wael"))
    }

    @Test
    @Throws(Exception::class)
    fun updateCareGiver() = runBlockingTest{
        giverDao.insert(currentGiver)
        currentGiver.apply {
            firstName = "Jhon"
            phone = "223334444"
        }
        giverDao.update(currentGiver)
        val careGiver = giverDao.getGiver("1")
        assertThat(careGiver.getOrAwaitValueAndroid().firstName,`is`("Jhon"))
        assertThat(careGiver.getOrAwaitValueAndroid().phone, `is`("223334444"))
    }
    @Test
    @Throws(Exception::class)
    fun deleteCareGiver()= runBlockingTest{
        giverDao.insert(currentGiver)
        giverDao.delete("1")
        val careGiver = giverDao.getGiver("1")
        assertNull(careGiver.getOrAwaitValueAndroid())
    }
}
