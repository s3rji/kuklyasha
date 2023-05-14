import React, {useContext} from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import {adminRoutes, authRoutes, publicRoutes} from "../routes";
import {ADMIN_ROLE, CATALOG_ROUTE} from "../utils/consts";
import {observer} from "mobx-react-lite";
import {Context} from "../index";

const AppRouter = observer(() => {
    const {user} = useContext(Context)

    return (
        <Routes>
            {user.isAuth && user.activeRole === ADMIN_ROLE && adminRoutes.map(({path, Component}) =>
                <Route key={path} path={path} element={<Component/>} exact/>
            )}
            {user.isAuth && authRoutes.map(({path, Component}) =>
                <Route key={path} path={path} element={<Component/>} exact/>)
            }
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