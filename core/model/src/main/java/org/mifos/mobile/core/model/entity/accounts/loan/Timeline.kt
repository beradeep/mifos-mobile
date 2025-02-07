/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package org.mifos.mobile.core.model.entity.accounts.loan

import android.os.Parcel
import android.os.Parcelable

data class Timeline(
    var submittedOnDate: List<Int>? = null,

    var submittedByUsername: String?,

    var submittedByFirstname: String?,

    var submittedByLastname: String?,

    var approvedOnDate: List<Int>? = null,

    var approvedByUsername: String?,

    var approvedByFirstname: String?,

    var approvedByLastname: String?,

    var expectedDisbursementDate: List<Int>? = null,

    var actualDisbursementDate: List<Int>? = null,

    var disbursedByUsername: String?,

    var disbursedByFirstname: String?,

    var disbursedByLastname: String?,

    var closedOnDate: List<Int>? = null,

    var expectedMaturityDate: List<Int>? = null,

    var withdrawnOnDate: List<Int>,

) : Parcelable {
    constructor(parcel: Parcel) : this(
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
        arrayListOf<Int>().apply {
            parcel.readArrayList(Int::class.java.classLoader)
        },
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeList(submittedOnDate)
        parcel.writeString(submittedByUsername)
        parcel.writeString(submittedByFirstname)
        parcel.writeString(submittedByLastname)
        parcel.writeList(approvedOnDate)
        parcel.writeString(approvedByUsername)
        parcel.writeString(approvedByFirstname)
        parcel.writeString(approvedByLastname)
        parcel.writeList(expectedDisbursementDate)
        parcel.writeList(actualDisbursementDate)
        parcel.writeString(disbursedByUsername)
        parcel.writeString(disbursedByFirstname)
        parcel.writeString(disbursedByLastname)
        parcel.writeList(closedOnDate)
        parcel.writeList(expectedMaturityDate)
        parcel.writeList(withdrawnOnDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        @JvmField
        var CREATOR: Parcelable.Creator<Timeline> = object : Parcelable.Creator<Timeline> {
            override fun createFromParcel(parcel: Parcel): Timeline {
                return Timeline(parcel)
            }

            override fun newArray(size: Int): Array<Timeline?> {
                return arrayOfNulls(size)
            }
        }
    }
}
