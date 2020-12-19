import axios from "axios";

import Configuration from "./ConfigProvider";

const apiUrl = Configuration.value("backendHost");
const basicAuthUsername = Configuration.value("basicAuthUsername");
const basicAuthPassword = Configuration.value("basicAuthPassword");

let ajax = axios.create({
    baseURL: apiUrl ? apiUrl : "http://localhost:8080",
    auth: {
        username: basicAuthUsername ? basicAuthUsername : "changeme",
        password: basicAuthPassword ? basicAuthPassword : "changeme",
    }
});

export default {...ajax};
