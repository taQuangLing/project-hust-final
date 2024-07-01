<template>
    <el-row class="tac">
        <el-col :span="12">
            <img src="../../assets/logo.png" alt="">
            <el-menu :default-active="index" class="el-menu-vertical-demo">
                <router-link to="/admin">
                    <el-menu-item index="1">
                        <i class="el-icon-s-home"></i>
                        <span>Trang chủ</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/admin/order-history">
                    <el-menu-item index="2">
                        <i class="el-icon-s-order"></i>
                        <span>Lịch sử đơn hàng</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/admin/employees">
                    <el-menu-item index="3">
                        <i class="el-icon-user-solid"></i>
                        <span>Nhân viên</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/admin/branches">
                    <el-menu-item index="4">
                        <i class="el-icon-s-shop"></i>
                        <span>Chi nhánh</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/admin/products">
                    <el-menu-item index="5">
                        <i class="el-icon-coffee-cup"></i>
                        <span>Sản phẩm</span>
                    </el-menu-item>
                </router-link>
            </el-menu>
            <div class="account">
                <el-dropdown :hide-on-click="false">
                    <span class="el-dropdown-link">
                        <i class="el-icon-user"></i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item>Thông tin</el-dropdown-item>
                        <el-dropdown-item>Đăng xuất</el-dropdown-item>
                    </el-dropdown-menu>
                </el-dropdown>
                <button class="logout">Đăng xuất</button>
            </div>
        </el-col>
        <div class="content">
            <router-view />
        </div>
    </el-row>
</template>

<script>
export default {
    data() {
        return {
            index: "",
        };
    },
    methods: {
    },
    mounted(){
        switch (this.$route.path) {
            case "/admin":
                this.index = "1";
                break;
            case "/admin/order-history":
                this.index = "2";
                break;
            case "/admin/employees":
                this.index = "3";
                break;
            case "/admin/branches":
                this.index = "4";
                break;
            case "/admin/products":
                this.index = "5";
                break;
        }
    },
}
</script>

<style scoped>
.tac {
    height: 100vh;
    display: flex;
}

.el-col {
    width: 260px;
    height: 100%;
    background-color: #EDDDD7;
    text-align: left;
}

.el-menu {
    background-color: #EDDDD7;
    padding: 0 15px 0 15px;
}

.el-menu-item {
    border-radius: 10px;
    padding: 0 15px 0 15px !important;
    height: 45px;
    display: flex;
    flex-direction: row;
    margin-bottom: 10px;
    color: black;
}

.el-menu-item i {
    margin-right: 10px;
    display: flex;
    align-items: center;
    color: #575757;
    font-size: 22px;
}

.el-menu-item span {
    display: flex;
    align-items: center;
    font-family: 'sarabun', sans-serif !important;
    font-size: 15px;
    font-weight: 500;
}

img {
    height: 35px;
    margin: 15px 0 20px 20px;
}

.is-active {
    background-color: #AA715A;
    color: white;
}

.el-menu-item.is-active i {
    color: white;
}

.content {
    flex-grow: 1;
}

.el-icon-user {
    color: rgb(83, 83, 83);
    font-size: 27px;
    border: solid 1px rgb(82, 82, 82);
    width: 38px;
    height: 38px;
    border-radius: 25px;
    margin-right: 15px;
    color: rgb(83, 83, 83);
    font-size: 24px;
    width: 35px;
    height: 35px;
    display: flex;
    align-items: center;
    justify-content: center;
}

.account {
    display: flex;
    position: absolute;
    bottom: 30px;
    left: 20px;
    flex-direction: row;
    align-items: center;
}

.logout {
    width: 150px;
    height: 35px;
    font-size: 15px;
    background-color: #00000000;
    border: 1px solid #444444;
    border-radius: 7px;
}

.logout:hover {
    background-color: #ffffff;
    color: rgb(0, 0, 0);
    cursor: pointer;
}
</style>