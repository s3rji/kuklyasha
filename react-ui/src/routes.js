import {AboutUs, Catalog, Contacts, DollPage, Login, Main, Profile, Orders, Registration, Cart} from "./pages/index";
import {
    ABOUT_US_ROUTE,
    CATALOG_ROUTE,
    CONTACTS_ROUTE,
    DOLL_ROUTE,
    LOGIN_ROUTE,
    MAIN_ROUTE, PROFILE_ROUTE,
    REGISTRATION_ROUTE, ORDERS_ROUTE, CART_ROUTE
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