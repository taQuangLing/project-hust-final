<template>
    <div class="order-item">
        <div class="start">
            <span class="datetime">{{ order.orderAt }}</span>
            <span :class="[status, statusStyle]">{{ order.status }}</span>
        </div>
        <div class="main">
            <div class="item" v-for="item in order.orderItemList" v::key="item.id">
                <img :src="item.productImg" alt="">
                <div class="info">
                    <span class="name">{{ item.productName }}</span>
                    <span class="size" v-if="item.hasSize">size {{ item.sizeSelected }}</span>
                    <div class="price">
                        <span class="price-display">{{ item.productPrice }}</span>
                        <span class="quantity">x{{ item.quantity }}</span>
                    </div>
                    <span class="note">{{ item.note }}</span>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: 'OrderItem',
    props: ['order'],
    data() {
        return {
            status: this.order.status,
        }
    },
    computed: {
        statusStyle() {
            switch (this.status) {
                case "Chờ xác nhận":
                    return "payment-pending";
                case "Hoàn thành":
                    return "done";
                case "Đang pha chế":
                    return "pending";
                case "Đã hủy":
                    return "cancel";
                default:
                    break;
            }
        }
    }
}

</script>