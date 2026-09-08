package com.example.multi_application

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShowChart
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import java.net.URLDecoder
import java.net.URLEncoder

// NOTE: despite its filename, WelcomeScreen.kt does NOT define a WelcomeScreen
// composable — it defines OnboardingScreen(onSkip, onFinish), which already
// renders the purple hero screen internally as its first step. There is no
// separate WelcomeScreen or SplashScreen composable in this project.
import com.example.multi_application.authentication.LoginScreen
import com.example.multi_application.authentication.OnboardingScreen
import com.example.multi_application.authentication.SignUpScreen
import com.example.multi_application.authentication.VerificationScreen
import com.example.multi_application.authentication.ForgotPasswordScreen
import com.example.multi_application.screens.AddressesScreen
import com.example.multi_application.screens.BecomeSellerScreen
import com.example.multi_application.screens.CategoriesScreen
import com.example.multi_application.screens.HomeScreen
import com.example.multi_application.screens.MyOrdersScreen
import com.example.multi_application.screens.ProductListScreen
import com.example.multi_application.screens.ProfileScreen
import com.example.multi_application.screens.SearchScreen
import com.example.multi_application.screens.SellerProfileScreen
import com.example.multi_application.screens.WishlistScreen
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.multi_application.screens.FoodDetailsScreen
import com.example.multi_application.screens.sampleAvocadaSalad
import com.example.multi_application.screens.sampleElenaSeller

// ---------------------------------------------------------------------------
// Routes — every destination in the app now lives in ONE flat NavHost.
// ---------------------------------------------------------------------------
object Routes {
    // Auth / onboarding flow.
    // Note: there is no separate splash route — OnboardingScreen renders the
    // purple hero/welcome screen as its own first internal step.
    const val ONBOARDING = "onboarding"
    const val LOGIN = "login"
    const val SIGN_UP = "signup"
    const val FORGOT_PASSWORD = "forgotPassword"
    const val VERIFICATION = "verification"

    // Main app (bottom-nav tabs)
    const val HOME = "home"
    const val SEARCH = "search"
    const val CATEGORIES = "categories"
    const val PROFILE = "profile"

    // Main app (pushed on top of tabs, no bottom bar)
    const val PRODUCT_LIST = "productList/{category}"
    const val PRODUCT_DETAILS = "productDetails/{productId}"
    const val SELLER_PROFILE = "sellerProfile/{sellerId}"
    const val BECOME_SELLER = "becomeSeller"

    // Profile sub-screens (each renders its own local bottom bar, see
    // ShopFlowBottomBar in ScreensSharedComponents.kt)
    const val MY_ORDERS = "myOrders"
    const val WISHLIST = "wishlist"
    const val ADDRESSES = "addresses"

    fun productList(category: String) =
        "productList/${URLEncoder.encode(category, "UTF-8")}"

    fun productDetails(productId: String) =
        "productDetails/${URLEncoder.encode(productId, "UTF-8")}"

    fun sellerProfile(sellerId: String) =
        "sellerProfile/${URLEncoder.encode(sellerId, "UTF-8")}"
}

// Only these routes show the bottom nav bar.
private data class BottomNavTab(
    val route: String,
    val label: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)

private val bottomNavTabs = listOf(
    BottomNavTab(Routes.HOME, "Home", Icons.Filled.ShoppingBag, Icons.Outlined.ShoppingBag),
    BottomNavTab(Routes.SEARCH, "Search", Icons.Filled.Search, Icons.Outlined.Search),
    BottomNavTab(Routes.CATEGORIES, "Categories", Icons.Filled.ShowChart, Icons.Outlined.ShowChart),
    BottomNavTab(Routes.PROFILE, "Profile", Icons.Filled.History, Icons.Outlined.History)
)

// ---------------------------------------------------------------------------
// Single top-level NavHost for the entire app.
// ---------------------------------------------------------------------------
@Composable
fun MultiMarketNavHost(
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination

    // Bottom bar only renders on the four tab routes — not on onboarding,
    // login, sign up, forgot password, verification, product list/details,
    // search, seller profile, or the profile sub-screens (those draw their
    // own bar).
    val showBottomBar = bottomNavTabs.any { tab ->
        currentRoute?.hierarchy?.any { it.route == tab.route } == true
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                AppBottomNavBar(navController, currentRoute)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.ONBOARDING,
            modifier = Modifier.padding(if (showBottomBar) innerPadding else PaddingValues(0.dp))
        ) {

            // ---------------- Auth / onboarding flow ----------------

            composable(Routes.ONBOARDING) {
                OnboardingScreen(
                    onSkip = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    },
                    onFinish = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.ONBOARDING) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.LOGIN) {
                LoginScreen(
                    onForgotPassword = {
                        navController.navigate(Routes.FORGOT_PASSWORD)
                    },
                    onSignIn = { _, _ ->
                        // TODO: authenticate with the backend, then navigate to Home
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onSignUpClick = {
                        navController.navigate(Routes.SIGN_UP)
                    }
                )
            }

            composable(Routes.SIGN_UP) {
                SignUpScreen(
                    onForgotPassword = {
                        navController.navigate(Routes.FORGOT_PASSWORD)
                    },
                    onSignUp = { formState ->
                        // TODO: handle account creation with formState.name, formState.email, formState.password
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.SIGN_UP) { inclusive = true }
                        }
                    },
                    onSignInClick = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.SIGN_UP) { inclusive = true }
                        }
                    }
                )
            }

            composable(Routes.FORGOT_PASSWORD) {
                ForgotPasswordScreen(
                    onBack = { navController.popBackStack() },
                    onSendResetLink = { _ ->
                        // TODO: trigger the backend reset-link/OTP send with the email
                        navController.navigate(Routes.VERIFICATION)
                    }
                )
            }

            composable(Routes.VERIFICATION) {
                VerificationScreen(
                    onBack = { navController.popBackStack() },
                    onContinue = { _ ->
                        // TODO: verify the code with the backend, then route to a
                        // real "set new password" screen once it exists; for now
                        // send the user back to Login.
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    },
                    onResend = {
                        // TODO: re-trigger the backend to resend the code
                    }
                )
            }

            // ---------------- Main app: bottom-nav tabs ----------------

            composable(Routes.HOME) {
                HomeScreen(
                    onSearchClick = { navController.navigate(Routes.SEARCH) },
                    onSeeAllCategoriesClick = { navController.navigate(Routes.CATEGORIES) },
                    onCategoryClick = { _ -> navController.navigate(Routes.CATEGORIES) },
                    onProductClick = { product ->
                        navController.navigate(Routes.productDetails(product.id))
                    }
                )
            }

            composable(Routes.SEARCH) {
                SearchScreen(
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(Routes.CATEGORIES) {
                CategoriesScreen(
                    onBackClick = { navController.popBackStack() },
                    onSearchQueryClick = { navController.navigate(Routes.SEARCH) },
                    onCategoryClick = { category ->
                        navController.navigate(Routes.productList(category.name))
                    }
                )
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    onOpenShopClick = { navController.navigate(Routes.BECOME_SELLER) },
                    onMyOrdersClick = { navController.navigate(Routes.MY_ORDERS) },
                    onMyWishlistClick = { navController.navigate(Routes.WISHLIST) },
                    onAddressesClick = { navController.navigate(Routes.ADDRESSES) },
                    onLogOutClick = {
                        navController.navigate(Routes.LOGIN) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                    // Wallet / Profile Details / Payment Methods / Notifications /
                    // App Settings / Help Center still default to no-ops until
                    // those screens exist — wire them up the same way when ready.
                )
            }

            // ---------------- Profile sub-screens (no shared bottom bar) ----------------

            composable(Routes.MY_ORDERS) {
                MyOrdersScreen(
                    onProfileNavClick = {
                        navController.navigate(Routes.PROFILE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onWishlistNavClick = {
                        navController.navigate(Routes.WISHLIST) { launchSingleTop = true }
                    },
                    onSellNavClick = { navController.navigate(Routes.BECOME_SELLER) }
                )
            }

            composable(Routes.WISHLIST) {
                WishlistScreen(
                    onProfileNavClick = {
                        navController.navigate(Routes.PROFILE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onOrdersNavClick = {
                        navController.navigate(Routes.MY_ORDERS) { launchSingleTop = true }
                    },
                    onSellNavClick = { navController.navigate(Routes.BECOME_SELLER) }
                )
            }

            composable(Routes.ADDRESSES) {
                AddressesScreen(
                    onProfileNavClick = {
                        navController.navigate(Routes.PROFILE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onOrdersNavClick = {
                        navController.navigate(Routes.MY_ORDERS) { launchSingleTop = true }
                    },
                    onWishlistNavClick = {
                        navController.navigate(Routes.WISHLIST) { launchSingleTop = true }
                    },
                    onSellNavClick = { navController.navigate(Routes.BECOME_SELLER) }
                )
            }

            // ---------------- Main app: pushed screens (no bottom bar) ----------------

            composable(
                route = Routes.PRODUCT_LIST,
                arguments = listOf(navArgument("category") { type = NavType.StringType })
            ) { backStackEntry ->
                val category = backStackEntry.arguments?.getString("category")
                    ?.let { URLDecoder.decode(it, "UTF-8") } ?: "Technology"
                ProductListScreen(
                    category = category,
                    onBackClick = { navController.popBackStack() },
                    onSearchClick = { navController.navigate(Routes.SEARCH) },
                    onProductClick = { product ->
                        navController.navigate(Routes.productDetails(product.id))
                    }
                )
            }

            composable(
                route = Routes.PRODUCT_DETAILS,
                arguments = listOf(navArgument("productId") { type = NavType.StringType })
            ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getString("productId")
                    ?.let { URLDecoder.decode(it, "UTF-8") }

                // Local favorite toggle state, scoped to this destination.
                var isFavorite by remember { mutableStateOf(false) }

                // TODO: replace with a real lookup once food items come from a
                // repository/API; for now every id resolves to the sample dish.
                FoodDetailsScreen(
                    food = sampleAvocadaSalad.copy(id = productId ?: sampleAvocadaSalad.id),
                    isFavorite = isFavorite,
                    onBackClick = { navController.popBackStack() },
                    onFavoriteToggle = { isFavorite = !isFavorite },
                    onReadMoreClick = {
                        // TODO: expand the description in place, or navigate to a
                        // full-details/reviews screen once one exists.
                    }
                )
            }

            composable(
                route = Routes.SELLER_PROFILE,
                arguments = listOf(navArgument("sellerId") { type = NavType.StringType })
            ) { backStackEntry ->
                val sellerId = backStackEntry.arguments?.getString("sellerId")
                    ?.let { URLDecoder.decode(it, "UTF-8") }
                // TODO: replace with a real lookup once sellers come from a
                // repository/API; for now every id resolves to the sample seller.
                SellerProfileScreen(
                    seller = sampleElenaSeller.copy(id = sellerId ?: sampleElenaSeller.id),
                    onBackClick = { navController.popBackStack() },
                    onSearchClick = { navController.navigate(Routes.SEARCH) },
                    onProductClick = { productId ->
                        navController.navigate(Routes.productDetails(productId))
                    },
                    onHomeClick = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    onSearchNavClick = { navController.navigate(Routes.SEARCH) },
                    onProfileClick = {
                        navController.navigate(Routes.PROFILE) {
                            popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }

            composable(Routes.BECOME_SELLER) {
                BecomeSellerScreen(
                    onBackClick = { navController.popBackStack() },
                    onSubmitApplication = { _, _, _ ->
                        // TODO: send the application to your backend, then
                        // navigate wherever makes sense once it's accepted —
                        // e.g. back to Profile or into a new Seller Dashboard.
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}

@Composable
private fun AppBottomNavBar(
    navController: NavHostController,
    currentRoute: NavDestination?
) {
    NavigationBar {
        bottomNavTabs.forEach { tab ->
            val selected = currentRoute?.hierarchy?.any { it.route == tab.route } == true
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) }
            )
        }
    }
}