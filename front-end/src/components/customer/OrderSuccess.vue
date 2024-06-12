<template>
    <div>
        <i class="el-icon-arrow-left" @click="this.$router.go(-1)"></i>
        <i class="el-icon-success"></i>
        <h1>Đơn hàng của bạn đã thực hiện thành công</h1>
        <h2>Vui lòng đến quầy thu ngân để thực hiện thanh toán</h2>
        <router-link to="/"><h3>Quay lại “Trang chủ”</h3></router-link>
    </div>
</template>

<script>
</script>

<style scoped>
.el-icon-arrow-left {
    display: fixed;
    position: absolute;
    width: 35px;
    height: 35px;
    background-color: #00000057;
    top: 72px;
    left: 15px;
    border-radius: 7px;
    display: flex;
    justify-content: center;
    align-items: center;
    color: #fff;
    font-size: 18px;
    font-weight: 800;
}
.el-icon-success {
    font-size: 130px;
    color: #00D89E;
    margin-top: 150px;
}
h1 {
    font-size: 27px;
    color: #3A3A3A;
    padding: 0 20px 0 20px;
    margin-top: 30px;
}

h2 {
    font-size: 16px;
    font-weight: normal;
    color: #8E8E8E;
    margin-top: 30px;
    padding: 0 30px 0 30px;
}

h3 {
    color: #424242;
    font-weight: normal;
    font-size: 18px;
    text-decoration: underline;
    margin-top: 50px;
}

</style>