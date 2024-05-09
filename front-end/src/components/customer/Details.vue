<template>
    <div class="product-details">
        <div class="product-image">
            <img :src="product.image" alt="Product Image">
        </div>
        <div class="product-info">
            <label>{{ product.name }}</label>
            <span>{{ product.price }}</span>
        </div>
        <div class="quantity">
            <label>Số lượng</label>
            <button class="decrease" @click="decreaseQuantity"><i class="el-icon-minus"></i></button>
            <span>{{ quantity }}</span>
            <button class="increase" @click="increaseQuantity"><i class="el-icon-plus"></i></button>
        </div>
        <div class="description">
            <label>Mô tả</label>
            <span>{{ product.description }}</span>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            product: {
                image: 'https://honglam.vn/pic/news/3%20(15).png',
                name: 'Trà chanh xí muội',
                price: '50,000 đ',
                description: 'Trà chanh xíu muội là một loại đồ uống phổ biến trong văn hóa ẩm thực Việt Nam. Một ly trà chanh xíu muội thường mang hương vị hài hòa giữa chua của chanh, ngọt của đường và hương thơm nhẹ nhàng từ lá trà. Vị chua của chanh được làm dịu bớt nhờ vào việc kết hợp với đường và xíu muội, tạo ra một hương vị độc đáo và hấp dẫn. Trà chanh xíu muội không chỉ là một đồ uống thư giãn mà còn là một cách thưởng thức hương vị tinh tế của văn hóa ẩm thực Việt. Đặc biệt, trong những ngày nắng nóng, một ly trà chanh xíu muội lạnh sẽ mang lại cảm giác sảng khoái và thư giãn cho người thưởng thức'
            },
            quantity: 1
        }
    },
    methods: {
        decreaseQuantity() {
            if (this.quantity > 1) {
                this.quantity--;
            }
        },
        increaseQuantity() {
            this.quantity++;
        }
    }
}
</script>

<style>
.product-details {
    display: flex;
    flex-direction: column;
    padding-top: 15px;
}

.product-image img {
    width: 400px;
    height: 300px;
    border-radius: 12px;
}
.product-info {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    margin: 10px 0 15px 0;
}

.product-info label {
    width: 70%;
    font-size: 24px;
    color: #451717;
    font-weight: bold;
    text-align: left;
}

.product-info span {
    width: 30%;
    font-size: 22px;
    color: #B2470B;
    font-weight: bold;
    text-align: right;
}

.quantity {
  display: flex;
  justify-content: start;
  margin: 15px 0 10px 0;
}

.quantity label {
  font-size: 16px;
  color: #50575C;
  margin-right: 15px;
  font-weight: 500;
}

.quantity button {
    width: 35px;
    height: 30px;
    background: #E0E0E0;
    font-size: 15px;
    padding-top: 2px;
    
}

.quantity button i {
    font-weight: bold;
}

.quantity span {
    width: 40px;
    height: 30px;
    background: #E0E0E0;
    text-align: center;
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 18px;
}

.decrease {
    border: 0;
    border-right: 0.5px solid #999999;
    border-top-left-radius: 15px;
    border-bottom-left-radius: 15px;
}

.increase {
    border: 0;
    border-left: 0.5px solid #999999;
    border-top-right-radius: 15px;
    border-bottom-right-radius: 15px;
}

.description {
    display: flex;
    flex-direction: column;
    align-items: start;
    margin-top: 20px
}

.description label {
    font-size: 18px;
    color: #414141;
    font-weight: 500;
    margin-bottom: 10px;
}

.description span {
    text-align: justify;
    font-size: 14px;
    color: #7c7c7c;
    font-weight: 500;
}

</style>