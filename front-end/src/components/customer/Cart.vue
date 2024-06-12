<template>
  <div class="order-screen">
    <h1>NHỮNG MÓN ĐÃ THÊM</h1>
    <div class="cart">
      <div v-for="(item, index) in items" :key="item.id" class="item">
        <input type="checkbox" v-model="item.isCheck" />
        <div class="item-info">
          <img :src="item.img" alt="" />
          <div class="details">
            <span class="item-name item-details">{{ item.name }}</span>
            <span class="item-size item-details" v-if="item.hasSize">size {{ item.sizeSelected }}</span>
            <span class="item-price item-details">{{ item.priceDisplay }}</span>
            <span class="item-note item-details" @click="editNote({id: item.id, note: item.note})">
              {{ item.note }}
              <i class="el-icon-edit"></i>
            </span>
          </div>
        </div>
        <div class="action">
          <i class="el-icon-plus icon" @click="incrementQuantity(item)"></i>
          <span>{{ item.quantity }}</span>
          <i class="el-icon-minus icon" @click="decrementQuantity(item)"></i>
        </div>
      </div>
    </div>
    <div class="option">
      <div class="discount-section" v-if="false">
        <el-input v-model="discountCode" placeholder="Mã giảm giá"></el-input>
      </div>
      <div class="payment-methods">
        <h3>Phương thức thanh toán</h3>
        <label><input type="radio" value="0" v-model="paymentMethod" /> Tiền
          mặt</label>
        <label><input type="radio" value="1" v-model="paymentMethod" />
          Chuyển khoản</label>
        <label><input type="radio" value="2" v-model="paymentMethod" />
          VNPay</label>
      </div>
      <div class="delivery-methods">
        <label><input type="radio" value="1" v-model="deliveryMethod" /> Tại
          bàn</label>
        <label><input type="radio" value="0" v-model="deliveryMethod" /> Mang
          đi</label>
      </div>
      <div class="total-cost">
        <h3>
          Tổng thanh toán: <br /><span>{{ totalCost }}</span>
        </h3>
        <button @click="payment" class="checkout-btn">ĐẶT ĐỒ</button>
      </div>
    </div>
    <NoteEditPopup v-if="showPopup" @close="showPopup = false" class="popup" @save="saveNote" :noteEdit="noteEdit.note"/>
  </div>
</template>

<script>
import axios from 'axios';
import { formatCurrency } from '@/helper/utils.js';
import NoteEditPopup from './NoteEditPopup.vue';

export default {
  components: {
    NoteEditPopup
  },
  data() {
    return {
      items: [],
      discountCode: "",
      paymentMethod: 0,
      deliveryMethod: 1,
      showPopup: false,
      noteEdit: {},
    };
  },
  computed: {
    totalCost() {
      return formatCurrency(this.items
        .filter((item) => item.isCheck)
        .reduce((sum, item) => sum + item.price * item.quantity, 0));
    },
  },
  methods: {
    saveNote(note) {
      axios.put(this.$store.state.baseUrl + `/guest/v1/carts/${this.noteEdit.id}/note/`, { note },
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      ).then(res => {
        if (res.data.code != 2000) {
          this.$message({
            message: res.data.message,
            type: 'error'
          })
          return false;
        }
        this.items = this.items.map((item) => {
          if (item.id === this.noteEdit.id) {
            item.note = note;
          }
          return item;
        });
      }).catch(err => {
        console.log(err);
      })
      this.showPopup = false;
    },
    editNote(note) {
      this.noteEdit = note;
      this.showPopup = true;
    },
    async getCartList() {
      // Call API to get cart list
      await axios.get(this.$store.state.baseUrl + "/guest/v1/carts?userId=" + localStorage.getItem("id"),
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      ).then(res => {
        this.items = res.data.data;
      }).catch(err => {
        console.log(err);
      })
    },
    incrementQuantity(item) {
      this.updateQuantity(item.id, ++item.quantity);
    },
    async decrementQuantity(item) {
      if (item.quantity > 1) {
        this.updateQuantity(item.id, --item.quantity);
      } else {
        // Call API to remove item from cart
        await axios.delete(this.$store.state.baseUrl + "/guest/v1/cartItem/" + item.id,
          { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
        ).then(res => {
          console.log(res);
          if (res.data.code != 2000) {
            this.$message({
              message: data.message,
              type: 'error'
            })
            return false;
          }
          // delete item
          this.items = this.items.filter((i) => i.id !== item.id);
        }).catch(err => {
          console.log(err);
        })
      }
    },
    async updateQuantity(id, quantity) {
      // Call API to update quantity
      await axios.put(this.$store.state.baseUrl + "/guest/v1/carts/" + id + "/quantity", { quantity },
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      ).then(res => {
        const data = res.data;
        if (data.code != 2000) {
          this.$message({
            message: data.message,
            type: 'error'
          })
          return false;
        }
        return true;
      }).catch(err => {
        console.log(err);
      })
    },
    payment() {
      let request = {
        "payments": this.paymentMethod,
        "isOrderAtTable": this.deliveryMethod,
        "userId": localStorage.getItem("id"),
        "branchId": localStorage.getItem("branchId"),
        "tableNumber": localStorage.getItem("tableNumber"),
      };

      let itemCartList = [];
      this.items.forEach(item => {
        if (item.isCheck) {
          itemCartList.push(item.id);
        }
      });
      if (itemCartList.length == 0) {
        this.$message({
          message: "Vui lòng chọn món",
          type: 'warning'
        })
        return false;
      }
      request.itemCartList = itemCartList;
      // Call API to create order
      axios.post(this.$store.state.baseUrl + "/guest/v1/orders", request,
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } })
        .then(res => {
          if (res.data.code != 2000) {
            this.$message({
              message: res.data.message,
              type: 'error'
            })
            return false;
          }else{
            this.$message({
              message: "Đặt đồ thành công",
              type: 'success'
            })
            this.$router.push('/order-success');
          }
        }).catch(err => {
          this.$message({
              message: "Lỗi hệ thống",
              type: 'error'
            })
            return false;
          console.log(err);
        })


    }
  },
  beforeMount() {
    this.getCartList();
  },
};
</script>

<style scoped>

.order-screen>>>.popup{
  .popup-overlay {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(39, 39, 39, 0.863);
    display: flex;
    flex-direction: column;
    justify-content: end;
  }

  .popup-content {
    /* height: 63%; */
    background-color: white;
    border-radius: 10px;
    display: flex;
    align-items: center;
    flex-direction: column;
    padding: 0 15px 60px 15px;
    overflow-y: auto;
  }

  .el-textarea {
    width: 100%;
    height: 200px;
    margin: 5px 0 15px;
  }

  .el-textarea textarea{
    height: 100%;
    font-family: "Sarabun", sans-serif;
    font-size: 16px;
  }

  .header {
    width: 100%;
    height: 45px;
    /* background: gray; */
    display: flex;
    align-items: center;
    justify-content: space-evenly;
  }

  .header label {
    font-size: 18px;
    font-weight: 600;
    color: rgb(49, 49, 49);
  }

  .submit {
    width: 100%;
    height: 40px;
    font-size: 16px;
    border-radius: 0;
    background: #ff6600;
    color: white;
    border: none;
    margin-bottom: 5px;
  }
}
.order-screen {
  display: flex;
  flex-direction: column;
  height: 100%;
}

h1 {
  color: #333;
  font-size: 18px;
  font-weight: 400;
  margin: 10px 0 10px 0;
  text-align: left;
}

.cart {
  flex-grow: 1;
  overflow-y: auto;
  scrollbar-width: thin;
  scrollbar-color: transparent transparent;
}

.cart::-webkit-scrollbar {
  width: 6px;
}

.cart::-webkit-scrollbar-thumb {
  background-color: transparent;
}

.cart::-webkit-scrollbar-track {
  background-color: transparent;
}

.action {
  margin-top: 3px;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.action .icon {
  width: 15px;
  height: 15px;
  padding: 5px;
  font-size: 14px;
  border-radius: 3px;
}

.el-icon-minus,
.el-icon-plus {
  display: flex;
  justify-content: center;
  align-items: center;
}

.action .el-icon-minus {
  border: solid 1px #FF902A;
  color: #FF902A;
  width: 9px;
  height: 9px;
}

.action .el-icon-plus {
  background: #FF902A;
  color: #FF902A;
  color: #fff;
  width: 11px;
  height: 11px;
}

.action span {
  padding: 4px 7px 4px 7px;
  font-size: 15px;
  text-align: center;
}

.item {
  display: flex;
  align-items: start;
  /* margin-bottom: 5px; */
  padding: 12px 0 10px;
  background-color: #fafafa;
  border-bottom: 1px solid #d8d8d8;
}

.item input[type="checkbox"] {
  margin: 25px 10px 0 0;
  height: 17px;
  width: 17px;
}

.item-info {
  flex: 1;
  display: flex;
  flex-direction: row;
  align-items: start;
  padding-right: 8px;
}

.details {
  margin-left: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  min-height: 100%;
}

.item-details {
  margin-bottom: 5px;
  text-align: left;
}

.item-info img {
  width: 65px;
  height: 65px;
  border-radius: 5px;
}

.item-name {
  font-size: 16px;
  font-weight: bold;
  color: #333;
}

.item-size {
  color: #888;
  font-size: 13px;
}

.item-note {
  color: #444444;
  font-size: 12px;
}

.item-price {
  color: #ff6600;
  font-size: 15px;
  font-weight: 600;
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
  background-color: #ee7e3d;
  color: #fff;
  border: none;
  cursor: pointer;
  width: 130px;
  font-size: 18px;
  font-weight: 400;
  font-family: "Sarabun", sans-serif;
}
</style>
