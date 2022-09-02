import {Main, Catalog, DollPage, Login, Registration, AboutUs} from "./pages/index";
import {CATALOG_ROUTE, DOLL_ROUTE, LOGIN_ROUTE, MAIN_ROUTE, REGISTRATION_ROUTE, ABOUT_US_ROUTE} from "./utils/consts";

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
    }
]