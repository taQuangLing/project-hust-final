<template>
    <div class="order">
        <div class="cart">
            <div class="title">
                <div class="space1"></div>
                <div class="name">Tên sản phẩm</div>
                <div class="size">Size</div>
                <div class="quantity">Số lượng</div>
                <div class="price">Đơn giá</div>
                <div class="space1"></div>
                <div class="space2"></div>
            </div>
            <div class="content">
                <div class="cart-item" v-for="cart in carts.cartItemList">
                    <div class="space1" @click="decrementQuantity(cart)"><i class="el-icon-minus btn"></i></div>
                    <span class="name">{{ cart.name }}</span>
                    <span class="size">{{ cart.sizeSelected }}</span>
                    <span class="quantity">{{ cart.quantity }}</span>
                    <span class="price">{{ cart.priceDisplay }}</span>
                    <div class="space1" @click="incrementQuantity(cart)"><i class="el-icon-plus btn"></i></div>
                    <i class="el-icon-delete space2 btn" @click="deleteItem(cart)"></i>
                </div>
            </div>
            <div class="action">
                <div class="space">
                    <el-dropdown class="hinhThuc" @command="hinhThucCommand">
                        <el-button type="primary">
                            {{ hinhThuc }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="1">Tại bàn</el-dropdown-item>
                            <el-dropdown-item command="0">Mang đi</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-dropdown class="payment" @command="paymentCommand">
                        <el-button type="primary">
                            {{ payments }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="1">Chuyển khoản</el-dropdown-item>
                            <el-dropdown-item command="0">Tiền mặt</el-dropdown-item>
                            <el-dropdown-item command="2">VNPay</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <div class="total">
                    <label for="">Tổng thanh toán</label>
                    <span>{{ total }}</span>
                </div>
                <div class="btn-order" @click="submit">
                    <el-button type="primary">ĐẶT ĐỒ</el-button>
                </div>
            </div>
        </div>
        <hr />
        <div class="category">
            <div class="category-wrapper" @click="categoryActive = -1">
                <i :class='{ "el-icon-menu": true, "menu-icon": true, "menu-active": -1 == categoryActive }'></i>
                <span :class="{ 'name': true, 'name-active': -1 === categoryActive }">Tất cả</span>
            </div>
            <div v-for="category in menu" :key="category.id" class="category-wrapper"
                @click="categoryActive = category.id">
                <img :src="category.img" alt="" :class="{ 'image-active': category.id === categoryActive }">
                <span :class="{ 'name': true, 'name-active': category.id === categoryActive }">{{ category.name
                    }}</span>
            </div>
        </div>
        <hr />
        <div class="products">
            <div v-for="product in products" :key="product.id" class="card-wrapper">
                <Card :product="product" class="card" @add-to-cart="getCart" />
            </div>
        </div>
    </div>
</template>

<script>
import axios from "axios";
import Card from "@/components/Card.vue";
import { formatCurrency } from "../../helper/utils";

export default {
    components: {
        Card,
    },
    data() {
        return {
            products: [],
            carts: [],
            menu: [],
            categoryActive: -1,
            hinhThuc: 'Tại bàn',
            hinhThucInt: 1,
            payments: 'Chuyển khoản',
            paymentsInt: 1,
        };
    },
    computed: {
        total() {
            if (this.carts.cartItemList) return formatCurrency(this.carts.cartItemList.reduce((total, cart) => total + cart.price * cart.quantity, 0));
        },
        // hinhThuc() {

        // }
    },
    methods: {
        submit() {
            if (this.carts.orderId) {
                // != null => update order
                const request = {
                    "payments": this.paymentsInt,
                    "isOrderAtTable": this.hinhThucInt,
                    "itemCartList": this.carts.cartItemList.map(item => item.id),
                    "userId": localStorage.getItem("id"),
                }
                axios.put(this.$store.state.baseUrl + "/cashier/v1/orders/" + this.carts.orderId,
                    request,
                    { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                    .then(res => {
                        if (res.data.code != 2000) {
                            this.$message({
                                message: res.data.message,
                                type: 'error'
                            });
                            return false;
                        }
                        this.$message({
                            message: 'Cập nhật đơn hàng thành công',
                            type: 'success'
                        });
                        this.getCart();
                        return true;
                    })
                    .catch(error => {
                        console.log(error);
                    })

            } else {
                // == null => create order
                const request = {
                    "payments": this.paymentsInt,
                    "isOrderAtTable": this.hinhThucInt,
                    "itemCartList": this.carts.cartItemList.map(item => item.id),
                    "userId": localStorage.getItem("id"),
                }
                axios.post(this.$store.state.baseUrl + "/cashier/v1/orders",
                    request,
                    { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                    .then(res => {
                        if (res.data.code != 2000) {
                            this.$message({
                                message: res.data.message,
                                type: 'error'
                            });
                            return false;
                        }
                        this.$message({
                            message: 'Đặt đồ thành công',
                            type: 'success'
                        });
                        this.getCart();
                        return true;
                    })
                    .catch(error => {
                        console.log(error);
                    })
            }
        },
        async getMenu() {
            await axios.get(this.$store.state.baseUrl + "/cashier/v1/menu?userId=" + localStorage.getItem("id"),
                { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                .then(response => {
                    this.menu = response.data.data;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        getTotal() {
            return this.carts.cartItemList.reduce((total, cart) => total + cart.price * cart.quantity, 0);
        },
        deleteItem(item) {
            axios.delete(this.$store.state.baseUrl + "/cashier/v1/cartItem/" + item.id,
                { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                .then(res => {
                    if (res.data.code != 2000) {
                        this.$message({
                            message: res.data.message,
                            type: 'error'
                        });
                        return false;
                    }
                    this.$message({
                        message: 'Xóa sản phẩm thành công',
                        type: 'success'
                    });
                    this.getCart();
                    return true;
                })
                .catch(error => {
                    console.log(error);
                });

        },
        decrementQuantity(cart) {
            if (cart.quantity > 1) {
                this.updateQuantity(cart, cart.quantity - 1);
            } else {
                this.deleteItem(cart);
            }
        },
        incrementQuantity(cart) {
            this.updateQuantity(cart, cart.quantity + 1);
        },
        updateQuantity(cart, quantity) {
            axios.put(this.$store.state.baseUrl + "/cashier/v1/carts/" + cart.id + "/quantity",
                { quantity: quantity },
                { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                .then(res => {
                    if (res.data.code != 2000) {
                        this.$message({
                            message: res.data.message,
                            type: 'error'
                        });
                        return false;
                    }
                    cart.quantity = quantity;
                    return true;
                })
                .catch(error => {
                    console.log(error);
                });
        },
        hinhThucCommand(command) {
            this.hinhThucInt = command;
            switch (command) {
                case '1':
                    this.hinhThuc = 'Tại bàn';
                    break;
                case '0':
                    this.hinhThuc = 'Mang đi';
                    break;
            }
        },
        paymentCommand(command) {
            this.paymentsInt = command;
            switch (command) {
                case '1':
                    this.payments = 'Chuyển khoản';
                    break;
                case '0':
                    this.payments = 'Tiền mặt';
                    break;
                case '2':
                    this.payments = 'VNPay';
                    break;
            }
        },
        getProducts() {
            this.products = [];
            if (this.categoryActive === -1) {
                this.prducts = this.menu.map(item => {
                    item.products.map(product => {
                        this.products.push(product);
                    });
                });
            } else {
                this.products = this.menu.find(item => item.id === this.categoryActive).products;
            }
        },
        getCart() {
            axios.get(this.$store.state.baseUrl + "/cashier/v1/carts?userId=" + localStorage.getItem("id"),
                { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
                .then(response => {
                    this.carts = response.data.data;
                })
                .catch(error => {
                    console.log(error);
                });
        }
    },

    beforeUpdate() {
        this.getProducts();
    },
    mounted() {
        this.getMenu();
        this.getCart();
    },
}
</script>

<style scoped>
.order {
    display: flex;
    flex-direction: row;
    height: 100vh;
    padding: 0;
    /* background: yellow; */
}

.menu-icon {
    font-size: 35px;
    color: rgb(126, 126, 126)
}

.menu-active {
    color: #EE7531;
}

.cart {
    width: 700px;
}

.content {
    height: calc(100% - 59px - 61px);
    overflow-y: auto;
}

.cart .title,
.cart-item {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
}

.cart .title {
    margin: 20px 0 20px;
    font-size: 15px;
    font-weight: 500;
}

.cart-item {
    border-top: solid 1px #555;
    padding-top: 10px;
    margin-bottom: 20px;
    font-size: 16px;
    color: #484848;

}

.cart .space1 {
    width: 9%;
    display: flex;
    justify-content: center
}

.cart .name {
    width: 30%;
    text-align: left;
}

.cart .size {
    width: 9.5%;
}

.cart .quantity {
    width: 14.6%;
}

.cart .price {
    width: 18%;
}

.cart .space2 {
    width: 9%;
}

.action {
    height: 60px;
    display: flex;
    border-top: solid 1px #FF902A;
}

.space {
    flex: 1;
    display: flex;
    flex-direction: row;
    align-items: center;
}

.el-button {
    height: 30px;

    /* width: 110px; */
    background-color: #fff;
    border: solid 0.5px #8d8d8d;
    border-radius: 7px;
    padding: 8px 10px 8px 12px;
    color: #4e4e4e;
}

.hinhThuc,
.payment {
    margin: 10px;
}

.action .total {
    width: 200px;
    display: flex;
    flex-direction: column;
    align-items: flex-end;
    padding: 7px 10px;
}

.total label {
    font-size: 14px;
    font-weight: 300;
    color: #4F4F4F;
}

.total span {
    font-size: 22px;
    font-weight: bold;
    color: #FF902A;
}

.btn-order {
    height: 100%;
}

.btn-order .el-button {
    width: 150px;
    background-color: #EE7531;
    height: 100%;
    color: #fff;
    font-size: 18px;
    font-weight: 500;
    border-radius: 0;
    border: none;

}

.el-icon-plus,
.el-icon-minus {
    border: solid 1px #4d4d4d;
    font-size: 13px;
    font-weight: 600;
    color: #4d4d4d;
    width: 18px;
    height: 18px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 3px;
}

.el-icon-delete {
    font-size: 20px;
    color: rgb(218, 0, 0);
    font-weight: 500;
}



.category {
    width: 100px;
    overflow-y: auto;
}

.category::-webkit-scrollbar {
    width: 5px;
}

.category::-webkit-scrollbar-track {
    background: #f1f1f1;
}

.category::-webkit-scrollbar-thumb {
    background: #b6b6b6;
}

.category::-webkit-scrollbar-thumb:hover {
    background: #8b8b8b;
}

.category-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin: 20px 0 30px;
    cursor: pointer;
}

.category-wrapper img:hover {
    box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.459);
}

.category-wrapper img {
    border-radius: 10px;
    width: 45px;
    height: 45px;
    border: solid 1px #858585;
    box-shadow: 0px 3px 10px rgba(0, 0, 0, 0.26);
}

.category-wrapper span {
    font-size: 14px;
    width: 70px;
    font-weight: 400;
    color: rgb(97, 97, 97);
    margin-top: 2px;
}

.name-active {
    color: #aa715a !important;
    font-weight: bold !important;
    font-size: 16px !important;
}

.image-active {
    width: 65px !important;
    height: 65px !important;
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.459) !important;
}

.products {
    width: calc(100% - 800px);
    overflow-y: auto;
    height: 100%;
    /* padding-top: 10px; */
}

.card-wrapper {
    /* flex: 1 0 18%;  */
    float: left;

    margin: 15px 0 10px 20px;
}

.card {
    width: 180px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    border: solid 0.5px #949494;
    border-radius: 10px;
    user-select: none;
    background: #fff;
    box-shadow: 0px 4px 10px rgba(0, 0, 0, 0.26);
}

.card:hover {
    box-shadow: 0px 5px 15px rgba(0, 0, 0, 0.46);
    transition: 0.3s;
    cursor: pointer;
}

.card-wrapper>>>.card {
    .name {
        font-size: 16px;
        font-weight: bold;
        margin-top: 3px;
    }

    img {
        width: 95%;
        margin-top: 5px;
        height: 135px;
        border-radius: 10px;
    }

    .size-price {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin: 5px 0 5px;
    }

    .size-price .price {
        width: 80px;
        font-size: 16px;
        font-weight: 500;
        color: #B2470B;
    }

    .el-dropdown {
        width: 55px;
        font-size: 16px;
    }

    .el-dropdown-menu__item {
        font-family: "sarabun", "sans-serif";
        font-size: 16px;
    }
}

@media (max-width: 600px) {
    .product-card {
        flex: 1 0 100%;
    }
}
</style>
