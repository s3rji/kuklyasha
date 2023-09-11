import React, {createContext} from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import {CartStore, DollStore, NavigationStore, OrderStore, UsersListStore, UserStore} from "./store/index";
import {AdminNavigationStore} from "./store/admin/index";

export const Context = createContext(null)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Context.Provider value={{
        user: new UserStore(),
        doll: new DollStore(),
        navigation: new NavigationStore(),
        adminNavigation: new AdminNavigationStore(),
        order: new OrderStore(),
        cart: new CartStore(),
        usersList: new UsersListStore()
    }}>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </Context.Provider>
);
