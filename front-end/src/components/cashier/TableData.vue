<template>
  <div class="data">
    <div class="pagination">
      <el-pagination layout="prev, pager, next" :total="1000"> </el-pagination>
      <span>1-20/128 bản ghi</span>
    </div>
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
      <div class="order-info">
        <span class="code">{{ order.code }}</span>
        <span class="datetime">{{ order.datetime }}</span>
        <span class="total">{{ order.total }}</span>
        <span class="hinh-thuc">{{ order.hinhThuc }}</span>
        <span class="payment">{{ order.payment }}</span>
        <span class="status" :style="statusColor(order.status)">{{
          order.status
        }}</span>
        <div class="space"></div>
      </div>
      <div class="order-item">
        <div
          v-for="item in order.items"
          v-bind:key="item.id"
          class="item"
        >
            <span class="name">{{ item.name + " x " + item.quantity}}</span>
            <span class="price">{{ item.price }}</span>
        </div>
      </div>
      <hr>
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
  },
};
</script>

<style>
.data {
  width: 100%;
  height: 100%;
  /* background-color: yellow; */
  display: flex;
  flex-direction: column;
}

.pagination {
  /* padding-right: 15px; */
  display: flex;
  width: 100%;
  height: 30px;
  align-items: end;
  justify-content: space-between;
}

.pagination span {
  font-size: 14px;
  margin-right: 15px;
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
  padding: 10px 10px 20px 10px;
  display: flex;
  justify-content: space-between;
}
.order {
  width: calc(100% - 20px);
  padding: 10px 10px 0px 10px;
  display: flex;
  flex-direction: column;
  justify-items: end;
}

.order-item {
  width: 100%;
  height: 50px;
  margin: 20px 0 20px;
}

.item {
    display: flex;
    justify-content: right;
    margin-bottom: 10px;
    width: calc(100% - 175px);
    padding-right: 175px;
}

.item .name {
    font-weight: 500;
    font-size: 15px;
    width: 300px;
    text-align: left;
}

.price {
    width: 120px;
}

.order-info {
  display: flex;
  justify-content: space-between;
}

.title span {
  font-size: 14px;
  font-weight: 600;
  color: #2c2c2c;
}

.order span {
  font-size: 14px;
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
}

hr {
    width: 100%;
    margin: 0;
}
</style>
