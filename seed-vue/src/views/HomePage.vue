<template>
  <v-container class="home">
    <v-row>
      <v-col>
        <div class="d-flex align-center">
          <p>To setup this Seed Project, several technologies were used:</p>
        </div>
        <div class="d-flex align-center">
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Spring Boot" class="shrink mr-2" contain :src="require('@/assets/spring.svg')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Spring Boot</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Kotlin" class="shrink mr-2" contain :src="require('@/assets/kotlin.svg')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Kotlin</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="MongoDB" class="shrink mr-2" contain :src="require('@/assets/mongo.svg')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>MongoDB</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Vue" class="shrink mr-2" contain :src="require('@/assets/logo.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Vue</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Vuetify" class="shrink mr-2" contain :src="require('@/assets/vuetify-logo-dark.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Vuetify</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Ansible" class="shrink mr-2" contain :src="require('@/assets/ansible.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Ansible</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Docker" class="shrink mr-2" contain :src="require('@/assets/docker.svg')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Docker</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Traefik" class="shrink mr-2" contain :src="require('@/assets/traefik.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Traefik</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="AWS EC2" class="shrink mr-2" contain :src="require('@/assets/aws.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>AWS EC2</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Cloudflare" class="shrink mr-2" contain :src="require('@/assets/cloudflare.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Cloudflare</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-img alt="Mailgun" class="shrink mr-2" contain :src="require('@/assets/mailgun.png')"
                     v-bind="attrs" v-on="on" transition="scale-transition" width="50"/>
            </template>
            <span>Mailgun</span>
          </v-tooltip>
        </div>
      </v-col>
    </v-row>
    <HelloWorld :response="response" :show-error="showError" @sendEmail="sendEmail"/>
    <v-snackbar v-model="snackbar.show" :color="snackbar.color">{{ snackbar.message }}
      <template v-slot:action="{ attrs }">
        <v-btn color="indigo" text v-bind="attrs" @click="snackbar.show = false">Close</v-btn>
      </template>
    </v-snackbar>
  </v-container>
</template>

<script>
// @ is an alias to /src
import HelloWorld from "@/components/HelloWorld.vue";
import SeedRequest from "@/api/SeedRequest";

export default {
  name: "HomePage",
  components: {
    HelloWorld,
  },
  data() {
    return {
      response: null,
      showError: false,
      snackbar: {
        show: false,
        message: "",
        color: "",
      },
    };
  },
  methods: {
    sendEmail(email) {
      SeedRequest.sendMail(email)
          .then(() => {
            this.showSnackbar("Email message sent successfully", "success");
          })
          .catch(error => {
            console.log(error);
            this.showSnackbar("Email message could not be sent", "warning");
          });
    },
    showSnackbar(message, color) {
      this.snackbar.message = message;
      this.snackbar.color = color;
      this.snackbar.show = "true";
    },
  },
  created() {
    SeedRequest.getMessage()
        .then(res => {
          this.response = res;
          this.showError = false;
        })
        .catch(error => {
          console.log(error);
          this.showError = true;
          this.showSnackbar("Request to API failed!", "warning");
        });
  },
};
</script>
