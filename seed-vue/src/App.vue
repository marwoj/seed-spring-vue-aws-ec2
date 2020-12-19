<template>
  <v-app>
    <v-app-bar app color="primary" dark>
      <v-spacer></v-spacer>
      <v-btn href="https://github.com/marwoj/seed-spring-vue-aws-ec2" target="_blank" text>
        <span class="mr-2">Visit project</span>
        <v-icon>mdi-github</v-icon>
      </v-btn>
    </v-app-bar>
    <v-main>
      <v-container>
        <v-row class="text-center">
          <v-col>
            <router-link to="/" class="mr-2">
              <v-btn color="primary">Home
                <v-icon right dark>mdi-link</v-icon>
              </v-btn>
            </router-link>
            <router-link to="/about" class="mr-2">
              <v-btn color="secondary">About
                <v-icon right dark>mdi-link</v-icon>
              </v-btn>
            </router-link>
          </v-col>
        </v-row>
      </v-container>
      <router-view/>
    </v-main>
  </v-app>
</template>
<script>
export default {
  name: "App",
  data: () => ({
    //
  })
};
</script>
<style lang="scss">
a {
  text-decoration: none;
}
</style>
