package com.projects.mycompany.cary.utils.filters.givers

import com.projects.mycompany.cary.models.GiverFilter
import com.projects.mycompany.cary.test.*
import com.projects.mycompany.cary.utils.*
import org.hamcrest.core.IsEqual
import org.junit.Assert.*
import org.junit.Test

class CareGiversFilterUtilsKtTest{

    private val list = mutableListOf(
        giver1,
        giver2,
        giver3,
        giver4,
        giver5,
        giver6
    )

    private val filter1 = GiverFilter(
        MALE, YRS_EXP_3,40,27
        ,32, FULL_TIME, currentSeeker.latitude, currentSeeker.longitude, currentSeeker.careCategory)

    private val filter2 = GiverFilter(
        MALE, YRS_EXP_3,40,25
        ,50, FULL_TIME, currentSeeker.latitude, currentSeeker.longitude, currentSeeker.careCategory)

    private val filter3 = GiverFilter(
        FEMALE, YRS_EXP_4,300,20
        ,50, PART_TIME, currentSeeker.latitude, currentSeeker.longitude, currentSeeker.careCategory)

    private val filter4 = GiverFilter(
        FEMALE, YRS_EXP_4,300,24
        ,40, PART_TIME, currentSeeker.latitude, currentSeeker.longitude, currentSeeker.careCategory)

    private val filter5 = GiverFilter(
        FEMALE, YRS_EXP_4,70,25
        ,50, PART_TIME, currentSeeker.latitude, currentSeeker.longitude, currentSeeker.careCategory)

    private val filter6 = GiverFilter(
        FEMALE, YRS_EXP_4,300,50
        ,60, PART_TIME, currentSeeker.latitude, currentSeeker.longitude, PET_CARE
    )

    @Test
    fun testFilter1(){
        val x = filterCareGivers(list,filter1)
        assertThat(x, IsEqual(mutableListOf(
            giver1,
            giver2
        )))
    }

}