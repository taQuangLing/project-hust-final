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
  font-family: "Sarabun", sans-serif !important;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #000000;
  background: #fafafa;
}

a {
  text-decoration: none;
}

body {
  all: unset;
}

button {
    cursor: pointer;
}

hr {
  margin: 0;
}

.btn {
  cursor: pointer;
}

.el-dropdown-menu__item {
  font-family: "sarabun", "sans-serif";
}
.el-message__content {
  font-family: "sarabun", "sans-serif" !important;
}

input[type="checkbox"] {
  accent-color: #474747 !important;
  margin: 0;
}
</style>
