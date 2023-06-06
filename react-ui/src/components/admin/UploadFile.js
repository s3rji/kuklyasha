import React from 'react';
import {FileUploader} from "react-drag-drop-files";
import {PhotographIcon} from "@heroicons/react/solid";
import {deleteFiles, uploadFile} from "../../http/fileApi";

const UploadFile = ({gallery, setGallery, doll}) => {
    const fileTypes = ["JPG", "PNG"];

    const upload = file => {
        const formData = new FormData();
        formData.append("file", file);
        uploadFile(formData)
            .then(data => setGallery([...gallery, data.fileName]))
            .catch(error => alert(error.response.data.message))
    }

    const deleteOne = (fileName, index) => {
        if (!doll.gallery.includes(fileName)) {
            deleteFiles({"fileNames": [fileName]})
                .catch(error => console.log(error))
        }
        gallery.splice(index, 1)
        setGallery([...gallery])
    }

    return (
        <div>
            <label htmlFor="photo"
                   className="block text-sm font-medium leading-6 text-gray-900">
                Фото
            </label>
            <div className="flex gap-4">
                {gallery.map((picture, index) => (
                    <div className="grid-col-2 py-2" key={index}>
                        <div
                            className="h-24 w-24 col-span-1 flex-shrink-0 overflow-hidden rounded-md border border-gray-700">
                            <img
                                src={process.env.REACT_APP_IMAGES_URL + picture}
                                alt={picture}
                                className="h-full w-full object-cover object-center"
                            />
                        </div>
                        <button
                            type="button"
                            onClick={() => deleteOne(picture, index)}
                            className="col-span-1 w-24 rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-400 hover:bg-gray-200"
                        >
                            Удалить
                        </button>
                    </div>
                ))}
            </div>
            <FileUploader handleChange={upload} types={fileTypes}>
                <div
                    className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
                    <div className="text-center">
                        <PhotographIcon
                            className="mx-auto h-12 w-12 text-gray-300"
                            aria-hidden="true"/>
                        <div
                            className="mt-4 flex text-sm leading-6 text-gray-600">
                            <label
                                htmlFor="file-upload"
                                className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
                            >
                                <span>Загрузить файл</span>
                                <input id="file-upload" name="file-upload"
                                       type="file" onChange={upload}
                                       className="sr-only"/>
                            </label>
                            <p className="pl-1">или перетащите</p>
                        </div>
                        <p className="text-xs leading-5 text-gray-600">PNG, JPG
                            не
                            более 10MB</p>
                    </div>
                </div>
            </FileUploader>
        </div>
    );
};

export default UploadFile;