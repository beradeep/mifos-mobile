package org.mifos.mobile.core.model.entity.templates.loans

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Rajan Maurya on 16/07/16.
 */

@Parcelize
data class AllowAttributeOverrides(

    var amortizationType: Boolean? = null,

    var interestType: Boolean? = null,

    var transactionProcessingStrategyId: Boolean? = null,

    var interestCalculationPeriodType: Boolean? = null,

    var inArrearsTolerance: Boolean? = null,

    var repaymentEvery: Boolean? = null,

    var graceOnPrincipalAndInterestPayment: Boolean? = null,

    var graceOnArrearsAgeing: Boolean? = null,

) : Parcelable
