import {observer} from "mobx-react-lite";
import {CartButton, Currency, Identification, Logo, Navigation, ProfileDropdown, SearchButton} from "./index";
import {useContext} from "react";
import {Context} from "../index";

const NavBar = observer(() => {
    const {user} = useContext(Context)

    return (
        <nav className="bg-pink-600">
            <div className="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
                <div className="relative flex items-center justify-between h-16">
                    <div className="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
                        <Logo/>
                        <Navigation/>
                    </div>

                    <div
                        className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                        <div className="ml-auto flex items-center">
                            {!user.isAuth && <Identification/>}
                            <Currency/>
                            <SearchButton/>
                            {user.isAuth && <CartButton/>}
                            {user.isAuth && <ProfileDropdown/>}
                        </div>
                    </div>
                </div>
            </div>
        </nav>
    );
});

export default NavBar;