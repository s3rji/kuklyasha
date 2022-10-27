import React from 'react';
import PhoneInput from "react-phone-input-2";

const InputPhone = ({name, label, readOnly, value, setValue, error}) => {
    return (
        <>
            <label htmlFor={name} className="block text-sm font-medium text-gray-700">
                {label}
            </label>
            {readOnly ?
                <PhoneInput
                    specialLabel={''}
                    country={"ru"}
                    countryCodeEditable={false}
                    value={value}
                    onChange={setValue}
                    placeholder="9876543210"
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2 text-gray-500"
                    disabled
                /> :
                <>
                    <PhoneInput
                        specialLabel={''}
                        country={"ru"}
                        countryCodeEditable={false}
                        value={value}
                        onChange={setValue}
                        placeholder="9876543210"
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2"
                    />
                    <span className="mt-2 text-sm text-red-600 dark:text-red-500">
                        {error}
                    </span>
                </>
            }
        </>
    );
};

export default InputPhone;