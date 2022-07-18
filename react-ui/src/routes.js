import {Catalog, DollPage, Login, Registration} from "./pages/index";
import {CATALOG_ROUTE, DOLL_ROUTE, LOGIN_ROUTE, REGISTRATION_ROUTE} from "./utils/consts";

export const publicRoutes = [
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