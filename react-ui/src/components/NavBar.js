import {Disclosure} from "@headlessui/react"
import {observer} from "mobx-react-lite";
import {Navigation, Identification, Currency,  Logo, SearchButton, CartButton, ProfileDropdown} from "./index";

const NavBar = observer(() => {
    return (
        <Disclosure as="nav" className="bg-pink-600">
            {({open}) => (
                <>
                    <div className="max-w-7xl mx-auto px-2 sm:px-6 lg:px-8">
                        <div className="relative flex items-center justify-between h-16">
                            <div className="flex-1 flex items-center justify-center sm:items-stretch sm:justify-start">
                                <Logo/>
                                <Navigation/>
                            </div>

                            <div
                                className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                                <div className="ml-auto flex items-center">
                                    <Identification/>
                                    <Currency/>
                                    <SearchButton/>
                                    <CartButton/>
                                </div>

                                <ProfileDropdown/>
                            </div>
                        </div>
                    </div>
                </>
            )}
        </Disclosure>
    );
});

export default NavBar;