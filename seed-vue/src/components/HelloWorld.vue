<template>
  <v-container>
    <v-row class="text-center">
      <v-col class="mb-4">
        <div class="hello">
          <div v-if="response">
            <span>Just now </span>
            <v-chip label outlined color="blue">{{ dateTime }}</v-chip>
            <span> request from address </span>
            <v-chip label outlined color="blue">{{ response.ip }}</v-chip>
            <span> was sent to API. Hurra! </span>
          </div>
          <div>
            <v-form ref="form" v-model="formValid" class="mt-6">
              <v-alert text dense>Fill form to send email</v-alert>
              <v-text-field v-model="message.to" :counter="100" :rules="emailRules" label="To" required></v-text-field>
              <v-text-field v-model="message.title" :counter="100" :rules="fieldRules" label="Title"
                            required></v-text-field>
              <v-text-field v-model="message.body" :counter="100" :rules="fieldRules" label="Message"
                            required></v-text-field>
              <v-btn :disabled="!formValid" color="success" class="mr-4" @click="sendMail">Send email</v-btn>
              <v-btn color="error" class="mr-4" @click="reset">Reset Form</v-btn>
            </v-form>
          </div>
          <br>
          <v-alert type="info">Note: If you are using mailgun developers account, make sure that email address is added
            to recipients list.
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
    response: Object
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
