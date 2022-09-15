import React from 'react';

const InputField = ({type, name, label, readOnly, value, autoComplete, setValue}) => {
    return (
        <>
            <label htmlFor={name} className="block text-sm font-medium text-gray-700">
                {label}
            </label>
            {readOnly ?
                <input
                    type={type}
                    name={name}
                    id={name}
                    autoComplete={autoComplete}
                    value={value}
                    onChange={setValue}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2 text-gray-500"
                    disabled
                /> :
                <input
                    type={type}
                    name={name}
                    id={name}
                    autoComplete={autoComplete}
                    value={value}
                    onChange={setValue}
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2"
                />
            }
        </>
    );
};

export default InputField;