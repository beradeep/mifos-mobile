package org.mifos.mobile.core.model.entity.accounts.savings

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
 * Created by saksham on 02/July/2018
 */

@Parcelize
data class SavingsAccountWithdrawPayload(
    var locale: String = "en",
    var dateFormat: String = "dd MMMM yyyy",
    var withdrawnOnDate: String? = null,
    var note: String? = null,
) : Parcelable
