<script>
export default {
  name: "activities-component",
  data() {
    return {
      activities: [],
    };
  },
  methods: {
    updateActivities(newActivities) {
      this.activities = newActivities;
    }
  },
  async mounted() {
    const response = await fetch('http://localhost:8080/activities', {
      method: "GET",
      headers: {
        Accept: 'application/json',
        'Content-Type': 'application/json',
      },
    });
    this.activities = await response.json();
  }
}
</script>

<template>
  <div class="activities__container">
    <div v-for="activity in activities" :key="activity.id" class="activities__activity">
      <p>Title: {{ activity.title }}</p>
      <p>Price: {{ activity.price }} {{ activity.currency }}</p>
      <p>Rating: {{ activity.rating }}</p>
      <p>Special Offer: {{ activity.specialOffer ? "Yes" : "No" }}</p>
      <p>Supplier Name: {{ activity.supplierName }}</p>
      <p>Supplier Location: {{ activity.supplierLocation }}</p>
    </div>
  </div>
</template>

<style lang="scss">
.activities {
  &__container {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    max-width: 1200px;
    padding: 0 20px;
  }
  &__activity {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    max-width: 300px;
    margin: 0 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: center;
    transition: all 0.3s ease-in-out;
    &:hover {
      border: 1px solid #000;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
    }
  }
}

@media screen and (min-width: 768px) {
  .activities {
    &__container {
      width: 100%;
      padding: 0;
      row-gap: 20px;
    }
  }
}
</style>
