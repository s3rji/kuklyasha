import Catalog from "./components/Catalog";
import NavBar from "./components/NavBar"
import {BrowserRouter} from "react-router-dom";

function App() {
    return (
        <BrowserRouter>
            <NavBar></NavBar>
            <Catalog></Catalog>
        </BrowserRouter>
    );
}

export default App;
