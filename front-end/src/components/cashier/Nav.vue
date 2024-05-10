<template>
  <div class="nav">
    <div class="nav-left">
      <div class="nav-sidebar">
        <router-link to="/cashier">
          <i
            :class="{ 'el-icon-s-home': true, active: state === 'home' }"
            @click="state = 'home'"
          ></i>
        </router-link>

        <router-link to="/cashier/order">
          <i
            :class="{
              'el-icon-shopping-cart-2': true,
              active: state === 'order',
            }"
            @click="state = 'order'"
          ></i>
        </router-link>
      </div>
      <i class="el-icon-user"></i>
    </div>
    <div class="content">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  // Your component logic here
  data() {
    return {
      state: "home",
    };
  },
  watch: {
    "$route.path": {
      immediate: true,
      handler(newVal) {
        switch (newVal) {
          case "/cashier/order":
            this.state = "order";
            break;
          case "/cashier":
            this.state = "home";
            break;
        }
      },
    },
  },
};
</script>

<style scoped>
.nav {
  display: grid;
  grid-template-columns: 70px 1fr;
  height: 100vh;
  overflow-x: none;
  overflow-x: hidden;
}

.nav-left {
  background: #edddd7;
  width: 70px;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: center;
  padding: 30px 0 30px;
}

.nav-sidebar {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  align-items: center;
}

i {
  color: rgb(83, 83, 83);
  font-size: 24px;
  margin-bottom: 30px;
  width: 35px;
  height: 35px;
  display: flex;
  align-items: center;
  justify-content: center;
}

a {
  text-decoration: none;
}

i:hover {
  cursor: pointer;
}

.nav-sidebar i {
  border-radius: 5px;
}

.el-icon-user {
  color: rgb(83, 83, 83);
  font-size: 27px;
  border: solid 1px rgb(82, 82, 82);
  width: 38px;
  height: 38px;
  border-radius: 25px;
}

.active {
  color: #fff;
  background: #aa715a;
}

.content {
  flex-grow: 1;
  background: #f3f3f3;
}

.icon {
  width: 35px;
  height: 35px;
}

.account-icon {
  width: 30px;
  height: 30px;
}
</style>
