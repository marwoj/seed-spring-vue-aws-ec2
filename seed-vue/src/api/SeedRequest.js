import ajax from "@/ajax";

export default {
  getMessage() {
    return ajax.get("/public/visitors").then(res => {
      return res.data;
    });
  },
  sendMail(mail) {
    return ajax.post("/mail", mail);
  }
};
