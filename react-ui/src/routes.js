import Catalog from "./pages/Catalog";
import {CATALOG_ROUTE, DOLL_ROUTE} from "./utils/consts";

export const publicRoutes = [
    {
        path: CATALOG_ROUTE,
        Component: Catalog
    },
    {
        path: DOLL_ROUTE + "/:id",
        Component: Catalog
    }
]