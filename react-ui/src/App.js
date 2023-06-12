import {AppRouter, Loading, NavBar} from "./components/index"
import {AdminBar} from "./components/admin/index"
import {BrowserRouter} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {useContext, useEffect, useState} from "react";
import {check} from "./http/userAPI";
import {Context} from "./index";
import {ADMIN_ROLE} from "./utils/consts";

const App = observer(() => {
    const {user} = useContext(Context)
    const [loading, setLoading] = useState(true)

    useEffect(() => {
        check().then(userData => {
            user.setIsAuth(true)
            user.setRoles(userData.roles)
            user.setActiveRole(userData.roles[0])
        }).finally(() => setLoading(false))
    }, [])

    if (loading) {
        return <Loading/>
    }

    return (
        <BrowserRouter>
            {user.activeRole === ADMIN_ROLE ? <AdminBar></AdminBar> : <NavBar></NavBar>}
            <AppRouter></AppRouter>
        </BrowserRouter>
    );
});

export default App;