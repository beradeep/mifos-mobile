package org.mifos.mobile.core.model.entity.accounts.share

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class Status(
    @Expose
    var id: Int? = null,
    @Expose
    var code: String? = null,

    @Expose
    var value: String? = null,

    @Expose
    var submittedAndPendingApproval: Boolean? = null,

    @Expose
    var approved: Boolean? = null,

    @Expose
    var rejected: Boolean? = null,

    @Expose
    var active: Boolean? = null,

    @Expose
    var closed: Boolean? = null,

) : Parcelable
