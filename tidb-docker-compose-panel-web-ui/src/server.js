import Axios from "axios";
export function list () {
    return Axios.get("/cluster");
}