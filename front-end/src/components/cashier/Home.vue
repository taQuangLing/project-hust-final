<template>
  <div class="container">
    <div class="left">
      <TableData 
            :orders="orders"
        />
    </div>
    <hr style="width: 0">
    <div class="right">
      <div class="creation">
        <i class="el-icon-circle-plus-outline"></i>
        <span>Tạo đơn mới</span>
      </div>
      <hr />
      <div class="status">
        <el-radio v-model="radioStatus" label="1">Tất cả đơn</el-radio>
        <el-radio v-model="radioStatus" label="2">Chờ xác nhận</el-radio>
        <el-radio v-model="radioStatus" label="3">Đang pha chế</el-radio>
        <el-radio v-model="radioStatus" label="4">Hoàn thành</el-radio>
        <el-radio v-model="radioStatus" label="5">Đã hủy</el-radio>
      </div>
      <hr />
      <div class="hinh-thuc">
        <el-radio v-model="radioHinhThuc" label="1">Tất cả</el-radio>
        <el-radio v-model="radioHinhThuc" label="2">Tại bàn</el-radio>
        <el-radio v-model="radioHinhThuc" label="3">Mang đi</el-radio>
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
          <el-date-picker
            v-model="fromDate"
            type="date"
            placeholder="Pick a day"
          ></el-date-picker>
        </div>
        <div class="toDate">
          <span class="demonstration">Đến ngày</span>
          <el-date-picker
            v-model="toDate"
            type="date"
            placeholder="Pick a day"
          ></el-date-picker>
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
import TableData from "./TableData.vue";
export default {
  components: {
    TableData,
  },
  data() {
    return {
      radioStatus: "1",
      radioHinhThuc: "1",
      searchInput: "",
      fromDate: "",
      toDate: "",
      orders: [
        {
            "id": 1234, 
            "code": "MX235742893",
            "datetime": "02/04/2024 10:30:42",
            "total": "145,000 đ",
            "hinhThuc": "Tại bàn",
            "payment": "Tiền mặt",
            "status": "Chờ xác nhận",
            items: [
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                },
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                }
            ]
        },
        {
            "id": 1234, 
            "code": "MX235742893",
            "datetime": "02/04/2024 10:30:42",
            "total": "145,000 đ",
            "hinhThuc": "Tại bàn",
            "payment": "Tiền mặt",
            "status": "Đang pha chế",
            items: [
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                },
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                }
            ]
        },
        {
            "id": 1234, 
            "code": "MX235742893",
            "datetime": "02/04/2024 10:30:42",
            "total": "145,000 đ",
            "hinhThuc": "Tại bàn",
            "payment": "Tiền mặt",
            "status": "Đã hủy",
            items: [
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                },
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                }
            ]
        },
        {
            "id": 1234, 
            "code": "MX235742893",
            "datetime": "02/04/2024 10:30:42",
            "total": "145,000 đ",
            "hinhThuc": "Tại bàn",
            "payment": "Tiền mặt",
            "status": "Hoàn thành",
            items: [
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                },
                {
                    "id": 63,
                    "name": "Cà phê sữa",
                    "quantity": 2,
                    "price": "45,000 đ",
                }
            ]
        }
      ]
    };
  },
  methods: {
    reset() {
      this.radioStatus = "1";
      this.radioHinhThuc = "1";
      this.searchInput = "";
      this.fromDate = "";
      this.toDate = "";
    },
  },
};
</script>

<style scoped>
.container {
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: row;
}

.left {
    /* background: #fff; */
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
  width: 70%;
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

>>> .el-radio__inner {
  width: 16px;
  height: 16px;
}

>>> .el-radio__label {
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

>>> .el-input__inner {
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
}
</style>
