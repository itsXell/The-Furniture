package com.example.furnitureapp.models

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.GeoPoint
import java.util.*

data class StoreViewModel (
    var Id: String? = null,
    var Name: String? = null,
    var Address: String? = null,
    var Location: GeoPoint? = null,
    var UpdatedAt: Date? = null,
    var UpdatedBy: DocumentReference? = null,
    var CreatedAt: Date? = null,
    var CreatedBy: DocumentReference? = null,
    var IsActive: Boolean = true
)