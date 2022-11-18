import React, {createContext} from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import DollStore from "./store/DollStore";
import NavigationStore from "./store/NavigationStore";
import UserStore from "./store/UserStore";
import OrderStore from "./store/OrderStore";

export const Context = createContext(null)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Context.Provider value={{
        user: new UserStore(),
        doll: new DollStore(),
        navigation: new NavigationStore(),
        order: new OrderStore()
    }}>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </Context.Provider>
);
