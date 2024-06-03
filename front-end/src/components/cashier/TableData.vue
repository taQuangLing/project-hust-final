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
            <el-dropdown v-if="order.status === 'Chờ xác nhận' || order.status === 'Đang pha chế'" trigger="click">
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

