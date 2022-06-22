import {makeAutoObservable} from "mobx";

export default class DollStore {
    constructor() {
        this._dolls = []
        makeAutoObservable(this)
    }

    get dolls() {
        return this._dolls;
    }

    setDolls(dolls) {
        this._dolls = dolls;
    }
}