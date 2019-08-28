import Axios from "axios";
export function list () {
    return Axios.get("/cluster");
}
export function purge (clusterName) {
    return Axios.delete(`/cluster/${clusterName}`);
}
export function startup (cluster) {
    return Axios.post("/cluster", cluster);
}