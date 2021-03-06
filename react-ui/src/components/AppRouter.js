import React from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import {publicRoutes} from "../routes";
import {CATALOG_ROUTE} from "../utils/consts";
import {observer} from "mobx-react-lite";

const AppRouter = observer(() => {
    return (
        <Routes>
            {publicRoutes.map(({path, Component}) =>
                <Route key={path} path={path} element={<Component/>} exact/>
            )}
            <Route
                path="*" element={<Navigate to={CATALOG_ROUTE} replace/>}
            />
        </Routes>
    );
});

export default AppRouter;