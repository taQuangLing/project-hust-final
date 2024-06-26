<template>
    <div @click.self="closePopup">
        <div class="order-details">
            <h1>CHI TIẾT ĐƠN HÀNG</h1>
            <div class="info">
                <span class="datetime">
                    <label>Thời gian</label>
                    <span>{{ order.datetime }}</span>
                </span>
                <span class="branch">
                    <label>Chi nhánh</label>
                    <span>{{ order.branch }}</span>
                </span>
                <span class="payments">
                    <label>Phương thức thanh toán</label>
                    <span>{{ order.payments }}</span>
                </span>
                <span class="status">
                    <label>Trạng thái</label>
                    <span :style="statusStyle(order.status)">{{ order.status }}</span>
                </span>
                <span class="note" v-if="order.status === 'Đã hủy'">
                    <label>Ghi chú</label>
                    <span>{{ order.note }}</span>
                </span>
            </div>
            <div class="bill">
                <h3>CHI TIẾT ĐẶT HÀNG</h3>
                <div class="wrapper">
                    <span v-for="(item, index) in order.orderItem" :key="item.id" class="order-item">
                        <div class="name-wrapper">
                            <span class="index">{{ index + 1 }}.</span>
                            <span class="name">{{ item.name }}</span>
                            <span class="size" v-if="item.size != ''">{{ 'size ' + item.size }}</span>
                        </div>
                        <span class="quantity">{{ item.quantity }}</span>
                        <span class="price">{{ item.priceDisplay }}</span>
                    </span>
                </div>
                <span class="total">
                    <span>Tổng tiền</span>
                    <span>{{ total }}</span>
                </span>
            </div>
            <button class="close" @click="closePopup">Đóng</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
import { formatCurrency } from '@/helper/utils.js';

export default {
    props: ["orderId"],
    data() {
        return {
            order: {},
        }
    },
    computed: {
        total() {
            if (this.order.orderItem)
                return formatCurrency(this.order.orderItem.reduce((acc, item) => acc + item.price, 0));
        },
    },
    methods: {
        async getOrderDetails() {
            await axios.get(this.$store.state.baseUrl + "/admin/v1/orders/" + this.orderId + "?userId=" + localStorage.getItem("id"),
                {
                    headers: { Authorization: "Bearer " + localStorage.getItem("user") }
                }
            ).then(res => {
                this.order = res.data.data;
            })
                .catch(err => {
                    console.log(err);
                });
        },
        statusStyle(status) {
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
        closePopup() {
            this.$emit("close", false);
        },
    },
    async beforeMount() {
        await this.getOrderDetails();
    }
}

</script>