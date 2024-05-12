<template>
  <div class="card">
    <img :src="product.img" alt="" />
    <span class="name">{{ product.name }}</span>
    <div class="size-price">
      <el-dropdown trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{ product.curSize }}
          <i class="el-icon-arrow-down el-icon--right"></i>
        </span>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command='S'>S</el-dropdown-item>
          <el-dropdown-item command='M'>M</el-dropdown-item>
          <el-dropdown-item command='L'>L</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span class="price">{{ curPrice }}</span>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    product: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {};
  },
  computed: {
    curPrice() {
      const result = this.product.price.find(p => p.size === this.product.curSize
      );
      return result.value;
    },
  },
  methods: {
    handleCommand(item) {
        this.product.curSize = item;
    },
  },
};
</script>

<style scoped>
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

</style>
