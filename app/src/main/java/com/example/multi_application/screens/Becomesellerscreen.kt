package com.example.multi_application.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

// ---------------------------------------------------------------------------
// The buyer-becomes-seller flow has three screens:
//   1. BecomeSellerIntroScreen   — "Empower your business" landing page
//   2. SellerApplicationScreen   — the registration form (step 1 of 3)
//   3. SellerApplicationStatusScreen — review/tracking screen after submit
//
// This composable owns which step is showing so the rest of the app (the
// NavHost) only has to know about a single BECOME_SELLER route, exactly like
// before. onBackClick still means "leave the seller flow entirely" — it's
// only wired to the intro screen's back chevron and the status screen's back
// chevron; the application screen's back chevron returns to the intro step.
// ---------------------------------------------------------------------------
private enum class BecomeSellerStep { INTRO, APPLICATION, STATUS }

@Composable
fun BecomeSellerScreen(
    onBackClick: () -> Unit = {},
    onBellClick: () -> Unit = {},
    onSubmitApplication: (fullName: String, businessEmail: String, mobileNumber: String) -> Unit = { _, _, _ -> },
    onSellerHandbookClick: () -> Unit = {},
    onVendorProfileClick: () -> Unit = {},
    onContactSupportClick: () -> Unit = {},
    onFaqClick: () -> Unit = {},
    onProfileNavClick: () -> Unit = {},
    onOrdersNavClick: () -> Unit = {},
    onWishlistNavClick: () -> Unit = {}
) {
    var step by remember { mutableStateOf(BecomeSellerStep.INTRO) }

    when (step) {
        BecomeSellerStep.INTRO -> BecomeSellerIntroScreen(
            onBackClick = onBackClick,
            onBellClick = onBellClick,
            onGetStartedClick = { step = BecomeSellerStep.APPLICATION }
        )

        BecomeSellerStep.APPLICATION -> SellerApplicationScreen(
            onBackClick = { step = BecomeSellerStep.INTRO },
            onBellClick = onBellClick,
            onContinueClick = { fullName, businessEmail, mobileNumber ->
                onSubmitApplication(fullName, businessEmail, mobileNumber)
                step = BecomeSellerStep.STATUS
            },
            onProfileNavClick = onProfileNavClick,
            onOrdersNavClick = onOrdersNavClick,
            onWishlistNavClick = onWishlistNavClick
        )

        BecomeSellerStep.STATUS -> SellerApplicationStatusScreen(
            onBackClick = onBackClick,
            onBellClick = onBellClick,
            onSellerHandbookClick = onSellerHandbookClick,
            onVendorProfileClick = onVendorProfileClick,
            onContactSupportClick = onContactSupportClick,
            onFaqClick = onFaqClick
        )
    }
}