package org.mifos.mobile.core.model.entity.accounts.savings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Created by saksham on 01/July/2018
 */

@Parcelize
data class SavingsAccountApplicationPayload(

    var submittedOnDate: String? = null,

    var clientId: Int? = null,

    var productId: Int? = null,

    var locale: String = "en",

    var dateFormat: String = "dd MMMM yyyy",

) : Parcelable
