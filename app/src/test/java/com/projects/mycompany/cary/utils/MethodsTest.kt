package com.projects.mycompany.cary.utils

import com.google.firebase.Timestamp
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.core.IsEqual
import org.junit.Test



class MethodsTest {

    @Test
    fun calculateAgeTest() {
        val str = "12/18/1985"
        val age = calculateAge(getDateFromString(str))
        assertThat(age,`is`(34))
    }

    @Test
    fun distance(){
        val distance = calculateDistance(34.9399,8.5666,34.4311,8.7757)
        assertThat(distance.toInt(),IsEqual(59))
    }

    @ExperimentalStdlibApi
    @Test
    fun formatNameTest(){
        val firstName = "wael"
        val lastName = "saadaoui"
        val nameFormatted = formatName(firstName,lastName)
        assertThat(nameFormatted,IsEqual("Wael.S"))
    }

    @Test
    fun formatJobTypeTest(){
        val job = PART_TIME
        val result = formatJobType(job)
        assertThat(result,IsEqual("Part Time"))
    }

    @Test
    fun timeAgoTest(){
        val date = Timestamp.now().toDate()
        val result = timeAgo(date)
        assertThat(result,IsEqual("just now"))
    }

    @Test
    fun passwordTest(){
        val password = "jhio8569"
        val check = isPasswordValid(password)
        assertThat(check, `is`(true))
    }
}