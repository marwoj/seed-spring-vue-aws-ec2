<template>
  <v-container>
    <v-row class="text-center">
      <v-col class="mb-4">
        <div class="hello">
          <div v-if="showError">
            <v-alert text dense color="red">Request to API failed</v-alert>
          </div>
          <div v-else>
            <div v-if="response">
              <span>Just now </span>
              <v-chip label outlined color="blue">{{ dateTime }}</v-chip>
              <span> request from address </span>
              <v-chip label outlined color="blue">{{ response.ip }}</v-chip>
              <span> was sent to API. Hurray! </span>
            </div>
            <div v-else>
              <v-progress-circular indeterminate color="primary"></v-progress-circular>
              <span>Sending request to API</span>
            </div>
          </div>
          <div>
            <v-form ref="form" v-model="formValid" class="mt-6">
              <v-alert v-if="showError" text dense>Could not connect to API. Email can't be send right now.</v-alert>
              <v-alert v-else text dense>Fill form to send email</v-alert>
              <v-text-field v-model="message.to" :counter="100" :rules="emailRules" label="To" required></v-text-field>
              <v-text-field v-model="message.title" :counter="100" :rules="fieldRules" label="Title"
                            required></v-text-field>
              <v-text-field v-model="message.body" :counter="100" :rules="fieldRules" label="Message"
                            required></v-text-field>
              <v-btn :disabled="!formValid || showError" color="success" elevation="1" class="mr-4" @click="sendMail">
                Send email
              </v-btn>
              <v-btn color="error" class="mr-4" elevation="1" @click="reset">Reset Form</v-btn>
            </v-form>
          </div>
          <br>
          <v-alert type="info" v-if="message.to">
            Note: If you are using mailgun developers account, make sure that email address is added to recipients
            list. Otherwise email won't be sent. Right now, using this page, ewail will be sent only to a single e-mail
            address, specified in mailgun configuration.
          </v-alert>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "HelloWorld",
  props: {
    response: Object,
    showError: Boolean
  },
  data: () => ({
    formValid: false,
    message: {
      to: '',
      title: '',
      body: '',
    },
    fieldRules: [
      v => !!v || 'Field is required',
      v => (v && v.length <= 100) || 'Field must have less than 100 characters',
    ],
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
  }),
  methods: {
    reset() {
      this.$refs.form.reset()
    },
    sendMail() {
      if (this.formValid) {
        this.$emit("sendEmail", this.message);
        this.reset();
      }
    },
  },
  computed: {
    dateTime: function () {
      return new Date(this.response.timestamp).toGMTString()
    }
  }
};
</script>
