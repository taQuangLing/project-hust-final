<template>
  <div class="order-data">
    <div class="pagination">
      <el-pagination :page-size="pageSize" layout="prev, pager, next" :total="length"
        @current-change="setPage"></el-pagination>
      <span>{{ (page - 1) * pageSize + 1 }}-{{ Math.min(length, (page) * pageSize) }}/{{ length }} bản ghi</span>
    </div>
    <hr style="width: 98%;">
    <div class="title">
      <!-- <span class="code">Mã hóa đơn</span> -->
      <span class="datetime">Thời gian tạo</span>
      <span class="total">Tổng tiền</span>
      <span class="hinh-thuc">Hình thức</span>
      <span class="payment">Phương thức thanh toán</span>
      <span class="status">Trạng thái</span>
      <div class="space"></div>
    </div>
    <div v-for="(order, index) in ordersResult" v-bind:key="order.id" class="order">
      <hr style="width: 100%">
      <div class="order-info">
        <!-- <span class="code">{{ order.code }}</span> -->
        <span class="datetime">{{ order.orderAt }}</span>
        <span class="total">{{ order.total }}</span>
        <span class="hinh-thuc">{{ order.isOrderAtTable }}</span>
        <span class="payment">{{ order.payments }}</span>
        <span class="status" :style="statusColor(order.status)">{{
        order.status
      }}</span>
        <div class="space">
          <div>
            <el-dropdown @command="handleCommand"
              v-if="order.status === 'Chờ xác nhận' || order.status === 'Đang pha chế'" trigger="click">
              <el-button type="primary">
                Hành động<i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command='{order, command: "edit"}'>Chỉnh sửa</el-dropdown-item>
                <el-dropdown-item :command='{order, command: "payment"}'>Đã thanh toán</el-dropdown-item>
                <el-dropdown-item :command='{order, command: "done"}'>Hoàn thành</el-dropdown-item>
                <el-dropdown-item :command='{order, command: "cancel"}'>Hủy</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <div @click="toggleExpand(order)" v-if="order.status === 'Hoàn thành' || order.status === 'Đã hủy'"
              class="expand">
              <i v-if="order.isExpanded === false" class="el-icon-arrow-down"></i>
              <i v-if="order.isExpanded === true" class="el-icon-arrow-up"></i>
            </div>
          </div>
        </div>
      </div>
      <div class='order-item' v-show="order.isExpanded">
        <div v-for="item in order.orderItemList" v-bind:key="item.id" class="item">
          <span class="name">{{ item.productName }}</span>
          <span class="quantity">x{{ item.quantity }}</span>
          <span class="price">{{ item.productPrice }}</span>
          <span class="note">{{ item.note }}</span>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import { convertStringToDate } from '@/helper/utils.js';
import axios from 'axios';
import { jwtDecode } from 'jwt-decode';

export default {
  props: {
    orders: {
      type: Array,
      required: true,
    },
    radioStatus: {
      required: true,
    },
    radioHinhThuc: {
      required: true,
    },
    searchInput: {
      type: String,
      required: false,
    },
    fromDate: {
      required: false,
    },
    toDate: {
      required: false,
    },
  },
  data() {
    return {
      page: 1,
      pageSize: 10,
      length: 0,
    };
  },
  computed: {
    ordersResult() {
      let result = this.orders.filter(order => {
        const orderAt = new Date(convertStringToDate(order.orderAt))
        orderAt.setHours(0, 0, 0, 0);
        return (order.status === this.radioStatus || this.radioStatus === "0")
          && (order.isOrderAtTable === this.radioHinhThuc || this.radioHinhThuc === "0")
          // && order.code.includes(this.searchInput) 
          && orderAt >= this.fromDate
          && orderAt <= this.toDate;
      });
      this.length = result.length;
      // return result;
      return result.slice(this.pageSize * this.page - this.pageSize, this.pageSize * this.page)

    },
  },
  methods: {
    statusColor(status) {
      switch (status) {
        case "Chờ xác nhận":
          return { color: "#FFA500" };
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
    toggleExpand(order) {
      order.isExpanded = !order.isExpanded;
      return true;
    },
    setPage(val) {
      this.page = val
    },
    handleCommand(param) {
      switch (param.command) {
        case 'edit':
          this.edit(param.order.id);
          break;
        case 'payment':
          this.updateStatus(param.order, 1)
          break;
        case 'done':
          this.updateStatus(param.order, 2)
          break;
        case 'cancel':
          this.updateStatus(param.order, 3)
          break;
        default:
          break;
      }

    },
    edit(id){
      axios.put(this.$store.state.baseUrl + `/cashier/v1/carts/orders/${id}`, {}, 
      { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
        .then(res => {
          if (res.data.code != 2000){
            this.$message({
              message: res.data.description,
              type: 'error'
            });
            return false;
          }
          this.$router.push({ name: 'order'});
        })
        .error(error => {
          console.log(error)
          this.$message({
              message: error,
              type: 'error'
            });
            return false;
        })
    },
    updateStatus(order, status) {
      let request = {};
      request.status = status;
      request.userId = localStorage.getItem('id');
      axios.put(this.$store.state.baseUrl + `/cashier/v1/orders/${order.id}/status`,
        request, {
        headers: {
          'Authorization': 'Bearer ' + localStorage.getItem('user')
        }
      })
        .then(res => {
          if (res.data.code === 2000) {
            this.$message({
              message: 'Cập nhật trạng thái thành công',
              type: 'success'
            });
            order.status = status === 1 ? 'Đang pha chế' : status === 2 ? 'Hoàn thành' : 'Đã hủy';
            return true;
          }
          this.$message({
            message: 'Cập nhật trạng thái thất bại',
            type: 'error'
          });
          return false;
        })
        .catch(error => {
          console.log(error)
        })
    },
  },
};
</script>
