import {NavBar, AppRouter} from "./components/index"
import {BrowserRouter} from "react-router-dom";
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
