<template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App",
};
</script>

<style>
#app {
  margin: 0;
  font-family: "Sarabun", sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
  background: #e8e8e8;
}
a {
  text-decoration: none;
}
body {
  all: unset;
}

hr {
  margin: 0;
}

.btn {
  cursor: pointer;
}

</style>
