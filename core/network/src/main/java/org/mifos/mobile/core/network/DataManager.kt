package org.mifos.mobile.core.network

import io.reactivex.Observable
import okhttp3.ResponseBody
import org.mifos.mobile.core.datastore.DatabaseHelper
import org.mifos.mobile.core.datastore.PreferencesHelper
import org.mifos.mobile.core.datastore.model.Charge
import org.mifos.mobile.core.datastore.model.MifosNotification
import org.mifos.mobile.core.model.entity.*
import org.mifos.mobile.core.model.entity.accounts.loan.*
import org.mifos.mobile.core.model.entity.accounts.savings.*
import org.mifos.mobile.core.model.entity.beneficiary.*
import org.mifos.mobile.core.model.entity.client.*
import org.mifos.mobile.core.model.entity.guarantor.*
import org.mifos.mobile.core.model.entity.notification.*
import org.mifos.mobile.core.model.entity.payload.*
import org.mifos.mobile.core.model.entity.register.*
import org.mifos.mobile.core.model.entity.templates.account.AccountOptionsTemplate
import org.mifos.mobile.core.model.entity.templates.beneficiary.BeneficiaryTemplate
import org.mifos.mobile.core.model.entity.templates.loans.LoanTemplate
import org.mifos.mobile.core.model.entity.templates.savings.SavingsAccountTemplate
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @since 13/6/16.
 */
@Singleton
class DataManager @Inject constructor(
    val preferencesHelper: PreferencesHelper,
    private val baseApiManager: BaseApiManager,
    private val databaseHelper: DatabaseHelper,
) {
    var clientId: Long? = preferencesHelper.clientId

    suspend fun login(loginPayload: LoginPayload?): User {
        return baseApiManager.authenticationApi.authenticate(loginPayload)
    }

    suspend fun clients(): Page<Client> = baseApiManager.clientsApi.clients()

    suspend fun currentClient(): Client {
        return baseApiManager.clientsApi.getClientForId(clientId)
    }

    suspend fun clientImage(): ResponseBody {
        return baseApiManager.clientsApi.getClientImage(clientId)
    }

    suspend fun clientAccounts(): ClientAccounts {
        return baseApiManager.clientsApi.getClientAccounts(clientId)
    }

    suspend fun getAccounts(accountType: String?): ClientAccounts {
        return baseApiManager.clientsApi.getAccounts(clientId, accountType)
    }

    suspend fun getRecentTransactions(offset: Int, limit: Int): Page<Transaction> {
        return baseApiManager.recentTransactionsApi.getRecentTransactionsList(clientId, offset, limit)
    }

    suspend fun getClientCharges(clientId: Long): Page<Charge> {
        return baseApiManager.clientChargeApi.getClientChargeList(clientId).apply {
            databaseHelper.syncCharges(this)
        }
    }

    suspend fun getLoanCharges(loanId: Long): List<Charge> {
        return baseApiManager.clientChargeApi.getLoanAccountChargeList(loanId)
    }

    suspend fun getSavingsCharges(savingsId: Long): List<Charge> {
        return baseApiManager.clientChargeApi.getSavingsAccountChargeList(savingsId)
    }

    suspend fun getSavingsWithAssociations(accountId: Long?, associationType: String?): SavingsWithAssociations {
        return baseApiManager.savingAccountsListApi.getSavingsWithAssociations(accountId, associationType)
    }

    suspend fun accountTransferTemplate(accountId: Long?, accountType: Long?): AccountOptionsTemplate =
        baseApiManager.savingAccountsListApi.accountTransferTemplate(accountId, accountType)

    suspend fun makeTransfer(transferPayload: TransferPayload?): ResponseBody {
        return baseApiManager.savingAccountsListApi.makeTransfer(transferPayload)
    }

    suspend fun getSavingAccountApplicationTemplate(client: Long?): SavingsAccountTemplate {
        return baseApiManager.savingAccountsListApi.getSavingsAccountApplicationTemplate(client)
    }

    suspend fun submitSavingAccountApplication(payload: SavingsAccountApplicationPayload?): ResponseBody {
        return baseApiManager.savingAccountsListApi.submitSavingAccountApplication(payload)
    }

    suspend fun updateSavingsAccount(accountId: Long?, payload: SavingsAccountUpdatePayload?): ResponseBody {
        return baseApiManager.savingAccountsListApi.updateSavingsAccountUpdate(accountId, payload)
    }

    suspend fun submitWithdrawSavingsAccount(accountId: String?, payload: SavingsAccountWithdrawPayload?): ResponseBody {
        return baseApiManager.savingAccountsListApi.submitWithdrawSavingsAccount(accountId, payload)
    }

    fun getLoanAccountDetails(loanId: Long): Observable<LoanAccount?>? {
        return baseApiManager.loanAccountsListApi.getLoanAccountsDetail(loanId)
    }

    suspend fun getLoanWithAssociations(associationType: String?, loanId: Long?): LoanWithAssociations {
        return baseApiManager.loanAccountsListApi.getLoanWithAssociations(loanId, associationType)
    }

    suspend fun loanTemplate(): LoanTemplate = baseApiManager.loanAccountsListApi.getLoanTemplate(clientId)

    suspend fun getLoanTemplateByProduct(productId: Int?): LoanTemplate {
        return baseApiManager.loanAccountsListApi.getLoanTemplateByProduct(clientId, productId)
    }

    suspend fun createLoansAccount(loansPayload: LoansPayload?): ResponseBody {
        return baseApiManager.loanAccountsListApi.createLoansAccount(loansPayload)
    }

    suspend fun updateLoanAccount(loanId: Long, loansPayload: LoansPayload?): ResponseBody {
        return baseApiManager.loanAccountsListApi.updateLoanAccount(loanId, loansPayload)
    }

    suspend fun withdrawLoanAccount(loanId: Long?, loanWithdraw: LoanWithdraw?): ResponseBody {
        return baseApiManager.loanAccountsListApi.withdrawLoanAccount(loanId, loanWithdraw)
    }

    suspend fun beneficiaryList(): List<Beneficiary> = baseApiManager.beneficiaryApi.beneficiaryList()

    suspend fun beneficiaryTemplate(): BeneficiaryTemplate = baseApiManager.beneficiaryApi.beneficiaryTemplate()

    suspend fun createBeneficiary(beneficiaryPayload: BeneficiaryPayload?): ResponseBody {
        return baseApiManager.beneficiaryApi.createBeneficiary(beneficiaryPayload)
    }

    suspend fun updateBeneficiary(beneficiaryId: Long?, payload: BeneficiaryUpdatePayload?): ResponseBody {
        return baseApiManager.beneficiaryApi.updateBeneficiary(beneficiaryId, payload)
    }

    suspend fun deleteBeneficiary(beneficiaryId: Long?): ResponseBody {
        return baseApiManager.beneficiaryApi.deleteBeneficiary(beneficiaryId)
    }

    suspend fun thirdPartyTransferTemplate(): AccountOptionsTemplate =
        baseApiManager.thirdPartyTransferApi.accountTransferTemplate()

    suspend fun makeThirdPartyTransfer(transferPayload: TransferPayload?): ResponseBody {
        return baseApiManager.thirdPartyTransferApi.makeTransfer(transferPayload)
    }

    suspend fun registerUser(registerPayload: RegisterPayload?): ResponseBody {
        return baseApiManager.registrationApi.registerUser(registerPayload)
    }

    suspend fun verifyUser(userVerify: UserVerify?): ResponseBody {
        return baseApiManager.registrationApi.verifyUser(userVerify)
    }

    suspend fun clientLocalCharges(): Page<Charge?> = databaseHelper.clientCharges()

    fun notifications(): List<MifosNotification> = databaseHelper.notifications()

    fun unreadNotificationsCount(): Int {
        return databaseHelper.unreadNotificationsCount()
    }

    suspend fun registerNotification(payload: NotificationRegisterPayload?): ResponseBody {
        return baseApiManager.notificationApi.registerNotification(payload)
    }

    suspend fun updateRegisterNotification(id: Long, payload: NotificationRegisterPayload?): ResponseBody {
        return baseApiManager.notificationApi.updateRegisterNotification(id, payload)
    }

    suspend fun getUserNotificationId(id: Long): NotificationUserDetail {
        return baseApiManager.notificationApi.getUserNotificationId(id)
    }

    suspend fun updateAccountPassword(payload: UpdatePasswordPayload?): ResponseBody {
        return baseApiManager.userDetailsApi.updateAccountPassword(payload)
    }

    suspend fun getGuarantorTemplate(loanId: Long?): GuarantorTemplatePayload {
        return baseApiManager.guarantorApi.getGuarantorTemplate(loanId)
    }

    suspend fun getGuarantorList(loanId: Long): List<GuarantorPayload> {
        return baseApiManager.guarantorApi.getGuarantorList(loanId)
    }

    suspend fun createGuarantor(loanId: Long?, payload: GuarantorApplicationPayload?): ResponseBody {
        return baseApiManager.guarantorApi.createGuarantor(loanId, payload)
    }

    suspend fun updateGuarantor(payload: GuarantorApplicationPayload?, loanId: Long?, guarantorId: Long?): ResponseBody {
        return baseApiManager.guarantorApi.updateGuarantor(payload, loanId, guarantorId)
    }

    suspend fun deleteGuarantor(loanId: Long?, guarantorId: Long?): ResponseBody {
        return baseApiManager.guarantorApi.deleteGuarantor(loanId, guarantorId)
    }
}