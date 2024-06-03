<template>
  <div>
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
