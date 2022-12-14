import React from 'react';

const Checkbox = ({name, label, readOnly, value, setValue}) => {
    return (
        <div className="flex items-start">
            <div className="flex h-5 items-center">
                {readOnly ?
                    <input
                        id={name}
                        name={name}
                        type="checkbox"
                        checked={value}
                        disabled
                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                    /> :
                    <input
                        id={name}
                        name={name}
                        type="checkbox"
                        checked={value}
                        onChange={setValue}
                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                    />
                }
            </div>
            <div className="ml-3 text-sm">
                <label htmlFor={name} className="font-medium text-gray-700">
                    {label}
                </label>
            </div>
        </div>
    );
};

export default Checkbox;