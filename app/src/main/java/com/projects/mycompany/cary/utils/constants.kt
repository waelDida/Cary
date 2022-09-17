package com.projects.mycompany.cary.utils

const val PRIVACY_POLICY_LINK = "PASTE_YOUR_POLICY_LINK_HERE"
const val TERMS_LINK = "PASTE_YOUR_TERMS_LINK_HERE"

const val isCareGiver = "is_caregiver"
const val isCareSeeker = "is_seeker"

const val MY_PERMISSIONS_REQUEST_LOCATION = 222
const val MY_PERMISSION_REQUEST_GALLERY = 333
const val RC_CHOOSE_PHOTO = 1
const val REQUEST_CHECK_SETTINGS = 20

const val LOGIN_REQUEST_CODE = 111

const val DELETE_ACCOUNT_TAG = "delete_account_tag"
const val DELETE_ACCOUNT_VAL = 200

const val CARE_SEEKER_TAG = "care_seeker_tag"
const val CARE_GIVER_TAG = "care_giver_tag"
const val EMAIL_TAG = "email_tag"
const val PASSWORD_TAG = "password_tag"

const val EMPTY_EMAIL_PASSWORD = "empty_email_password"
const val INVALID_EMAIL = "invalid email"
const val VALID_EMAIL_PASSWORD = "valid_email_password"

const val MALE = "male"
const val FEMALE = "female"
const val YES = "yes"
const val NO = "no"
const val BOTH_GENDERS = "bothGenders"
const val FULL_TIME = "fullTime"
const val PART_TIME = "partTime"
const val BOTH_JOB_TYPES = "bothJobTypes"

const val FILL_ALL_FIELDS = "fill_all_fields"
const val CHECK_PASSWORD = "check_password"
const val INVALID_PASSWORD = "invalid_password"
const val VALID_INFO = "valid info"
const val MISSING_INFO = "missing info"
const val INVALID_AGE = "invalid age"
const val UNDER_18 ="You are under 18"
const val INVALID_PRICE = "Invalid Price"
const val MISSING_GENDER = "Missing gender"
const val MISSING_JOB_TYPE = "Missing job type"
const val INVALID_AGE_RANGE ="invalid age range"
const val MISSING_PRICES = "missing_prices"
const val MISSING_AVAILABILITY = "missing_availability"
const val MISSING_FIRST_NAME = "missing_first_name"
const val MISSING_LAST_NAME = "missing_last_name"
const val MISSING_ADDRESS = "missing_address"
const val MISSING_PHONE_NUMBER = "missing_phone_number"
const val MISSING_OFFER_TITLE = "missing_offer_title"
const val MISSING_OFFER_DESCRIPTION = "missing offer description"

const val SIGN_IN_RESULT = "sign_in_result"
const val SUCCESS = "success"
const val FAIL = "fail"
const val EMAIL_VERIFICATION_FAIL = "email_verification_failed"
const val INDICE_AFTER_CREATION = "indice_after_creation"


// Experience values
const val YRS_EXP_ALL = "All"
const val YRS_EXP_0 ="0 yrs exp"
const val YRS_EXP_1 ="1 yrs exp"
const val YRS_EXP_2 ="2 yrs exp"
const val YRS_EXP_3 ="3 yrs exp"
const val YRS_EXP_4 ="4 yrs exp"
const val YRS_EXP_5 ="+5 yrs exp"
const val YRS_EXP_10 ="+10 yrs exp"

// Care categories
const val CHILD_CARE = "Child Care"
const val PET_CARE = "Pet Care"
const val HOUSE_KEEPING = "House Keeping"
const val SENIOR_CARE = "Senior Care"
const val TUTOR = "Tutor"
const val SPECIAL_NEEDS = "Special needs"

// Firebase Collections names
const val GIVERS = "givers"
const val SEEKERS = "seekers"
const val REVIEWS = "reviews"
const val MESSAGES = "messages"
const val CHATS = "chats"

val experience = arrayOf(
    YRS_EXP_0, YRS_EXP_1,
    YRS_EXP_2, YRS_EXP_3, YRS_EXP_4, YRS_EXP_5, YRS_EXP_10
)

val experienceForFilter = arrayOf(
    YRS_EXP_ALL,YRS_EXP_0, YRS_EXP_1,
    YRS_EXP_2, YRS_EXP_3, YRS_EXP_4, YRS_EXP_5, YRS_EXP_10)

val ages = arrayListOf(18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,
    41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70)

val categoriesList = arrayListOf(CHILD_CARE, PET_CARE, HOUSE_KEEPING, SENIOR_CARE, SPECIAL_NEEDS, TUTOR)

val rateList = arrayListOf(1,2,3,4,5)



const val PREF_GENDER_MALE = "gender_pref_male"
const val PREF_GENDER_FEMALE = "gender_pref_female"
const val PREF_EXPERIENCE = "experience_pref"
const val PREF_DISTANCE = "max_distance_pref"
const val PREF_MIN_AGE = "min_age_pref"
const val PREF_MAX_AGE = "max_age_pref"
const val PREF_JOB_FULL = "job_type_pref_full"
const val PREF_JOB_PART = "job_type_pref_part"

const val PREF_EMAIL_VERIFIED= "pref_email_verified"
const val PREF_USER_TYPE = "pref_user_type"

const val SECOND_MILLIS = 1000
const val MINUTE_MILLIS = 60 * SECOND_MILLIS
const val HOUR_MILLIS = 60 * MINUTE_MILLIS
const val DAY_MILLIS = 24 * HOUR_MILLIS
