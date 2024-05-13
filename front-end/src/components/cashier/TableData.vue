<template>
  <div class="order-data">
    <div class="pagination">
      <el-pagination layout="prev, pager, next" :total="1000"> </el-pagination>
      <span>1-20/128 bản ghi</span>
    </div>
    <hr style="width: 98%;">
    <div class="title">
      <span class="code">Mã hóa đơn</span>
      <span class="datetime">Thời gian tạo</span>
      <span class="total">Tổng tiền</span>
      <span class="hinh-thuc">Hình thức</span>
      <span class="payment">Phương thức thanh toán</span>
      <span class="status">Trạng thái</span>
      <div class="space"></div>
    </div>
    <div v-for="order in orders" v-bind:key="order.id" class="order">
      <hr style="width: 100%">
      <div class="order-info">
        <span class="code">{{ order.code }}</span>
        <span class="datetime">{{ order.datetime }}</span>
        <span class="total">{{ order.total }}</span>
        <span class="hinh-thuc">{{ order.hinhThuc }}</span>
        <span class="payment">{{ order.payment }}</span>
        <span class="status" :style="statusColor(order.status)">{{
        order.status
      }}</span>
        <div class="space">
          <div>
            <el-dropdown v-if="order.status === 'Chờ xác nhận' || order.status === 'Đang pha chế'">
              <el-button type="primary">
                Hành động<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item>Chỉnh sửa</el-dropdown-item>
                <el-dropdown-item>Đã thanh toán</el-dropdown-item>
                <el-dropdown-item>Hoàn thành</el-dropdown-item>
                <el-dropdown-item>Hủy</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <div @click="toggleExpand(order.id)" v-if="order.status === 'Hoàn thành' || order.status === 'Đã hủy'"
              class="expand">
              <i v-if="order.isExpanded === false" class="el-icon-arrow-down"></i>
              <i v-if="order.isExpanded === true" class="el-icon-arrow-up"></i>
            </div>
          </div>
        </div>
      </div>
      <div class='order-item' v-show="order.isExpanded">
        <div v-for="item in order.items" v-bind:key="item.id" class="item">
          <span class="name">{{ item.name + " x " + item.quantity }}</span>
          <span class="price">{{ item.price }}</span>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
export default {
  props: {
    orders: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {};
  },
  methods: {
    statusColor(status) {
      switch (status) {
        case "Chờ xác nhận":
          return { color: "#EFD700" };
        case "Đang pha chế":
          return { color: "#3278CA" };
        case "Đã hủy":
          return { color: "#CF2127" };
        case "Hoàn thành":
          return { color: "#0D9F1C" };
        default:
          return {};
      }
    },
    toggleExpand(orderId) {
      const order = this.orders.find(order => order.id === orderId);
      if (order) {
        order.isExpanded = !order.isExpanded;
      }
      console.log(order);
    }
  },
};
</script>

<style>
.order-data {
  width: 100%;
  height: 100vh;
  /* background-color: yellow; */
  display: flex;
  flex-direction: column;
  align-items: center;
  overflow-y: auto;
  color: #484848;
}

.order-data::-webkit-scrollbar {
  width: 10px;
}

.order-data::-webkit-scrollbar-track {
  background: #f1f1f1;
}

.order-data::-webkit-scrollbar-thumb {
  background: #b6b6b6;
}

.order-data::-webkit-scrollbar-thumb:hover {
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

.order-data .title {
  width: calc(100% - 20px);
  padding: 20px 10px 20px 10px;
  display: flex;
  justify-content: space-between;
}

.order-data .order {
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

.order-data .item {
  display: flex;
  justify-content: right;
  margin-bottom: 10px;
  width: 86.8%;
}

.item .name {
  font-weight: 600;
  width: 300px;
  text-align: left;
  margin-right: 20px;
}

.item span {
  font-size: 15px;
}

.price {
  width: 120px;
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

.order-data.payment {
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

.order-data .el-button span {
  font-family: "sarabun", "sans-serif";
  font-size: 14px;
}

.el-button--primary:focus {
  background-color: #f0f0f0;
  border: solid 0.5px #8d8d8d;
  color: #4e4e4e
}

.el-dropdown-menu__item:not(.is-disabled) {
  font-family: "sarabun", "sans-serif";
}
</style>
