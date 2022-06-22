import NavBar from "./components/NavBar"
import {BrowserRouter} from "react-router-dom";
import AppRouter from "./components/AppRouter";
import {observer} from "mobx-react-lite";

const App = observer(() => {
    return (
        <BrowserRouter>
            <NavBar></NavBar>
            <AppRouter></AppRouter>
        </BrowserRouter>
    );
});

export default App;
