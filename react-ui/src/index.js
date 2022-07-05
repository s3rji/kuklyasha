import React, {createContext} from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import App from "./App";
import DollStore from "./store/DollStore";
import NavigationStore from "./store/NavigationStore";

export const Context = createContext(null)

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <Context.Provider value={{
        doll: new DollStore(),
        navigation: new NavigationStore()
    }}>
        <React.StrictMode>
            <App/>
        </React.StrictMode>
    </Context.Provider>
);
