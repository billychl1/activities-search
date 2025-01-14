<script>
import Activities from './components/Activities.vue'

export default {
  name: 'App',
  components: {
    Activities
  },
  data() {
    return {
      searchQuery: ""
    };
  },
  methods: {
    async searchActivities() {
      const response = await fetch(`http://localhost:8080/activities/search?title=${this.searchQuery}`, {
        method: "GET",
        headers: {
          Accept: 'application/json',
          'Content-Type': 'application/json',
        },
      });
      const activities = await response.json();
      this.$refs.activities.updateActivities(activities);
    }
  }
}
</script>

<template>
  <div id="app">
    <h1>Activities</h1>
    <input type="text" v-model="searchQuery" placeholder="Search activities by title..." @input="searchActivities" />
    <button @click="searchActivities">Search</button>
    <Activities ref="activities" />
  </div>
</template>

<style>
#app {
  text-align: center;
  color: #2c3e50;
}
</style>
