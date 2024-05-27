<template>
  <div class="order-screen">
    <h1>NHỮNG MÓN ĐÃ THÊM</h1>
    <div class="cart">
      <div v-for="(item, index) in items" :key="index" class="item">
        <input type="checkbox" v-model="item.selected" />
        <div class="item-info">
          <img :src="item.img" alt="" />
          <div class="details">
            <span class="item-name">{{ item.name }}</span>
            <span class="item-size">size {{ item.size }}</span>
            <span class="item-price">{{ item.price.toLocaleString() }} đ</span>
          </div>
        </div>
        <div class="quantity-control">
          <button
            @click="decrementQuantity(index)"
            class="quantity-btn btn-left"
          >
            -
          </button>
          <span>{{ item.quantity }}</span>
          <button
            @click="incrementQuantity(index)"
            class="quantity-btn btn-right"
          >
            +
          </button>
        </div>
      </div>
    </div>
    <div class="option">
      <div class="discount-section" v-if="false">
        <el-input v-model="discountCode" placeholder="Mã giảm giá"></el-input>
      </div>
      <div class="payment-methods">
        <h3>Phương thức thanh toán</h3>
        <label
          ><input type="radio" value="Tiền mặt" v-model="paymentMethod" /> Tiền
          mặt</label
        >
        <label
          ><input type="radio" value="Chuyển khoản" v-model="paymentMethod" />
          Chuyển khoản</label
        >
        <label
          ><input type="radio" value="VNPAY" v-model="paymentMethod" />
          VNPay</label
        >
      </div>
      <div class="delivery-methods">
        <label
          ><input type="radio" value="Tại bàn" v-model="deliveryMethod" /> Tại
          bàn</label
        >
        <label
          ><input type="radio" value="Mang đi" v-model="deliveryMethod" /> Mang
          đi</label
        >
      </div>
      <div class="total-cost">
        <h3>
          Tổng thanh toán: <br /><span>{{ totalCost.toLocaleString() }} đ</span>
        </h3>
        <button @click="checkout" class="checkout-btn">Thanh toán</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
        {
          img: "https://nguyenlieuantoan.com/assets/upload/news/1699843199_tra-chanh-xi-muoi-vat.jpg?v=0.0.3.1",
          name: "Trà chanh xí muội",
          size: "L",
          price: 24300,
          quantity: 1,
          selected: true,
        },
      ],
      discountCode: "",
      paymentMethod: "Tiền mặt",
      deliveryMethod: "Tại bàn",
    };
  },
  computed: {
    totalCost() {
      return this.items
        .filter((item) => item.selected)
        .reduce((sum, item) => sum + item.price * item.quantity, 0);
    },
  },
  methods: {
    incrementQuantity(index) {
      this.items[index].quantity++;
    },
    decrementQuantity(index) {
      if (this.items[index].quantity > 1) {
        this.items[index].quantity--;
      }
    },
    checkout() {
      alert("Thanh toán thành công!");
    },
  },
};
</script>

<style>
.order-screen {
  display: flex;
  flex-direction: column;
  height: 100%;
}

h1 {
  color: #333;
  font-size: 18px;
  font-weight: 400;
  margin: 10px 0 20px 0;
  text-align: left;
}

.cart {
  flex-grow: 1;
  overflow-y: auto;
}

.item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  padding: 7px;
  background-color: #ffffff;
  border-radius: 10px;
  box-shadow: 1px 5px 10px 1px rgba(0, 0, 0, 0.212);
}

.item input[type="checkbox"] {
  margin-right: 10px;
  height: 17px;
  width: 17px;
}

.item-info {
  flex: 1;
  display: flex;
  flex-direction: row;
  align-items: center;
}

.details {
  margin-left: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.item-info img {
  width: 65px;
  height: 65px;
  border-radius: 5px;
}

.item-name {
  font-size: 18px;
  font-weight: bold;
  color: #333;
}

.item-size {
  color: #888;
  font-size: 14px;
  margin-bottom: 5px;
}

.item-price {
  color: #ff6600;
  font-weight: bold;
}

.quantity-control {
  display: flex;
  align-items: center;
  margin-top: 35px;
  height: 100%;
  border: solid 1px #969696;
  border-radius: 7px;
  background: #f6f6f6;
}

.quantity-control span {
  margin: 0 7px 0 7px;
  font-size: 14px;
}

.quantity-btn {
  width: 20px;
  height: 20px;
  font-size: 15px;
  display: flex;
  justify-content: center;
  align-items: center;
  border: none;
  background: none;
}

.btn-left {
  border-right: solid 1px #969696;
}

.btn-right {
  border-left: solid 1px #969696;
}

.option {
    height: auto;
}

.discount-section {
  width: 50%;
  margin-top: 15px;
  font-size: 16px;
}

.discount-section input {
  font-family: "Sarabun", sans-serif;
  height: 32px;
  border-radius: 7px;
  font-size: 16px;
}

.payment-methods {
  text-align: left;
  display: flex;
  flex-direction: column;
  padding-left: 5px;
}

.payment-methods label {
  padding: 0 0 10px 40px;
}

.payment-methods h3 {
  margin: 10px 0 10px;
  font-weight: 400;
  font-size: 16px;
}

input[type="radio"] {
  margin-right: 5px;
}

.delivery-methods {
  margin-top: 15px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.delivery-methods label {
  margin-bottom: 10px;
}

.total-cost {
  margin-top: 5px;
  text-align: center;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  border-top: solid 1px #ff6600;
  border-bottom: solid 1px #ff6600;
}

.total-cost h3 {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  font-size: 16px;
  font-weight: 400;
  margin: 5px 10px 5px;
}

.total-cost span {
  color: #ee7531;
  font-weight: bold;
  text-align: right;
  font-size: 18px;
}

.checkout-btn {
  padding: 10px 20px;
  background-color: #ee7531;
  color: #fff;
  border: none;
  cursor: pointer;
  width: 130px;
  font-size: 16px;
  font-family: "Sarabun", sans-serif;
}

.checkout-btn:hover {
  background-color: #e55c00;
}
</style>

