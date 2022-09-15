import React from 'react';

const Checkbox = ({name, label, readOnly}) => {
    return (
        <div className="flex items-start">
            <div className="flex h-5 items-center">
                {readOnly ?
                    <input
                        id={name}
                        name={name}
                        type="checkbox"
                        disabled
                        className="h-4 w-4 rounded border-gray-300 text-indigo-600 focus:ring-indigo-500"
                    /> :
                    <input
                        id={name}
                        name={name}
                        type="checkbox"
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