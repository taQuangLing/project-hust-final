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
                <div class="cart-item" v-for="cart in carts">
                    <div class="space1"><i class="el-icon-minus btn"></i></div>
                    <span class="name">{{ cart.name }}</span>
                    <span class="size">{{ cart.size }}</span>
                    <span class="quantity">{{ cart.quantity }}</span>
                    <span class="price">{{ cart.price }}</span>
                    <div class="space1"><i class="el-icon-plus btn"></i></div>
                    <i class="el-icon-delete space2 btn"></i>
                </div>
            </div>
            <div class="action">
                <div class="space">
                    <el-dropdown class="hinhThuc" @command="hinhThucCommand">
                        <el-button type="primary">
                            {{ hinhThuc }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="Tại bàn">Tại bàn</el-dropdown-item>
                            <el-dropdown-item command="Mang đi">Mang đi</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                    <el-dropdown class="payment" @command="paymentCommand">
                        <el-button type="primary">
                            {{ payment }}<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="Chuyển khoản">Chuyển khoản</el-dropdown-item>
                            <el-dropdown-item command="Tiền mặt">Tiền mặt</el-dropdown-item>
                            <el-dropdown-item command="VNPay">VNPay</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
                <div class="total">
                    <label for="">Tổng thanh toán</label>
                    <span>146,000 đ</span>
                </div>
                <div class="btn-order">
                    <el-button type="primary">ĐẶT HÀNG</el-button>
                </div>
            </div>
        </div>
        <hr />
        <div class="category">
            <div v-for="category in categories" :key="category.id" class="category-wrapper"
                @click="categoryActive = category.id">
                <img :src="category.img" alt="" :class="{ 'image-active': category.id === categoryActive }">
                <span :class="{ 'name': true, 'name-active': category.id === categoryActive }">{{ category.name
                    }}</span>
            </div>
        </div>
        <hr />
        <div class="products">
            <div v-for="product in products" :key="product.id" class="card-wrapper">
                <Card :product="product" />
            </div>
        </div>
    </div>
</template>

<script>
import Card from "./Card.vue";

export default {
    components: {
        Card,
    },
    data() {
        return {
            products: [
                {
                    id: 123,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 356,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 3456,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 3456,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 34563,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 36456,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 3456,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
                {
                    id: 456,
                    img: "https://cong-news.appwifi.com/wp-content/uploads/2023/02/co%CC%82%CC%81t-du%CC%9B%CC%80a-ca%CC%80-phe%CC%82_lo%CC%9B%CC%81n.png",
                    name: "Cà phê cốt dừa",
                    price: [
                        {
                            size: "S",
                            value: "35,000 đ",
                        },
                        {
                            size: "M",
                            value: "45,000 đ",
                        },
                        {
                            size: "L",
                            value: "55,000 đ",
                        },
                    ],
                    curSize: "S",
                },
            ],
            categories: [
                {
                    id: 13,
                    name: "Cà phê",
                    img: "https://media.baoquangninh.vn/upload/image/202309/medium/2121767_707dcce179f6866d132a2d6a384312f9.jpg"
                },
                {
                    id: 23,
                    name: "Trà sữa",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 33,
                    name: "Nước ép",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 43,
                    name: "Sinh tố",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 35,
                    name: "Nước ngọt",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 13,
                    name: "Cà phê",
                    img: "https://media.baoquangninh.vn/upload/image/202309/medium/2121767_707dcce179f6866d132a2d6a384312f9.jpg"
                },
                {
                    id: 23,
                    name: "Trà sữa",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 33,
                    name: "Nước ép",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 43,
                    name: "Sinh tố",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
                {
                    id: 35,
                    name: "Nước ngọt",
                    img: "https://abcsport.com.vn/image/catalog/2023/T01/mot-ly-tra-sua-bao-nhieu-calo-1.jpg"
                },
            ],
            carts: [
                {
                    id: 123,
                    "name": "Cà phê cốt dừa",
                    "size": "S",
                    "quantity": 1,
                    "price": "35,000 đ",
                },
                {
                    id: 123,
                    "name": "Cà phê cốt dừa",
                    "size": "S",
                    "quantity": 1,
                    "price": "35,000 đ",
                },
                {
                    id: 123,
                    "name": "Cà phê cốt dừa",
                    "size": "S",
                    "quantity": 1,
                    "price": "35,000 đ",
                }
            ],
            categoryActive: 0,
            hinhThuc: 'Tại bàn',
            payment: 'Chuyển khoản',
        };
    },
    beforeMount() {
        this.categoryActive = this.categories[0].id;
        console.log(this.categoryActive);
    },
    methods: {
        hinhThucCommand(command) {
            this.hinhThuc = command;
        },
        paymentCommand(command) {
            this.payment = command;
        }
    }
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

.cart {
    width: 700px;
}

.content {
    height: calc(100% - 60px - 60px);
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
}

.hinhThuc, .payment {
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
    font-weight: 300;
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

@media (max-width: 600px) {
    .product-card {
        flex: 1 0 100%;
    }
}
</style>
