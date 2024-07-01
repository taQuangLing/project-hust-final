<template>
  <div @click="addToCart">
    <img :src="product.img" alt="" />
    <span class="name">{{ product.name }}</span>
    <div class="size-price">
      <div @click.stop>
        <el-dropdown trigger="click" @command="handleCommand" v-if="product.hasSize">
        <span class="el-dropdown-link">
          {{ size.size }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item :command='size' v-for="size in product.sizes" :key="size.id">{{ size.size }}</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      </div>
      <span class="price" v-if="!product.hasSize">{{ product.price }}</span>
      <span class="price" v-if="product.hasSize">{{ size.priceDisplay }}</span>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      size: {},
    };
  },
  mounted() {
    if (this.product.hasSize) {
      const size = this.product.sizes.find(item => item.isDefault == 1);
      this.size = size;
    }
  },
  methods: {
    handleCommand(item) {
      this.size = item;
    },
    async addToCart() {
      let request = {
        "userId": localStorage.getItem('id'),
        "quantity": 1,
        "productId": this.product.id,
      };

      if (this.product.hasSize) {
        request.sizeSelectedId = this.size.id;
      }

      console.log(request);
      await axios.post(this.$store.state.baseUrl + "/cashier/v1/carts",
        request,
        {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("user")}`,
          },
        }
      ).then((res) => {
        if (res.data.code != 2000){
          this.$message({
            message: res.data.description,
            type: 'error'
          });
        } else {
          this.$message({
            message: 'Thêm thành công',
            type: 'success'
          });
          this.$emit('add-to-cart');
        }
      }).catch((err) => {
        console.log(err);
      });
    },
  },
};
</script>
