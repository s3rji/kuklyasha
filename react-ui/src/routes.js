import {
    AboutUs,
    Cart,
    Catalog,
    Contacts,
    DollPage,
    Login,
    Main,
    Orders,
    Profile,
    Registration
} from "./pages/index";
import {
    AdminCatalog,
    AdminOrders,
    AdminUsers,
} from "./pages/admin/index";
import {
    ABOUT_US_ROUTE,
    ADMIN_CATALOG_ROUTE,
    ADMIN_ORDERS_ROUTE,
    ADMIN_USERS_ROUTE,
    CART_ROUTE,
    CATALOG_ROUTE,
    CONTACTS_ROUTE,
    DOLL_ROUTE,
    LOGIN_ROUTE,
    MAIN_ROUTE,
    ORDERS_ROUTE,
    PROFILE_ROUTE,
    REGISTRATION_ROUTE
} from "./utils/consts";

export const publicRoutes = [
    {
        path: MAIN_ROUTE,
        Component: Main
    },
    {
        path: CATALOG_ROUTE,
        Component: Catalog
    },
    {
        path: DOLL_ROUTE + "/:id",
        Component: DollPage
    },
    {
        path: LOGIN_ROUTE,
        Component: Login
    },
    {
        path: REGISTRATION_ROUTE,
        Component: Registration
    },
    {
        path: ABOUT_US_ROUTE,
        Component: AboutUs
    },
    {
        path: CONTACTS_ROUTE,
        Component: Contacts
    }
]

export const authRoutes = [
    {
        path: PROFILE_ROUTE,
        Component: Profile
    },
    {
        path: ORDERS_ROUTE,
        Component: Orders
    },
    {
        path: CART_ROUTE,
        Component: Cart
    }
]

export const adminRoutes = [
    {
        path: ADMIN_CATALOG_ROUTE,
        Component: AdminCatalog
    },
    {
        path: ADMIN_ORDERS_ROUTE,
        Component: AdminOrders
    },
    {
        path: ADMIN_USERS_ROUTE,
        Component: AdminUsers
    }
]