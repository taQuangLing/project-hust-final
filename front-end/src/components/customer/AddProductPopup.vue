<template>
  <div>
    <div class="popup-overlay" @click.self="closePopup">
      <div class="popup-content">
        <!-- Nội dung của popup -->
        <div class="header">
          <label for="">Thêm món mới</label>
          <!-- <i class="el-icon-close" @click="closePopup"></i> -->
        </div>
        <hr>
        <div class="product">
          <div class="info">
            <img :src="product.img" alt="" v-if="product.img" />
            <div class="info-detail">
              <label>{{ product.name }}</label>
              <p>{{ product.sumary }}</p>
              <span>{{ sizeSelected.priceDisplay }}</span>
            </div>
          </div>
          <div class="action">
            <i class="el-icon-minus icon" @click="decrease"></i>
            <span>{{ quantity }}</span>
            <i class="el-icon-plus icon" @click="quantity++"></i>
          </div>
        </div>
        <hr>
        <div class="size" v-if="sizeSelected.size">
          <el-radio-group v-model="sizeSelected">
            <div v-for="item in product.sizes" :key="item.id" class="size-item">
              <el-radio :label="item">
                <span style="margin-right: 40px;">{{ "Size " + item.size }}</span>
                <span>{{ item.priceDisplay }}</span>
              </el-radio>
            </div>
          </el-radio-group>
          <hr>
        </div>
        <div class="note">
          <el-input type="textarea" :rows="2" placeholder="Ghi chú cho quán..." v-model="note">
          </el-input>
        </div>
        <div class="submit">
          <el-button type="primary" plain @click="addToCart">{{ "Thêm vào - " + total }}</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
import { formatCurrency } from '@/helper/utils.js';

export default {
  props: ["isPopupVisible", "productId"],
  data() {
    return {
      quantity: 1,
      size: {},
      note: "",
      product: {},
      sizeSelected: {},
      total: 0,
    };
  },
  methods: {
    closePopup() {
      this.$emit("close", false);
    },
    async getProductDetail() {
      await axios.get(this.$store.state.baseUrl + "/guest/v1/products/" + this.productId,
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      )
        .then((response) => {
          this.product = response.data.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    decrease() {
      if (this.quantity > 1) {
        this.quantity--;
      }
    },
    addToCart() {
      let cartItemRequest = {};
      const userId = localStorage.getItem('id');
      const productId = this.product.id;
      const sizeSelectedId = this.sizeSelected.id;
      const quantity = this.quantity;
      const note = this.note;
      axios.post(this.$store.state.baseUrl + "/guest/v1/carts",
        {
          userId,
          productId,
          sizeSelectedId,
          quantity,
          note
        },
        { headers: { Authorization: `Bearer ${localStorage.getItem('user')}` } }
      )
        .then((response) => {
          if (response.data.code != 2000) {
            console.log(response.data);
            this.$emit("close", false);
            this.$message.error('Thêm vào giỏ hàng thất bại');
            return;
          }
          this.$emit("close", false);
          this.$message({
            message: 'Thêm vào giỏ hàng thành công',
            type: 'success',
            duration: 3000,
          });
        })
        .catch((error) => {
          console.log(error);
          this.$emit("close", false);
          this.$message.error('Thêm vào giỏ hàng thất bại');
        });
    }
  },
  async beforeMount() {
    await this.getProductDetail();
    if (this.product.hasSize == 0) {
      this.sizeSelected = {
        priceDisplay: this.product.priceDisplay,
        price: this.product.price,
        size: false,
      }
    } else {
      this.product.sizes.map(size => {
        if (size.isDefault == 1) {
          this.sizeSelected = size;
        }
      })
    }
    this.total = formatCurrency(this.sizeSelected.price * this.quantity);
  },
  watch: {
    quantity: function () {
      this.total = formatCurrency(this.sizeSelected.price * this.quantity);
    },
    sizeSelected: function () {
      this.total = formatCurrency(this.sizeSelected.price * this.quantity);
    }
  }
};
</script>