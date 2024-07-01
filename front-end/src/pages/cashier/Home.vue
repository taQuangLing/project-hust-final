<template>
  <div class="container">
    <div class="left">
      <TableData class="table-data" 
        :orders="orders" 
        :radioStatus="radioStatus"
        :radioHinhThuc="radioHinhThuc"
        :searchInput="searchInput"
        :fromDate="fromDate"
        :toDate="toDate"  
      />
    </div>
    <hr style="width: 0">
    <div class="right">
      <router-link class="creation" to="/cashier/order">
        <i class="el-icon-circle-plus-outline"></i>
        <span>Tạo đơn mới</span>
      </router-link>
      <hr />
      <div class="status">
        <el-radio v-model="radioStatus" label="0">Tất cả đơn</el-radio>
        <el-radio v-model="radioStatus" label="Chờ xác nhận">Chờ xác nhận</el-radio>
        <el-radio v-model="radioStatus" label="Đang pha chế">Đang pha chế</el-radio>
        <el-radio v-model="radioStatus" label="Hoàn thành">Hoàn thành</el-radio>
        <el-radio v-model="radioStatus" label="Đã hủy">Đã hủy</el-radio>
      </div>
      <hr />
      <div class="hinh-thuc">
        <el-radio v-model="radioHinhThuc" label="0">Tất cả</el-radio>
        <el-radio v-model="radioHinhThuc" label="Tại bàn">Tại bàn</el-radio>
        <el-radio v-model="radioHinhThuc" label="Mang đi">Mang đi</el-radio>
      </div>
      <hr />
      <div class="search">
        <el-input placeholder="Nhập mã hóa đơn..." v-model="searchInput">
          <i slot="prefix" class="el-input__icon el-icon-search"></i>
        </el-input>
      </div>
      <hr />
      <div class="date-filter">
        <div class="fromDate">
          <span class="demonstration">Từ ngày</span>
          <el-date-picker format="dd/MM/yyyy" v-model="fromDate" type="date" placeholder="Pick a day"></el-date-picker>
        </div>
        <div class="toDate">
          <span class="demonstration">Đến ngày</span>
          <el-date-picker format="dd/MM/yyyy" v-model="toDate" type="date" placeholder="Pick a day"></el-date-picker>
        </div>
        <div class="reset" @click='reset'>
          <i class="el-icon-refresh-right"></i>
          <span>Reset</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import TableData from "@/components/TableData.vue";

export default {
  components: {
    TableData,
  },
  data() {
    return {
      radioStatus: "0",
      radioHinhThuc: "0",
      searchInput: "",
      fromDate: this.initDate(),
      toDate: this.initDate(),
      orders: [],
    };
  },
  methods: {
    initDate() {
      return new Date().setHours(0, 0, 0, 0);
    },
    reset() {
      this.radioStatus = "0";
      this.radioHinhThuc = "0";
      this.searchInput = "";
      this.fromDate = this.initDate();
      this.toDate = this.initDate();
    },
    async getOrders(){
      await axios.get(this.$store.state.baseUrl + "/cashier/v1/orders/" + localStorage.getItem("id"),
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
        .then(response => {
          this.orders = response.data.data;
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  beforeMount(){
    this.getOrders();
    this.interval = setInterval(this.getOrders, 10000);
  },
  beforeDestroy(){
    clearInterval(this.interval);
  }
};
</script>

<style scoped>
.table-data {
  width: 100%;
  height: 100vh;
  /* background-color: yellow; */
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  color: #484848;
}

.left>>>.table-data {
  ::-webkit-scrollbar {
    width: 10px;
  }

  ::-webkit-scrollbar-track {
    background: #f1f1f1;
  }

  ::-webkit-scrollbar-thumb {
    background: #b6b6b6;
  }

  ::-webkit-scrollbar-thumb:hover {
    background: #8b8b8b;
  }

  .pagination {
    /* padding-right: 15px; */
    display: flex;
    width: 100%;
    height: 30px;
    align-items: end;
    justify-content: space-between;
    margin: 10px 0 5px;
  }

  .pagination span {
    font-size: 15px;
    margin-right: 15px;
  }

  .el-pagination {
    height: 21px;
    padding-left: 30px;
  }

  .el-pager li,
  .el-pagination .btn-next,
  .el-pagination .btn-prev,
  .el-pagination button:disabled {
    background: none;
  }

  .el-pagination button:disabled {
    color: #ffffff00;
  }

  .title {
    width: calc(100% - 20px);
    padding: 20px 10px 20px 10px;
    display: flex;
    justify-content: space-between;
  }

  .order {
    width: calc(100% - 20px);
    padding: 0 10px 0px 10px;
    display: flex;
    flex-direction: column;
    justify-items: end;
  }

  .order-item {
    width: 100%;
    margin-bottom: 20px;
  }

  .item {
    display: flex;
    justify-content: right;
    margin-bottom: 15px;
    width: 86.8%;
  }

  .item span {
    font-size: 15px;
    color: #000
  }

  .item .name {
    font-weight: 600;
    min-width: 300px;
    text-align: left;
    margin-right: 20px;
    color: #000000;
  }

  .quantity {
    color: #000000;
    font-weight: 500;
    width: 100px;
  }

  .price {
    width: 120px;
    color: #313131 !important;
  }

  .note { 
    width: 300px;
    color: #8b8b8b !important;
  }

  .order-info {
    display: flex;
    justify-content: space-between;
    margin-bottom: 15px;
    background: #efefef;
    padding: 10px 0 10px;
    height: 33px;
  }

  .order-info span {
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .title span {
    font-size: 15px;
    font-weight: 600;
    color: #2c2c2c;
  }

  .span {
    font-size: 15px;
    color: #4b4b4b;
  }

  .code {
    width: 13.42%;
  }

  .datetime {
    width: 18.16%;
  }

  .total {
    width: 11.47%;
    font-weight: 600;
  }

  .hinh-thuc {
    width: 12.42%;
  }

  .payment {
    width: 19.12%;
  }

  .status {
    width: 13.42%;
  }

  .space {
    width: 10.42%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-right: 8px;
  }

  .el-button {
    /* width: 110px; */
    background-color: #fff;
    border: solid 0.5px #8d8d8d;
    border-radius: 7px;
    padding: 8px 10px 8px 12px;
    color: #4e4e4e;
  }

  .el-button:hover {
    background-color: #f0f0f0;
    border: solid 0.5px #313131;
    color: #4e4e4e
  }

  .el-button span {
    font-family: "sarabun", "sans-serif";
    font-size: 14px;
  }

  .el-button--primary:focus {
    background-color: #f0f0f0;
    border: solid 0.5px #8d8d8d;
    color: #4e4e4e
  }

  .el-dropdown-menu__item{
    font-family: "sarabun", "sans-serif" !important;
  }
}

.container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: row;
  background: #fff;
}

.left {
  width: 83%;
  height: 100%;
}

.right {
  width: 17%;
  height: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
}

.creation {
  background: #13b03f;
  color: white;
  width: 65%;
  min-height: 40px;
  height: auto;
  border-radius: 5px;
  margin-top: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 30px;
  padding: 0 10px 0 10px;
}

.creation:hover {
  cursor: pointer;
}

.creation i {
  margin-right: 5px;
  font-size: 20px;
}

.creation span {
  font-size: 16px;
  font-weight: 300;
  height: 100%;
  padding-top: 17px;
  user-select: none
}

.status {
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-self: start;
  align-items: space-between;
  margin: 30px 0 20px;
}

hr {
  width: 100%;
  margin: 0;
}

.el-radio,
.el-checkbox {
  text-align: left;
  height: 35px;
  margin-left: 30px;
}

.el-radio__inner {
  width: 16px;
  height: 16px;
}

.el-radio__label {
  font-size: 17px;
}

.hinh-thuc {
  display: flex;
  flex-direction: column;
  margin: 30px 0 20px;
  width: 100%;
}

.search {
  width: 85%;
  margin: 25px 0 25px;
}

.el-input__inner {
  border-radius: 7px;
  font-size: 15px;
}

.date-filter {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 20px 0 20px;
  width: 100%;
}

.fromDate,
.toDate {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 88%;
}

.toDate {
  margin-top: 10px;
}

.demonstration {
  text-align: left;
  width: 90px;
}

.el-date-editor--date {
  width: 60%;
}

.reset {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100px;
  height: 35px;
  background: #fff;
  border: solid 0.5px #7e7e7e;
  border-radius: 7px;
  margin-top: 50px;
}

.reset:hover {
  cursor: pointer;
}

.el-icon-refresh-right {
  font-size: 18px;
  margin-right: 5px;
}

.reset span {
  color: #474747;
  user-select: none
}
</style>
