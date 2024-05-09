<template>
  <div class='nav'>
    <header>
      <img src="../../assets/logo.png" alt="Logo" class="logo">
      <i class="el-icon-search"></i>
    </header>
    <main>
        <router-view/>
    </main>
    <nav>
      <i :class="{'el-icon-s-home': true, 'active': state === 'home'}" @click='state = "home"'></i>
      <i :class="{'el-icon-shopping-cart-2': true, 'active': state === 'shopping-cart'}" @click='state="shopping-cart"'></i>
      <i :class="{'el-icon-s-order': true,  'active' : state === 'history'}" @click='state="history"'></i>
    </nav>
  </div>
</template>

<script>
    export default {
        components: {
        },
        data() {
            return {
                state: 'home',
            }
        }
        
    }
</script>


<style scoped>
.nav {
    display: flex;
    flex-direction: column;
    height: 100vh;
    padding: 0 15px 0 15px;
}

.el-icon-search {
    /* border: 0.5px solid #ababab; */
    display: flex;
    font-size: 20px;
    height: 35px;
    width: 35px;
    align-items: center;
    justify-content: center;
    border-radius: 100px;
    background-color: #ffffff;
    font-weight: 700;
}

.el-icon-s-home, .el-icon-shopping-cart-2, .el-icon-s-order {
    font-size: 25px;
    height: 45px;
    width: 45px;
    display: flex;
    align-items: center;
    justify-content: center;
    color: #545454;
}
.el-icon-shopping-cart-2 {
  font-weight: bold;
}

header {
  height: 60px;
  display: flex;
  align-items: center;
  box-sizing: border-box;
  justify-content: space-between;
  border-bottom: solid 0.5px #949494;
}

nav {
  position: fixed;
  bottom: 0;
  left: 0;
  right: 0;
  height: 60px;
  padding: 0 80px 0 80px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background: #fbfbfb;
  /* Thêm các styles khác cho điều hướng nếu cần */
}

.logo {
  height: 30px;
}

.search-button {
  /* Style cho nút tìm kiếm */
}

.active {
    color: #FF902A;
    background: #eeeeee;
    border-radius: 12px;
}

main {
  /* border: solid 0.5px #222; */
  height: calc(100vh - 120px);
}

.nav-button {
  flex: 1;
  /* Style cho các nút điều hướng */
}
</style>