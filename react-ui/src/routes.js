import {Catalog, DollPage, Login, Registration} from "./pages/index";
import {CATALOG_ROUTE, DOLL_ROUTE, LOGIN_ROUTE, MAIN_ROUTE, REGISTRATION_ROUTE} from "./utils/consts";
import Main from "./pages/Main";

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
    }
]