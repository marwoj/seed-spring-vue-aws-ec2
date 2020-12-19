import dotenv from "dotenv";

dotenv.config();

export default class ConfigProvider {
  static get CONFIG() {
    return {
      backendHost: "$VUE_APP_API_ADDRESS",
      basicAuthUsername: "$VUE_APP_BASIC_AUTH_USERNAME",
      basicAuthPassword: "$VUE_APP_BASIC_AUTH_PASSWORD"
    };
  }

  static value(name) {
    if (!(name in this.CONFIG)) {
      return;
    }
    const value = this.CONFIG[name];
    if (value && value.startsWith("$VUE_APP_")) {
      const envName = value.substr(1);
      return process.env[envName];
    } else {
      return value;
    }
  }
}
