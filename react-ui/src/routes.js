import Catalog from "./pages/Catalog";
import {CATALOG_ROUTE, DOLL_ROUTE, LOGIN_ROUTE} from "./utils/consts";
import Login from "./pages/Login";

export const publicRoutes = [
    {
        path: CATALOG_ROUTE,
        Component: Catalog
    },
    {
        path: DOLL_ROUTE + "/:id",
        Component: Catalog
    },
    {
        path: LOGIN_ROUTE,
        Component: Login
    }
]