package com.channelpartner.model.response

data class ProfileResponse(
    val address: String,
    val alternateContact: String,
    val channelPartnerId: Int,
    val cityName: String,
    val contactNo: String,
    val currentLevel: String,
    val description: String,
    val dob: String,
    val email: String,
    val firstName: String,
    val gender: String,
    val imageUrl: String,
    val introducerName: String,
    val lastName: String,
    val parentId: Int,
    val pincode: String,
    val referCode: String,
    val stateName: String,
    val teachingExperience: String
)