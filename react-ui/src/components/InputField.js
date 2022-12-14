import React from 'react';

const InputField = ({type, name, label, readOnly, value, autoComplete, setValue, error}) => {
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
                    className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2 text-gray-500"
                    disabled
                /> :
                <>
                    <input
                        type={type}
                        name={name}
                        id={name}
                        autoComplete={autoComplete}
                        value={value}
                        onChange={setValue}
                        className="mt-1 block w-full rounded-md border-gray-300 shadow-sm shadow-gray-600/50 focus:border-indigo-500 focus:ring-indigo-500 sm:text-sm indent-2"
                    />
                    <span className="mt-2 text-sm text-red-600 dark:text-red-500 ">
                        {error}
                    </span>
                </>
            }
        </>
    );
};

export default InputField;